package cn.zhaojian.system.config.bean;

import cn.zhaojian.system.common.enums.LoginCodeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录验证码配置信息
 * **/
@Data
@Getter
@Setter
public class LoginCode {

    /**
     * 验证码配置
     * */
    private LoginCodeEnum codeType;

    /**
     * 验证码有效期 分钟
     * */
    private Long expiration=2L;

    /**
     * 验证码内容长度
     * */
    private int length=2;

    /**
     * 验证码宽度
     * */
    private int width=111;

    /**
     * 验证码高度
     * */
    private int height=36;

    /**
     * 验证码字体
     * */
    private String fontName;

    /**
     * 验证码大小
     * */
    private int fontSize=25;

    public LoginCode(){}

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof LoginCode)) return false;
        final LoginCode other = (LoginCode) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$codeType = this.getCodeType();
        final Object other$codeType = other.getCodeType();
        if (this$codeType == null ? other$codeType != null : !this$codeType.equals(other$codeType)) return false;
        final Object this$expiration = this.getExpiration();
        final Object other$expiration = other.getExpiration();
        if (this$expiration == null ? other$expiration != null : !this$expiration.equals(other$expiration))
            return false;
        if (this.getLength() != other.getLength()) return false;
        if (this.getWidth() != other.getWidth()) return false;
        if (this.getHeight() != other.getHeight()) return false;
        final Object this$fontName = this.getFontName();
        final Object other$fontName = other.getFontName();
        if (this$fontName == null ? other$fontName != null : !this$fontName.equals(other$fontName)) return false;
        if (this.getFontSize() != other.getFontSize()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof LoginCode;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $codeType = this.getCodeType();
        result = result * PRIME + ($codeType == null ? 43 : $codeType.hashCode());
        final Object $expiration = this.getExpiration();
        result = result * PRIME + ($expiration == null ? 43 : $expiration.hashCode());
        result = result * PRIME + this.getLength();
        result = result * PRIME + this.getWidth();
        result = result * PRIME + this.getHeight();
        final Object $fontName = this.getFontName();
        result = result * PRIME + ($fontName == null ? 43 : $fontName.hashCode());
        result = result * PRIME + this.getFontSize();
        return result;
    }
}
