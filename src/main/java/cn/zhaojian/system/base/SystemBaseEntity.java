package cn.zhaojian.system.base;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class SystemBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull(groups = Update.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    @Column(name = "create_by", updatable = false)
    private String createBy;

    @LastModifiedBy
    @Column(name = "update_by")
    private String updatedBy;
    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private Timestamp createtime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updatetime;
}


