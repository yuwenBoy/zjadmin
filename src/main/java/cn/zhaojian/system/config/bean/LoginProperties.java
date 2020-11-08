package cn.zhaojian.system.config.bean;

import cn.zhaojian.system.common.enums.LoginCodeEnum;
import cn.zhaojian.system.common.exception.CaptchaException;
import cn.zhaojian.system.common.utils.StringUtils;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;

import java.awt.*;
import java.util.Objects;

/*
*配置文件读取
* **/
public class LoginProperties {
    /**
     * 账号单用户 登录
     * */
    private boolean singleLogin=false;

    private LoginCode loginCode;

    /**
     * 用户登录信息缓存
     * */
    private boolean cacheEnable;

    public LoginProperties(){}

    public boolean isSingleLogin(){return singleLogin;}
    public boolean isCacheEnable(){return cacheEnable;}

    /**
     * 获取验证码生产类
     * */
    public Captcha getCaptcha(){
        if(Objects.isNull(loginCode)){
            loginCode=new LoginCode();
            if(Objects.isNull(loginCode.getCodeType())){
                loginCode.setCodeType(LoginCodeEnum.arithmetic);
            }
        }
        return switchCaptcha(loginCode);
    }

    /**
     * 依据配置信息生产验证码
     * @param loginCode 验证码配置信息
     * @return/
    */
    private  Captcha switchCaptcha(LoginCode loginCode)
    {
        Captcha captcha;
        synchronized (this){
            switch (loginCode.getCodeType()){
                case arithmetic:
                    captcha=new ArithmeticCaptcha(loginCode.getWidth(),loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
//                case chinese:
//                    break;
//                case chinese_gif:
//                    break;
//                case gif:
//                    break;
//                case spec:
//                    break;
                default:
                    throw new CaptchaException("验证码配置信息错误！正确配置查看LoginCodeEnum");
            }
        }
        if(StringUtils.isNotBlank(loginCode.getFontName())){
            captcha.setFont(new Font(loginCode.getFontName(),Font.PLAIN,loginCode.getFontSize()));
        }
        return captcha;
    }



    public LoginCode getLoginCode(){return  this.loginCode;}

    public void setSingleLogin(boolean singleLogin){this.singleLogin=singleLogin;}
    public void setLoginCode(LoginCode loginCode){this.loginCode=loginCode;}
    public void setCacheEnable(boolean cacheEnable){this.cacheEnable=cacheEnable;}

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof LoginProperties)) return false;
        final LoginProperties other = (LoginProperties) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.isSingleLogin() != other.isSingleLogin()) return false;
        final Object this$loginCode = this.getLoginCode();
        final Object other$loginCode = other.getLoginCode();
        if (this$loginCode == null ? other$loginCode != null : !this$loginCode.equals(other$loginCode)) return false;
        if (this.isCacheEnable() != other.isCacheEnable()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof LoginProperties;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isSingleLogin() ? 79 : 97);
        final Object $loginCode = this.getLoginCode();
        result = result * PRIME + ($loginCode == null ? 43 : $loginCode.hashCode());
        result = result * PRIME + (this.isCacheEnable() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "LoginProperties(singleLogin=" + this.isSingleLogin() + ", loginCode=" + this.getLoginCode() + ", cacheEnable=" + this.isCacheEnable() + ")";
    }
}

