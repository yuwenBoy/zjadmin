package cn.zhaojian.system.modules.base.vo;
import javax.validation.constraints.NotBlank;

public class AuthUserDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";

    @Override
    public String toString() {
        return "{username=" + username  + ", password= ******}";
    }

    public @NotBlank String getUsername() {
        return this.username;
    }

    public @NotBlank String getPassword() {
        return this.password;
    }

    public String getCode() {
        return this.code;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
