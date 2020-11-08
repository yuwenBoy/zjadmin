package cn.zhaojian.system.common.exception;

public class JxxqzException extends RuntimeException {
    private String msg;
    public JxxqzException(String msg){
        super(msg);
        this.msg=msg;
    }
}
