package cn.zhaojian.system.modules.base.vo;

import java.io.Serializable;
import java.sql.Array;
import java.util.List;

public class UserRoleDto implements Serializable {
    public Long getUserId() {
        return userId;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    private Long userId;

    public UserRoleDto(Long userId, String[] roles) {
        this.userId = userId;
        this.roles = roles;
    }

    public UserRoleDto() {

    }

    private String[] roles;
}
