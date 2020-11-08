package cn.zhaojian.system.config.service;

import cn.zhaojian.system.common.utils.StringUtils;
import cn.zhaojian.system.modules.base.entity.User;
import org.springframework.stereotype.Component;

/*
* @Author:继续向前走
* @date:2020年8月26日13:54:54
* @apiNote:用于清理 用户登录信息缓存，为防止spring循环依赖与安全考虑，单独构成工具类
* */
@Component
public class UserCacheClean {

    /*
    * 清理特定用户缓存信息
    * 用户信息变更时
    * */
    public void cleanUserCache(String userName){
        if(StringUtils.isNotBlank(userName)){
            UserDetailsServicelmpl.userDtoMap.remove(userName);
        }
    }

    /**
     * 清理所有信息缓存
     * */
    public void cleanAll(){
        UserDetailsServicelmpl.userDtoMap.clear();
    }
}
