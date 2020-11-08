package cn.zhaojian.system.modules.base.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class ModuleListDto implements Serializable {
    private String name;
    private String path;
    private Boolean hidden;
    private String component;
    private ModuleMeta meta;
}
