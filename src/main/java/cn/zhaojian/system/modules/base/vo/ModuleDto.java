package cn.zhaojian.system.modules.base.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ModuleDto implements Serializable {
    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public String getRedirect() {
        return redirect;
    }

    public String getComponent() {
        return component;
    }

    public Boolean getAlwaysShow() {
        return alwaysShow;
    }

    public ModuleMeta getMeta() {
        return meta;
    }

    public List<ModuleListDto> getChildren() {
        return children;
    }

    /*
    * 菜单名称
    * */
    private String name;

    /*
     * 菜单路径
     * */
    private String path;

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public void setMeta(ModuleMeta meta) {
        this.meta = meta;
    }

    public void setChildren(List<ModuleListDto> children) {
        this.children = children;
    }

    /*
     * 是否隐藏
     * */
    private Boolean hidden;

    /*
     * 页面跳转
     * */
    private String redirect;

    /*
     * 组件名称
     * */
    private String component;

    /*
     * 是否展示
     * */
    private Boolean alwaysShow;

    private ModuleMeta meta;

    private List<ModuleListDto> children =new ArrayList<>();
}

