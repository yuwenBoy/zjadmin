package cn.zhaojian.system.common.vo;

import lombok.Data;

import java.io.Serializable;

/*
 * @Author:继续向前走
 * @Description：构建分页对象
 * */
@Data
public class PageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int page;
    private int size;
    private String sort;
    private String order;
}
