package cn.zhaojian.system.modules.base.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class ModuleTreeDto implements Serializable {
    private String label;
    private Long id;
    private List<ModuleTreeDto> children =new ArrayList<>();
}
