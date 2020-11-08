package cn.zhaojian.system.modules.base.service;

import cn.zhaojian.system.base.SystemBaseService;
import cn.zhaojian.system.common.utils.ResultUtil;
import cn.zhaojian.system.common.vo.Result;
import cn.zhaojian.system.config.security.AnonymousGetMapping;
import cn.zhaojian.system.modules.base.entity.*;
import cn.zhaojian.system.modules.base.vo.ModuleDto;
import cn.zhaojian.system.modules.base.vo.ModuleTreeDto;
import cn.zhaojian.system.modules.base.vo.RoleModuleDto;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ModuleService extends SystemBaseService<Module,String> {

    /*
    * 根据当前用户所属角色获取模块
    * */
    List<Module> findByRoleIdsAndMenuType(Set<Long> roleIds,int menuType);

    /*
    * 构建菜单树
    * */
    List<ModuleDto> moduleToTree(List<Module> modules);

    /*
    * 获取全部菜单
    * */
    public List<ModuleTreeDto> getModuleAll();

    /**
     * 多条件分页获取用户
     * @param module
     * @param pageable
     * @return
     */
    Page<Module> findByCondition(Module module, Pageable pageable);

    /*
    * 根据id懒加载方式获取菜单
    * */
    List<Map<String,Object>> getMenusTree(Long id);

    /*
    *创建菜单
    * */
    void create(Module module);




    void delete(Set<Long> ids);

    /*
     * 根据角色id获取模块id集合
     * */
    List<RoleModule> findByRoleId(Long roleId);

    /*
    * 保存权限
    * */

    void saveRoleModuleId(RoleModuleDto roleModuleDto);


    Set<String> findByRoleIds(Set<Long> roleids);

}
