package cn.zhaojian.system.modules.base.serviceimpl;

import cn.hutool.core.util.StrUtil;
import cn.zhaojian.system.common.exception.BabException;
import cn.zhaojian.system.common.exception.EntityExistException;
import cn.zhaojian.system.common.utils.ValidationUtil;
import cn.zhaojian.system.common.vo.Result;
import cn.zhaojian.system.common.vo.SearchVo;
import cn.zhaojian.system.modules.base.dao.PositionDao;
import cn.zhaojian.system.modules.base.entity.Department;
import cn.zhaojian.system.modules.base.entity.Position;
import cn.zhaojian.system.modules.base.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.awt.peer.ScrollbarPeer;
import java.util.*;

@Slf4j
@Service
@Transactional
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionDao positionDao;

    @Override
    public PositionDao getRepository() {
        return positionDao;
    }

    @Override
    public Page<Position> findByCondition(Position position, Pageable pageable) {
        // Specification<Department> specification = new
        return positionDao.findAll(new Specification<Position>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Position> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Path<String> nameField = root.get("name");
                List<Predicate> list = new ArrayList<Predicate>();
                Root<Department> cRoot = cq.from(Department.class);
                list.add(cb.equal(root.get("dept"), cRoot.get("id")));
                //模糊搜素
                if (StrUtil.isNotBlank(position.getName())) {
                    list.add(cb.like(nameField, '%' + position.getName() + '%'));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));


                return null;
            }
        }, pageable);
    }

    /*
     * 保存职位信息
     * */
    @Override
    public void create(Position position) {
        Position position1 = positionDao.findByName(position.getName());
        if (position1 != null) {
            throw new BabException("职位名称已存在，请重新添加。");
        }
        //  position.setDept(1);
        positionDao.save(position);
    }

    @Override
    public void delete(Set<Long> ids) {
        positionDao.deleteAllByIdIn(ids);
    }

    @Override
    public List<Map<String,Object>> getPositionList(String userName) {
        List<Position> list = positionDao.findByCreateBy(userName);
        List<Map<String,Object>> mapList=new ArrayList<>();
        for(Position p:list){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("value",p.getId());
            map.put("label",p.getName());
            mapList.add(map);
        }
        return mapList;
    }
}
