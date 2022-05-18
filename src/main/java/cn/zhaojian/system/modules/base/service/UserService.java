package cn.zhaojian.system.modules.base.service;

import cn.zhaojian.system.base.SystemBaseService;
import cn.zhaojian.system.modules.base.entity.User;
import cn.zhaojian.system.modules.base.vo.UserDto;
import cn.zhaojian.system.modules.base.vo.UserRoleDto;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * 用户接口
 * @author 继续向前走
 * */
@CacheConfig(cacheNames = "user")
public interface UserService extends SystemBaseService<User, String> {
    /*
     * 根据用户名查询
     * */
    UserDto findByUserName(String username);


    /**
     * 上传头像
     *
     * @param file 文件
     * @return /
     */
    Map<Object, String> uploadAvatar(MultipartFile file);

    /**
     * 导出数据
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<UserDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 多条件分页获取用户
     *
     * @param user
     * @param pageable
     * @return
     */
    Page<User> findByCondition(User user, Pageable pageable);

    /*
     * 新增用户
     * */
    void create(User user);


    /*
     * 删除用户
     * */
    void delete(Set<Long> ids);

    /*
    * 设置角色
    * */
    void setRoles(UserRoleDto userRoleDto);
}
