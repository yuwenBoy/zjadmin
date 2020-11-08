package cn.zhaojian.system.modules.base.entity;
import cn.zhaojian.system.base.SystemBaseEntity;
import lombok.*;
import javax.persistence.*;
import java.io.File;
import java.util.Date;

@Entity
@Data
@Table(name="t_user")
public class User extends SystemBaseEntity {
    @Column(name = "username",unique = true)
    private String userName;
    private String phone;
    @Column(name = "password")
    private String Password;

    private String cname;

    private String email;
    @Column(name = "address")
    private String Address;


    public String getAvatar() {
        String strFileName=null;
        if (Avatar != null) {
            File tempFile = new File(Avatar);
            strFileName = tempFile.getName();
        }
        return strFileName;
    }

    @Column(name = "avatar")
    private String Avatar;


    @Column(name = "isdisabled")
    private Integer Isdisabled;

    @OneToOne
    @JoinColumn(name = "position_id",referencedColumnName = "id")
    private Position position;

    @OneToOne
    @JoinColumn(name = "dept_id",referencedColumnName = "id")
    private Department dept;

    @Transient
    private String departmentId;

    private Date birthday;
    private int sex;

}

