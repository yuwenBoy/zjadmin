package cn.zhaojian.system.modules.base.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Data
@Getter
@Setter
public class RoleModuleDto implements Serializable {
    private Long roleId;
    private Long[] moduleId;
}
