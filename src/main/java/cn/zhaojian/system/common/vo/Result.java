package cn.zhaojian.system.common.vo;

import lombok.Data;

import java.io.Serializable;

/*
* @author:继续向前走
* @Date:2020年7月24日14:44:40
* @Description：前后端交互数据标准
* */
@Data
public class Result<T> implements Serializable {
    private static final long seriavVersionUID=1L;

    /*
    * 成功标志
    * */
    private boolean success;

    /**
     * 失败消息
     */
    private String message;

    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 结果对象
     */
    private T result;

}
