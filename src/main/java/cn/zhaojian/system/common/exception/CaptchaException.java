package cn.zhaojian.system.common.exception;


import lombok.Data;

/**
 * @author 继续向前走
 */
@Data
public class CaptchaException extends RuntimeException {

    private String msg;

    public CaptchaException(String msg){
        super(msg);
        this.msg = msg;
    }
}
