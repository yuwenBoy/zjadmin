package cn.zhaojian.system.modules.base.serviceimpl;

import cn.zhaojian.system.modules.base.dao.RoleModuleDao;
import cn.zhaojian.system.modules.base.entity.RoleModule;
import cn.zhaojian.system.modules.base.service.RoleModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class RoleModulelmpl implements RoleModuleService {
    @Autowired
    public RoleModuleDao roleModuleDao;

    @Override
    public RoleModuleDao getRepository() {
        return roleModuleDao;
    }

    @Override
    public void deleteAllByModuleIdIn(Set<Long> ids) {
        roleModuleDao.deleteAllByModuleIdIn(ids);
    }

    @Override
    public List<RoleModule> findByRoleId(Long roleId) {
        return roleModuleDao.findByRoleId(roleId);
    }
}
