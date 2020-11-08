package cn.zhaojian.system.modules.base.entity;

import cn.zhaojian.system.base.SystemBaseEntity;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Entity;
import javax.persistence.*;

/**
 * 职位表
 * */
@Data
@Entity
@Table(name = "t_position")
public class Position extends SystemBaseEntity {
    private String name;

    @Column(name = "code")
    private String Code;

    @OneToOne
    @JoinColumn(name = "t_department_id",referencedColumnName = "id")
    private Department dept;
}
