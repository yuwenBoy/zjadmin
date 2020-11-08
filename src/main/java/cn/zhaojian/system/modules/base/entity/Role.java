package cn.zhaojian.system.modules.base.entity;

import cn.zhaojian.system.base.SystemBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "t_role")
public class Role extends SystemBaseEntity {
    private String name;
    private String code;
    private Integer parentId;
    private String SystemCode;

    private String remark;
}
