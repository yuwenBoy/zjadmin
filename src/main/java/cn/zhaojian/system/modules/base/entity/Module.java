package cn.zhaojian.system.modules.base.entity;

import cn.zhaojian.system.base.SystemBaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Entity
@Table(name = "t_module")
public class Module extends SystemBaseEntity {
    private String name;
    private String code;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "system_code")
    private String systemCode;
    @Column(name = "menu_path")
    private String menuPath;

    @Column(name = "menu_type")
    private int menuType;

    @Column(name = "index_no")
    private int index;

    private String icon;

    private String permission;

    public Boolean setHasChildren(Boolean hasChildren) {
      return  true;
    }

    public Boolean getHasChildren() {
        return true;
    }
    @Transient
    private Boolean hasChildren;
}
