package cn.zhaojian.system.modules.base.dao;

import cn.zhaojian.system.base.SystemBaseDao;
import cn.zhaojian.system.modules.base.entity.Department;
import cn.zhaojian.system.modules.base.entity.Module;
import cn.zhaojian.system.modules.base.entity.UserRole;
import org.springframework.data.jpa.repository.Query;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public interface ModuleDao extends SystemBaseDao<Module,String> {

    /*
    * 根据当前用户获取所属角色获取菜单
    * */
    @Query(value = "select m.* from t_role_module rm  join t_module m on rm.t_module_id=m.id\n" +
            "where rm.t_role_id in ?1 and m.menu_type!= ?2 order by m.index_no desc",nativeQuery = true)
    List<Module> findByRoleIdsAndMenuType(Set<Long> roleIds,int menuType);

    Module findByName(String name);

    /*
    * 根据pid查询
    * */
    List<Module> findByParentId(Long id);

    /**
     * 根据Id删除
     * @param ids /
     */
    void deleteAllByIdIn(Set<Long> ids);

    /*
    * 根据角色ids获取权限
    * */
    @Query(value = "select m.permission from t_module m\n" +
            "join t_role_module rm on m.id=rm.t_module_id\n" +
            "where rm.t_role_id in ?1",nativeQuery = true)
    Set<String> findByRoleIds(Set<Long> roleIds);
}
