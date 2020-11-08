package cn.zhaojian.system.modules.base.dao;

import cn.zhaojian.system.base.SystemBaseDao;
import cn.zhaojian.system.modules.base.entity.Position;
import cn.zhaojian.system.modules.base.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

public interface PositionDao extends SystemBaseDao<Position,String> {
    Position findByName(String name);

    /**
     * 根据Id删除
     * @param ids /
     */
    void deleteAllByIdIn(Set<Long> ids);

    /*
    * 获取职位列表
    * */
    List<Position> findByCreateBy(String userName);
}
