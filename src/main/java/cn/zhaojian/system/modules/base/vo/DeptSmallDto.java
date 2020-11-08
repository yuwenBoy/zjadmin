package cn.zhaojian.system.modules.base.vo;

import cn.zhaojian.system.base.SystemBaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class DeptSmallDto implements Serializable {
    public Long getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public List<DeptSmallDto> getChildren() {
        return children;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setChildren(List<DeptSmallDto> children) {
        if(children.size()>0)
        {
            this.children= children;
        }
        else {
            this.children=null;
        }
    }

    private Long value;

    @Override
    public String toString() {
        return "DeptSmallDto{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }

    private String label;
    private List<DeptSmallDto> children =new ArrayList<>();
}
