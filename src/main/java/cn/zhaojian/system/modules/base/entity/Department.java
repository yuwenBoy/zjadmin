package cn.zhaojian.system.modules.base.entity;

import cn.zhaojian.system.base.SystemBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


@Data
@Entity
@Table(name = "t_department")
public class Department extends SystemBaseEntity {
    @Column(name = "department_name")
    private String DepartmentName;

    @Column(name = "department_code")
    private String DepartmentCode;

    @Column(name = "parent_id")
    private Long pid;

    @Column(name = "department_type")
    private Integer DepartmentType;

    public Boolean setHasChildren(Boolean hasChildren) {
        return  true;
    }

    public Boolean getHasChildren() {
        return true;
    }
    @Transient
    private Boolean hasChildren;
}