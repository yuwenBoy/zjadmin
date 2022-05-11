package cn.zhaojian.system.modules.base.controller.manage;

import cn.zhaojian.system.common.utils.PageUtil;
import cn.zhaojian.system.common.utils.ResultUtil;
import cn.zhaojian.system.common.vo.PageVo;
import cn.zhaojian.system.common.vo.Result;
import cn.zhaojian.system.config.security.AnonymousGetMapping;
import cn.zhaojian.system.config.security.AnonymousPostMapping;
import cn.zhaojian.system.modules.base.entity.Module;
import cn.zhaojian.system.modules.base.entity.RoleModule;
import cn.zhaojian.system.modules.base.service.ModuleService;
import cn.zhaojian.system.modules.base.service.RoleModuleService;
import cn.zhaojian.system.modules.base.service.UserRoleService;
import cn.zhaojian.system.modules.base.utils.SecurityUtils;
import cn.zhaojian.system.modules.base.vo.ModuleDto;
import cn.zhaojian.system.modules.base.vo.ModuleTreeDto;
import cn.zhaojian.system.modules.base.vo.RoleModuleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/jxxqz/api/module")
@Transactional
public class ModuleController {
    @Autowired
    public ModuleService moduleService;

    @Autowired
    public UserRoleService userRoleService;

    @Autowired
    public RoleModuleService roleModuleService;

    @AnonymousGetMapping("/buildAll")
    public Result<Object> buildAll() {
        Set<Long> roleIds = userRoleService.findByUserRoleId(SecurityUtils.getCurrentUserId());
        List<Module> modules = moduleService.findByRoleIdsAndMenuType(roleIds, 1);
        List<ModuleDto> moduleDtos = moduleService.moduleToTree(modules);
        return ResultUtil.data(moduleDtos);
    }

    @AnonymousGetMapping("/getByCondition")
    public Result<Page<Module>> getRoleByPage(Module module, PageVo pageVo) {
        Page<Module> page = moduleService.findByCondition(module, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Module>>().setData(page);
    }

    /*
     * 根据id懒加载方式获取菜单
     * */
    @AnonymousGetMapping("/getMenusTree")
    public Result<Object> getMenusTree(Long id) {
        List<Map<String, Object>> list = moduleService.getMenusTree(id);
        return ResultUtil.data(list);
    }

//    @AnonymousGetMapping("/getMenuSuperior")
//    public Result<Object> getMenuSuperior(Set<Long> ids){
//        return ResultUtil.data(0);
//    }

    @AnonymousPostMapping("/add")
    public Result<Object> create(@Validated @RequestBody Module resources) {
        moduleService.create(resources);
        return ResultUtil.success("添加成功");
    }

    @AnonymousPostMapping("/edit")
    public Result<Object> update(@Validated @RequestBody Module module) {
        moduleService.update(module);
        return ResultUtil.success("修改成功");
    }

    @AnonymousPostMapping("/delete")
    public Result<Object> delete(@RequestBody Set<Long> ids) {
        moduleService.delete(ids);
        return ResultUtil.success("删除成功");
    }

    /*
     * 获取全部菜单
     * */
    @AnonymousGetMapping("/getModuleTreeAll")
    public Result<Object> getModuleTreeAll() {
        List<ModuleTreeDto> moduleTreeDtoList = moduleService.getModuleAll();
        return ResultUtil.data(moduleTreeDtoList);
    }

    /*
     * 根据角色id获取模块id集合
     * */
    @AnonymousGetMapping("/findByRoleId")
    public Result<Object> findByRoleId(Long roleId) {
        List<RoleModule> mapList = moduleService.findByRoleId(roleId);
        return ResultUtil.data(mapList);
    }

    /*
     * author：zhao.jian
     * date:2020年9月24日11:18:43
     * description：保存权限
     * */
    @AnonymousPostMapping("/saveRoleModuleId")
    public Result<Object> saveRoleModuleId(@RequestBody RoleModuleDto roleModuleDto) {
        moduleService.saveRoleModuleId(roleModuleDto);
        return ResultUtil.success("保存成功");
    }
}
