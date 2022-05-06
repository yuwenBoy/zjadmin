package cn.zhaojian.system.modules.base.entity;

import cn.zhaojian.system.base.SystemBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "c_news")
public class News extends SystemBaseEntity {
    private String title;
    private int type;
    private Date pubdate;
    private String content;
    private int priority;
    private int status;
}
