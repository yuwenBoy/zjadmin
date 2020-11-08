package cn.zhaojian.system.modules.base.dao;

import cn.zhaojian.system.base.SystemBaseDao;
import cn.zhaojian.system.modules.base.entity.Department;

import java.util.List;
import java.util.Set;

public interface DepartmentDao extends SystemBaseDao<Department,String> {
    List<Department> findByPidOrderByIdDesc(Long id);

    /**
     * 根据Id删除
     * @param ids /
     */
    void deleteAllByIdIn(Set<Long> ids);
}
