package cn.zhaojian.system.modules.base.service;

import cn.zhaojian.system.base.SystemBaseService;
import cn.zhaojian.system.modules.base.entity.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*
* @Author:继续向前走
* @Description：职位接口
* */
public interface PositionService extends SystemBaseService<Position,String> {
    /**
     * 多条件分页获取用户
     * @param position
     * @param pageable
     * @return
     */
    Page<Position> findByCondition(Position position, Pageable pageable);

    /*
    * 保存职位信息
    * */
    void create(Position position);


    void delete(Set<Long> ids);

    /*
    * 获取职位列表
    * */
    List<Map<String,Object>> getPositionList();
}