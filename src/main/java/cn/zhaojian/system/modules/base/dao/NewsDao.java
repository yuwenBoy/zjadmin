package cn.zhaojian.system.modules.base.dao;

import cn.zhaojian.system.base.SystemBaseDao;
import cn.zhaojian.system.modules.base.entity.News;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Set;

public interface NewsDao extends SystemBaseDao<News,String> {
    /**
     * 根据Id删除
     * @param ids /
     */
    void deleteAllByIdIn(Set<Long> ids);
    @Modifying
    @Query(value = "update c_news set status= ?2,pubdate = ?3 where id= ?1 ",nativeQuery = true)
    void apply(Long id, int status, Date pubdate);
}
