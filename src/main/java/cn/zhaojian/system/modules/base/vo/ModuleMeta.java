package cn.zhaojian.system.modules.base.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModuleMeta implements Serializable {

        /*
         * 标题名称
         * */
        public String title;

        /*
         * 菜单icon
         * */
        public String icon;

        /*
         * 是否缓存
         * */
        public Boolean noCache;
}
