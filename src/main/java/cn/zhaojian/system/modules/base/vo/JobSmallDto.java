package cn.zhaojian.system.modules.base.vo;

import cn.zhaojian.system.base.SystemBaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class JobSmallDto  extends SystemBaseEntity {
    private String name;
    private String code;
}
