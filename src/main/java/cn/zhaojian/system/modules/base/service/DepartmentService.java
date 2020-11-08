package cn.zhaojian.system.modules.base.service;

import cn.zhaojian.system.base.SystemBaseService;
import cn.zhaojian.system.common.vo.SearchVo;
import cn.zhaojian.system.modules.base.entity.Department;
import cn.zhaojian.system.modules.base.entity.Module;
import cn.zhaojian.system.modules.base.vo.DeptSmallDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public  interface DepartmentService extends SystemBaseService<Department,String> {
    /**
     * 多条件分页获取用户
     * @param department
     * @param pageable
     * @return
     */
    Page<Department> findByCondition(Department department, Pageable pageable);

    /*
    * 获取所有部门
    * */
    List<Map<String,Object>> getDepartmentAll();

    /*
    *根据pid获取部门信息
    * */
    List<Map<String,Object>> getDepartmentByid(Long pid);

    /*
     *创建
     * */
    void create(Department module);

    public void delete(Set<Long> ids);

    /*
     * 根据id懒加载方式获取
     * */
    List<Map<String,Object>> getDeptTree(Long id);
}
