package cn.zhaojian.system.modules.base.serviceimpl;

import cn.hutool.core.util.StrUtil;
import cn.zhaojian.system.common.exception.BabException;
import cn.zhaojian.system.common.exception.EntityExistException;
import cn.zhaojian.system.common.utils.FileUtil;
import cn.zhaojian.system.modules.base.entity.*;
import cn.zhaojian.system.modules.base.service.UserRoleService;
import cn.zhaojian.system.modules.base.utils.SecurityUtils;
import cn.zhaojian.system.common.utils.StringUtils;
import cn.zhaojian.system.config.service.UserCacheClean;
import cn.zhaojian.system.modules.base.dao.UserDao;
import cn.zhaojian.system.modules.base.service.UserService;
import cn.zhaojian.system.modules.base.vo.UserDto;
import cn.zhaojian.system.modules.base.vo.UserRoleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.*;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserCacheClean userCacheClean;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserDao getRepository() {
        return userDao;
    }

    @Override
    public Page<User> findByCondition(User user, Pageable pageable) {
        return userDao.findAll(new Specification<User>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> userNameField = root.get("userName");
                Path<String> cNameField = root.get("cname");
                Path<String> emailField = root.get("email");
                Path<String> phoneField = root.get("phone");

                List<Predicate> list = new ArrayList<Predicate>();
                Root<Position> bRoot = cq.from(Position.class);
                Root<Department> cRoot = cq.from(Department.class);
                list.add(cb.equal(root.get("position"), bRoot.get("id")));
                list.add(cb.equal(root.get("dept"), cRoot.get("id")));

                //模糊搜素
                if (StrUtil.isNotBlank(user.getUserName())) {
                    list.add(cb.like(userNameField, '%' + user.getUserName() + '%'));
                }
                if (StrUtil.isNotBlank(user.getCname())) {
                    list.add(cb.like(cNameField, '%' + user.getCname() + '%'));
                }
                if (StrUtil.isNotBlank(user.getEmail())) {
                    list.add(cb.like(emailField, '%' + user.getEmail() + '%'));
                }
                if (StrUtil.isNotBlank(user.getPhone())) {
                    list.add(cb.like(phoneField, '%' + user.getPhone() + '%'));
                }

                if (StrUtil.isNotBlank(user.getDepartmentId())) {
                    list.add(cb.equal(cRoot.get("id"), user.getDepartmentId()));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public UserDto findByUserName(String username) {
        User user = userDao.findByUserName(username);
        UserDto userDto = new UserDto();
        if (user == null) {
            return null;
        } else {
            userDto.setId(user.getId());
            userDto.setCname(user.getCname());
            userDto.setUsername(user.getUserName());
            userDto.setPassword(user.getPassword());
            userDto.setEmail(user.getEmail());
            userDto.setPhone(user.getPhone());
            userDto.setAvatar(user.getAvatar());
            userDto.setSex(user.getSex());
            userDto.setAddress(user.getAddress());
            userDto.setIsdisabled(user.getIsdisabled());
            userDto.setDept(user.getDept());
            String fileName = "";
            if (user.getAvatar() != null) {
                File tempFile = new File(user.getAvatar().trim());
                fileName = tempFile.getName();
            }
            userDto.setAvatar(fileName);
        }
        return userDto;
    }

    @Override
    public Map<Object, String> uploadAvatar(MultipartFile multipartFile) {
        Map<Object, String> map = new HashMap<>();
        User user = userDao.findByUserName(SecurityUtils.getCurrentUsername());
        String oldPath = user.getAvatar();
        File file = FileUtil.upload(multipartFile, "F:\\my-project\\jxxqz-admin\\zjadmin-web\\zjadmin-web\\src\\assets\\avatar\\");
        user.setAvatar(Objects.requireNonNull(file).getPath());
        userDao.save(user);
        if (StringUtils.isNotBlank(file.getName())) {
            FileUtil.del(oldPath);
        }
        @NotBlank String username = user.getUserName();
        redisTemplate.delete("user::username:" + username);
        flushCache(username);
        map.put("avatar", file.getName());
        return map;
//        return new HashMap<String, String>(1) {{
//            put("avatar", file.getName());
//        }};
    }

    /**
     * 清理 登陆时 用户缓存信息
     *
     * @param username /
     */
    private void flushCache(String username) {
        userCacheClean.cleanUserCache(username);
    }

    /*
    保存用户
    *
     */
    @Override
    public void create(User user) {
        User user1 = userDao.findByUserName(user.getUserName());
        if (user1 != null) {
            throw new EntityExistException(User.class, "userName", user.getUserName());
        }

        User user2 = userDao.findByCname(user.getCname());
        if (user2 != null) {
            throw new BabException("姓名已存在，请重新添加");
        }
        User user3 = userDao.findByEmail(user.getEmail());
        if (user3 != null) {
            throw new BabException("邮箱已存在，请重新添加");
        }
        User user4 = userDao.findByPhone(user.getPhone());
        if (user4 != null) {
            throw new BabException("手机号已存在，请重新添加。");
        }
        userDao.save(user);
    }

    @Override
    public void delete(Set<Long> ids) {
        userDao.deleteAllByIdIn(ids);
        userRoleService.deleteAllByUserIdIn(ids);
    }

    /*
     * 设置角色
     * */
    @Override
    public void setRoles(UserRoleDto roles) {
        List<UserRole> userRole = new ArrayList<>();
        List<UserRole> deleteUserRole = new ArrayList<>();
        Object[] rqRoleIds = roles.getRoles();
        List<UserRole> userRoleList = userRoleService.findByUserId(roles.getUserId());
        List<UserRole> list = new ArrayList<>();
        for (UserRole rr : userRoleList) {
            UserRole userRole1 = new UserRole();
            userRole1.setRoleId(rr.getRoleId());
            userRole1.setUserId(rr.getUserId());
            userRole1.setId(rr.getId());
            list.add(userRole1);
        }

        if (list.size() > 0) {
            for (UserRole userRole1 : list) {
                UserRole entity = new UserRole();
                entity.setRoleId(userRole1.getRoleId());
                entity.setUserId(userRole1.getUserId());
                entity.setId(userRole1.getId());
                deleteUserRole.add(entity);
            }
            userRoleService.delete(deleteUserRole);
        }

        if (rqRoleIds.length > 0) {
            for (Object roleId : rqRoleIds) {
                UserRole entity = new UserRole();
                entity.setRoleId(Long.valueOf(String.valueOf(roleId)).longValue());
                entity.setUserId(roles.getUserId());
                userRole.add(entity);
            }
            userRoleService.saveOrUpdateAll(userRole);
        }
    }
}
