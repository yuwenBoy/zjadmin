package cn.zhaojian.system.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.zhaojian.system.common.vo.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {
    private final static String[] KEYWORDS = {"master", "truncate", "insert", "select",
            "delete", "update", "declare", "alter", "drop", "sleep"};

    /**
     * JPA分页封装
     * @param page
     * @return
     */
    public static Pageable initPage(PageVo page){

        Pageable pageable = null;
        int pageNumber = page.getPage();
        int pageSize = page.getSize();
        String sort = page.getSort();
        String order = page.getOrder();

        if(pageNumber<1){
            pageNumber = 1;
        }
        if(pageSize<1){
            pageSize = 10;
        }
        if(pageSize>100){
            pageSize = 100;
        }
        if(StrUtil.isNotBlank(sort)) {
            Sort.Direction d;
            if(StrUtil.isBlank(order)) {
                d = Sort.Direction.DESC;
            } else {
                d = Sort.Direction.valueOf(order.toUpperCase());
            }
            Sort s = Sort.by(d, sort);
            pageable = PageRequest.of(pageNumber-1, pageSize, s);
        } else {
            pageable = PageRequest.of(pageNumber-1, pageSize);
        }
        return pageable;
    }
}
