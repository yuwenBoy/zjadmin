package cn.zhaojian.system.modules.base.serviceimpl;


import cn.hutool.core.util.StrUtil;
import cn.zhaojian.system.modules.base.dao.DepartmentDao;
import cn.zhaojian.system.modules.base.entity.Department;
import cn.zhaojian.system.modules.base.service.DepartmentService;
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
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    public DepartmentDao departmentDao;

    @Override
    public DepartmentDao getRepository() {
        return departmentDao;
    }

    @Override
    public Page<Department> findByCondition(Department module, Pageable pageable) {
        return departmentDao.findAll(new Specification<Department>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.equal(root.get("pid"), module.getPid()));
                Path<String> nameField = root.get("DepartmentName");
                if (StrUtil.isNotBlank(module.getDepartmentName())) {
                    list.add(cb.like(nameField, '%' + module.getDepartmentName() + '%'));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public List<Map<String,Object>> getDepartmentAll(){
        List<Department> departments = departmentDao.findAll();
        List<Map<String,Object>> mapList=new ArrayList<>();
        for(Department dt:departments){
            Map<String,Object> map= new HashMap<>();
            map.put("label",dt.getDepartmentName());
            map.put("value",dt.getId());
            map.put("pid",dt.getPid());
            mapList.add(map);
        }
        return mapList;
//        List<DeptSmallDto> deptSmallDtos =new ArrayList<>();
//        List<Department> departments = departmentDao.findAll();
//        for (Department dept : departments){
//            DeptSmallDto deptSmallDto =new DeptSmallDto();
//            deptSmallDto.setLabel(dept.getDepartmentName());
//            deptSmallDto.setValue(dept.getId());
//            deptSmallDtos.add(deptSmallDto);
//        }
//        return deptSmallDtos;
    }

//    @Data
//    public class deptDto {
//        private Long id;
//        private String name;
//        private Long pid;
//    }

//    @Override
//    public List<DeptSmallDto> getDepartmentAll() {
//        List<Department> departments = departmentDao.findAll();
//        List<DeptSmallDto> deptSmallDtos = new ArrayList<>();
//        for (Department dept : departments) {
//               if(dept.getPid()==0){
//                   DeptSmallDto smallDto = new DeptSmallDto();
//                   smallDto.setLabel(dept.getDepartmentName());
//                   smallDto.setValue(dept.getId());
//                   List<DeptSmallDto> childTree = getChild(dept.getId());
//                   if(childTree.size()>0){
//                       smallDto.setChildren(childTree);
//                   }
//                   deptSmallDtos.add(smallDto);
//               }
//        }
//        return deptSmallDtos;
//    }
//
//    public List<DeptSmallDto> getChild(Long parentid){
//        List<DeptSmallDto> trees = new ArrayList<>();
//        List<Department> departments = departmentDao.findByPid(parentid);
//        for(Department d:departments){
//            DeptSmallDto  tree = new DeptSmallDto();
//            tree.setValue(d.getId());
//            tree.setLabel(d.getDepartmentName());
//            List<DeptSmallDto> childTree = getChild(d.getId());
//            if(childTree.size()>0){
//                tree.setChildren(childTree);
//            }
//
//            trees.add(tree);
//        }
//        return  trees;
//    }

    /*
    * 根据pid获取部门信息
    * */
    @Override
    public List<Map<String,Object>> getDepartmentByid(Long id){
        List<Map<String,Object>> mapList=new ArrayList<>();
        List<Department> list = departmentDao.findByPidOrderByIdDesc(id);
        for(Department dt:list){
            Map<String,Object> map = new HashMap<>();
            map.put("label",dt.getDepartmentName());
            map.put("value",dt.getId());
            map.put("pid",dt.getPid());
            mapList.add(map);
        }
        return mapList;
    }

    /*
     * 保存信息
     * */
    @Override
    public void create(Department module) {
        departmentDao.save(module);
    }

    @Override
    public void delete(Set<Long> ids) {
        departmentDao.deleteAllByIdIn(ids);
    }

    /*
     * 根据id懒加载方式获取
     * */
    @Override
    public List<Map<String, Object>> getDeptTree(Long id) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Department> departments = departmentDao.findByPidOrderByIdDesc(id);
        for (Department department : departments) {
            Map<String, Object> map = new HashMap<>();
            List<Department> list = departmentDao.findByPidOrderByIdDesc(department.getId());
            map.put("id", department.getId());
            map.put("label", department.getDepartmentName());
            map.put("hasChildren", list.size()>0?true:false);
            map.put("leaf", list.size()>0?false:true);
            mapList.add(map);
        }
        return mapList;
    }
}