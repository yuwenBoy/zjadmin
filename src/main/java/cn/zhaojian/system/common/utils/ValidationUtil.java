package cn.zhaojian.system.common.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.zhaojian.system.common.exception.BabException;

/*
 * 验证工具
 * @author 继续向前走
 * @date 2020年9月3日10:19:46
 * */
public class ValidationUtil {
    /*
     * 验证空
     * */
    public static void isNull(Object obj, String entity, String parameter, Object value) {
        if (ObjectUtil.isNull(obj)) {
            String msg = entity + "不存在：" + parameter + "is" + value;
            throw new BabException(msg);
        }
    }
}
