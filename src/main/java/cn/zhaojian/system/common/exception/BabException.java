package cn.zhaojian.system.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 * 统一异常处理
 */
@Getter
public class BabException extends RuntimeException{

    private Integer status = BAD_REQUEST.value();

    public BabException(String msg){
        super(msg);
    }

    public BabException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }
}