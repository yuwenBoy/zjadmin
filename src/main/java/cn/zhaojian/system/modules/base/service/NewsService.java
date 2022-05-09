package cn.zhaojian.system.modules.base.service;

import cn.zhaojian.system.base.SystemBaseService;
import cn.zhaojian.system.modules.base.entity.News;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Set;

/*
 * 公告模块接口
 * @author 继续向前走
 * */
@CacheConfig(cacheNames = "news")
public interface NewsService extends SystemBaseService<News, String> {
    /**
     * 多条件分页获取
     *
     * @param news
     * @param pageable
     * @return
     */
    Page<News> findByCondition(News news, Pageable pageable);

    /*
     * 添加公告
     * */
    void create(News news);

    void delete(Set<Long> ids);

    void apply(Long id, int status, Date pubdate);

    News detail(Long id);
}
