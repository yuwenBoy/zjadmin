package cn.zhaojian.system.modules.base.serviceimpl;

import cn.hutool.core.util.StrUtil;
import cn.zhaojian.system.common.exception.BabException;
import cn.zhaojian.system.modules.base.dao.ModuleDao;
import cn.zhaojian.system.modules.base.entity.Module;
import cn.zhaojian.system.modules.base.entity.RoleModule;
import cn.zhaojian.system.modules.base.service.ModuleService;
import cn.zhaojian.system.modules.base.service.RoleModuleService;
import cn.zhaojian.system.modules.base.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
@Transactional
public class ModuleServicelmpl implements ModuleService {
    @Autowired
    public ModuleDao moduleDao;

    @Override
    public ModuleDao getRepository() {
        return moduleDao;
    }

    @Autowired
    public RoleModuleService roleModuleService;

    /*
     * 根据当前用户获取所属角色获取模块
     * */
    @Override
    public List<Module> findByRoleIdsAndMenuType(Set<Long> roleIds, int menuType) {
        return moduleDao.findByRoleIdsAndMenuType(roleIds, menuType);
    }

    /*
    * 根据id查询子项数据
    * */
    public List<ModuleListDto> moduleToTreeChild(List<Module> moduleList, Long id) {
        List<ModuleListDto> trees = new ArrayList<>();
        for (Module d : moduleList) {
            if (d.getParentId() == id) {
                ModuleListDto tree = new ModuleListDto();
                ModuleMeta meta = new ModuleMeta();
                tree.setComponent(d.getMenuPath());
                tree.setName(d.getName());
                tree.setPath(d.getMenuPath());
                tree.setHidden(d.getHidden());
                meta.setTitle(d.getName());
                meta.setNoCache(true);
                meta.setIcon(d.getIcon());
                tree.setMeta(meta);
                List<ModuleListDto> children=  moduleToTreeChild(moduleList,d.getId());
                tree.setChildren(children);
                trees.add(tree);
            }
        }
        return trees;
    }


    /*
     * 转化成菜单结果数据
     * */
    public List<ModuleDto> moduleToTree(List<Module> modules) {
        List<ModuleDto> moduleDtos = new ArrayList<>();
        for (Module module : modules) {
            ModuleDto moduleDto = new ModuleDto();
            ModuleMeta meta = new ModuleMeta();
            if (module != null) {
                moduleDto.setName(module.getName());
                moduleDto.setHidden(module.getHidden());

                if (module.getParentId() == 0) {
                    moduleDto.setComponent("Layout");
                    moduleDto.setPath("/" + module.getMenuPath());
                    moduleDto.setRedirect("noredirect");
                    moduleDto.setAlwaysShow(true);
                    meta.setIcon(module.getIcon());
                    meta.setNoCache(true);
                    meta.setTitle(module.getName());
                    moduleDto.setMeta(meta);
                    List<ModuleListDto> childTree = moduleToTreeChild(modules, module.getId());
                    if (childTree.size() > 0) {
                        moduleDto.setChildren(childTree);
                    }
                    moduleDtos.add(moduleDto);
                }
            }
        }
        return moduleDtos;
    }

    /*
     * 获取全部菜单
     * */
    @Override
    public List<ModuleTreeDto> getModuleAll() {
        List<ModuleTreeDto> moduleTreeDtos = new ArrayList<>();
        List<Module> modules = moduleDao.findAll();
        for (Module module : modules) {
            if (module != null) {
                if (module.getParentId() == 0) {
                    ModuleTreeDto moduleTreeDto = new ModuleTreeDto();
                    moduleTreeDto.setId(module.getId());
                    moduleTreeDto.setLabel(module.getName());
                    List<ModuleTreeDto> childTree = getModuleChildTree(module.getId());
                    if (childTree.size() > 0) {
                        moduleTreeDto.setChildren(childTree);
                    }
                    moduleTreeDtos.add(moduleTreeDto);
                }
            }
        }
        return moduleTreeDtos;
    }

    public List<ModuleTreeDto> getModuleChildTree(Long id) {
        List<ModuleTreeDto> trees = new ArrayList<>();
        List<Module> modules = moduleDao.findByParentId(id);
        for (Module module : modules) {
            ModuleTreeDto tree = new ModuleTreeDto();
            tree.setLabel(module.getName());
            tree.setId(module.getId());
            List<ModuleTreeDto> childTree = getModuleChildTree(module.getId());
            if (childTree.size() > 0) {
                tree.setChildren(childTree);
            }
            trees.add(tree);
        }
        return trees;
    }

    @Override
    public Page<Module> findByCondition(Module module, Pageable pageable) {
        return moduleDao.findAll(new Specification<Module>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Module> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.equal(root.get("parentId"), module.getParentId()));
                Path<String> nameField = root.get("name");
                if (StrUtil.isNotBlank(module.getName())) {
                    list.add(cb.like(nameField, '%' + module.getName() + '%'));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    /*
     * 根据id懒加载方式获取菜单
     * */
    @Override
    public List<Map<String, Object>> getMenusTree(Long id) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Module> modules = moduleDao.findByParentId(id);
        for (Module module : modules) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", module.getId());
            map.put("label", module.getName());
            List<Module> childCount = moduleDao.findByParentId(module.getId());
            map.put("hasChildren", childCount.size()>0?true:false);
            map.put("leaf", childCount.size()>0?false:true);
            mapList.add(map);
        }
        return mapList;
    }

    /*
     * 保存信息
     * */
    @Override
    public void create(Module module) {
        Module module1 = moduleDao.findByName(module.getName());
        if (module1 != null) {
            throw new BabException("菜单名称已存在。");
        }
//        Module module2 = moduleDao.findByMenuPath(module.getMenuPath());
//        if (module2 != null) {
//            throw new BabException("菜单路由已存在。");
//        }
        moduleDao.save(module);
    }


    @Override
    public void delete(Set<Long> ids) {
        moduleDao.deleteAllByIdIn(ids);
        roleModuleService.deleteAllByModuleIdIn(ids);
    }

    /*
     * 根据角色id获取模块id集合
     * */
    public List<RoleModule> findByRoleId(Long roleId) {
        List<RoleModule> roleModules = roleModuleService.findByRoleId(roleId);
        return roleModules;
    }

    /*
     * 保存权限
     * */
    public void saveRoleModuleId(RoleModuleDto roleModuleDto) {
        List<RoleModule> oldRoleModule = roleModuleService.findByRoleId(roleModuleDto.getRoleId());
        if (oldRoleModule.size() > 0) {
            roleModuleService.delete(oldRoleModule);
        }

        if (roleModuleDto.getRoleId() > 0) {
            for (Long moduleId : roleModuleDto.getModuleId()) {
                RoleModule roleModule = new RoleModule();
                roleModule.setModuleId(moduleId);
                roleModule.setRoleId(roleModuleDto.getRoleId());
                roleModuleService.save(roleModule);
            }
        }
    }

    public Set<String> findByRoleIds(Set<Long> roleIds) {
        return moduleDao.findByRoleIds(roleIds);
    }
}
