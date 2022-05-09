package cn.zhaojian.system.modules.base.controller.manage;

import cn.zhaojian.system.common.utils.PageUtil;
import cn.zhaojian.system.common.utils.ResultUtil;
import cn.zhaojian.system.common.vo.PageVo;
import cn.zhaojian.system.common.vo.Result;
import cn.zhaojian.system.config.security.AnonymousGetMapping;
import cn.zhaojian.system.config.security.AnonymousPostMapping;
import cn.zhaojian.system.modules.base.entity.Department;
import cn.zhaojian.system.modules.base.service.DepartmentService;
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
@RequestMapping("/jxxqz/api/department")
@Transactional
public class DepartmentController {

    @Autowired
    public DepartmentService departmentService;


    @AnonymousGetMapping("/getByCondition")
    public Result<Page<Department>> getRoleByPage(Department department, PageVo pageVo) {
        Page<Department> page = departmentService.findByCondition(department, PageUtil.initPage(pageVo));
    
        return new ResultUtil<Page<Department>>().setData(page);
    }


//    /*
//    * 获取全部组织信息
//    * */
//    @AnonymousGetMapping("/getDepartmentAll")
//    public Result<Object> getDepartmentAll() {
//        List<DeptSmallDto> deptSmallDto = departmentService.getDepartmentAll();
//        return ResultUtil.data(deptSmallDto);
//    }

    /*
     * 获取全部组织信息
     * */
    @AnonymousGetMapping("/getDepartmentAll")
    public Result<Object> getDepartmentAll() {
        List<Map<String,Object>> deptSmallDto = departmentService.getDepartmentAll();
        return ResultUtil.data(deptSmallDto);
    }



    /*
    * 根据pid获取部门信息
    * */
    @AnonymousGetMapping("/getDepartmentByid")
    public Result<Object> getDepartmentByid(Long id){
        List<Map<String,Object>> deptSmallDto = departmentService.getDepartmentByid(id);
        return ResultUtil.data(deptSmallDto);
    }

    @AnonymousPostMapping("/add")
    public Result<Object> create(@Validated @RequestBody Department resources) {
        departmentService.create(resources);
        return ResultUtil.success("添加成功");
    }

    @AnonymousPostMapping("/edit")
    public Result<Object> update(@Validated @RequestBody Department department) {
        departmentService.update(department);
        return ResultUtil.success("修改成功");
    }

    @AnonymousPostMapping("/delete")
    public Result<Object> delete(@RequestBody Set<Long> ids) {
        departmentService.delete(ids);
        return ResultUtil.success("删除成功");
    }

    /*
     * 根据id懒加载方式获取
     * */
    @AnonymousGetMapping("/getDeptTree")
    public Result<Object> getDeptTree(Long id) {
        List<Map<String, Object>> list = departmentService.getDeptTree(id);
        return ResultUtil.data(list);
    }
}