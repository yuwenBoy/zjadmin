package cn.zhaojian.system.modules.base.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ModuleListDto implements Serializable {
    private String name;
    private String path;
    private Boolean hidden;
    private String component;
    private ModuleMeta meta;
    private List<ModuleListDto> children =new ArrayList<>();
    public List<ModuleListDto> getChildren() {
        return children;
    }
    public void setChildren(List<ModuleListDto> children) {
        this.children = children;
    }
}
