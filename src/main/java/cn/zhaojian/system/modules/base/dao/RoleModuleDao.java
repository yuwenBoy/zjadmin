package cn.zhaojian.system.modules.base.dao;

import cn.zhaojian.system.base.SystemBaseDao;
import cn.zhaojian.system.modules.base.entity.Module;
import cn.zhaojian.system.modules.base.entity.RoleModule;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface RoleModuleDao extends SystemBaseDao<RoleModule,String> {
     void deleteAllByModuleIdIn(Set<Long> ids);

    List<RoleModule> findByRoleId(Long roleId);
}
