package cn.zhaojian.system.modules.base.service;

import cn.zhaojian.system.base.SystemBaseService;
import cn.zhaojian.system.modules.base.entity.Module;
import cn.zhaojian.system.modules.base.entity.RoleModule;

import java.util.List;
import java.util.Set;

public interface RoleModuleService extends SystemBaseService<RoleModule,String> {
     void deleteAllByModuleIdIn(Set<Long> ids);

     List<RoleModule> findByRoleId(Long roleId);

}
