package cn.zhaojian.system.modules.base.serviceimpl;

import cn.hutool.core.util.StrUtil;
import cn.zhaojian.system.modules.base.dao.NewsDao;
import cn.zhaojian.system.modules.base.entity.News;
import cn.zhaojian.system.modules.base.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsDao newsDao;

    @Override
    public NewsDao getRepository() {
        return newsDao;
    }

    @Override
    public Page<News> findByCondition(News news, Pageable pageable) {
        return newsDao.findAll(new Specification<News>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> listAnd = new ArrayList<Predicate>();
                List<Predicate> listOr = new ArrayList<>();///组装or语句
                Path<String> titleField = root.get("title");
                Path<Integer> statusField = root.get("status");
                //模糊搜素
                if (StrUtil.isNotBlank(news.getTitle())) {
                    listAnd.add(cb.like(titleField, '%' + news.getTitle() + '%'));
                }
                if(news.getStatus() == 1){
                    listOr.add(cb.and(cb.equal(statusField,news.getStatus())));
                    listOr.add(cb.and(cb.equal(statusField,2)));
                }
                else{
                    listAnd.add(cb.and(cb.equal(statusField,news.getStatus())));
                }
                Predicate predicateAnd = cb.and(listAnd.toArray(new Predicate[listAnd.size()])); //AND查询加入
                if(listOr.size()>0){
                    Predicate[] psOr = new Predicate[listOr.size()];
                    Predicate preOr = cb.or(listOr.toArray(psOr));
                    cq.where(predicateAnd,preOr).getRestriction();
                }else{
                    cq.where(predicateAnd).getRestriction();
                }
                return null;
            }
        }, pageable);
    }

    /*
     * 保存信息
     * */
    @Override
    public void create(News news) {
        String text = HtmlUtils.htmlUnescape(news.getContent());
        news.setContent(text);
        newsDao.save(news);
    }

    @Override
    public void delete(Set<Long> ids) {
        newsDao.deleteAllByIdIn(ids);
    }

    @Override
    public void apply(Long id, int status, Date pubdate){
        newsDao.apply(id,status,pubdate);
    }
}
