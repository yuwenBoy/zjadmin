package cn.zhaojian.system.modules.base.vo;

import cn.zhaojian.system.base.SystemBaseEntity;
import cn.zhaojian.system.modules.base.entity.Department;
import lombok.Data;
import lombok.Setter;
import lombok.Getter;

import java.util.Set;

@Data
@Setter
@Getter
public class UserDto extends SystemBaseEntity {
    private Set<RoleSmallDto> roles;
    private Set<JobSmallDto> jobs;
    private Department dept;
    private Long deptId;
    private String Username;
    private String Password;
    private String cname;
    private String email;
    private String Phone;
    private String address;
    private Integer sex;

    /*
    * 用户状态（0.默认值，1.启用；2.禁用）
    * */
    private Integer isdisabled;
    private String avatar;
}
