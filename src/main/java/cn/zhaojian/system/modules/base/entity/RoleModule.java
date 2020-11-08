package cn.zhaojian.system.modules.base.entity;

import cn.zhaojian.system.base.SystemBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/*
 * 角色模块关联表
 * */
@Data
@Entity
@Table(name = "t_role_module")
public class RoleModule extends SystemBaseEntity {

    @Column(name="t_module_id")
    private Long moduleId;

    @Column(name="t_role_id")
    private Long roleId;

    @Column(name="system_code")
    private String systemCode;
}
