package cn.zhaojian.system.modules.base.dao;

import cn.zhaojian.system.base.SystemBaseDao;
import cn.zhaojian.system.modules.base.entity.Position;
import cn.zhaojian.system.modules.base.entity.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleDao  extends SystemBaseDao<Role,String> {
    Role findByName(String name);

    /**
     * 根据Id删除
     * @param ids /
     */
    void deleteAllByIdIn(Set<Long> ids);
}
