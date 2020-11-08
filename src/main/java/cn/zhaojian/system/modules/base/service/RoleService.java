package cn.zhaojian.system.modules.base.service;

import cn.zhaojian.system.base.SystemBaseService;
import cn.zhaojian.system.modules.base.entity.Position;
import cn.zhaojian.system.modules.base.entity.Role;
import cn.zhaojian.system.modules.base.vo.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleService extends SystemBaseService<Role,String> {

    /**
     * 多条件分页获取用户
     * @param role
     * @param pageable
     * @return
     */
    Page<Role> findByCondition(Role role, Pageable pageable);

    /**
     * 获取用户权限信息
     * @param user 用户信息
     * @return 权限信息
     */
    List<GrantedAuthority> mapToGrantedAuthorities(UserDto user);

    /*
     * 保存信息
     * */
    void create(Role role);


    void delete(Set<Long> ids);

    /*
    * 获取全部角色
    * */
    List<Map<String,Object>> getRoleAll();

    /*
    * 根据用户id获取角色id集合
    * */
    List<Map<String,Object>> findByUserId(Long userId);
}
