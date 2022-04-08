package cn.zhaojian.system.modules.base.serviceimpl;

import cn.hutool.core.util.StrUtil;
import cn.zhaojian.system.common.exception.BabException;
import cn.zhaojian.system.modules.base.dao.RoleDao;
import cn.zhaojian.system.modules.base.entity.Module;
import cn.zhaojian.system.modules.base.entity.Role;
import cn.zhaojian.system.modules.base.service.ModuleService;
import cn.zhaojian.system.modules.base.service.RoleService;
import cn.zhaojian.system.modules.base.service.UserRoleService;
import cn.zhaojian.system.modules.base.vo.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class RoleServicelmpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private ModuleService moduleService;

    @Override
    public RoleDao getRepository() {
        return roleDao;
    }

    @Override
    public Page<Role> findByCondition(Role role, Pageable pageable) {
        return roleDao.findAll(new Specification<Role>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Path<String> nameField = root.get("name");
                Path<String> codeField = root.get("code");
                List<Predicate> list = new ArrayList<Predicate>();
//                //模糊搜素
                if (StrUtil.isNotBlank(role.getName())) {
                    list.add(cb.or(cb.like(nameField, '%' + role.getName() + '%'),cb.like(codeField, '%' + role.getName() + '%')));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    /**
     * 获取用户权限信息
     * @param user 用户信息
     * @return 权限信息
     */
    @Override
    //@Cacheable(key = "'auth:' + #p0.id")
    public List<GrantedAuthority> mapToGrantedAuthorities(UserDto user) {
        Set<String> permissions = new HashSet<>();
        Set<Long> roleIds=userRoleService.findByUserRoleId(user.getId());
        List<Module> modules = moduleService.findByRoleIdsAndMenuType(roleIds, 3);
        for (Module module : modules) {
            if (module.getPermission() != "" && module.getPermission() != null) {
                permissions.add(module.getPermission());
            }
        }
        return permissions.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /*
     * 保存信息
     * */
    @Override
    public void create(Role role) {
        Role role1 = roleDao.findByName(role.getName());
        if (role1 != null) {
            throw new BabException("角色名称已存在。");
        }
        roleDao.save(role);
    }

    @Override
    public void delete(Set<Long> ids) {
        roleDao.deleteAllByIdIn(ids);
    }

    /*
     * 获取全部角色
     * */
    @Override
    public List<Map<String, Object>> getRoleAll() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Role> roles = roleDao.findAll();
        for (Role role : roles) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", role.getName());
            map.put("value", role.getId());
            mapList.add(map);
        }
        return mapList;
    }

    /*
     * 根据用户id获取角色id集合
     * */
    @Override
    public List<Map<String, Object>> findByUserId(Long userId) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Set<Long> roles = userRoleService.findByUserRoleId(userId);
        for (Long role : roles) {
            Map<String, Object> map = new HashMap<>();
            map.put("roleId", role);
            mapList.add(map);
        }
        return mapList;
    }
}
