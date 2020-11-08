package cn.zhaojian.system.modules.base.utils;

import cn.hutool.json.JSONObject;
import cn.zhaojian.system.common.exception.BabException;
import cn.zhaojian.system.common.exception.CaptchaException;
import cn.zhaojian.system.common.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Slf4j
/**
 * 获取当前登录的用户
 * */
public class SecurityUtils {

    /**
     * SecurityContextHolder用户存储安全上下文Security context的信息。当前操作的用户是谁，该用户是否已经被认证，
     * 他拥有哪些角色权限...这些都被保存在SecurityContextHolder中。SecurityContextHolder默认使用ThreadLocal
     * 策略来存储认证信息。看到ThreadLocal也就意味着，这是一种与线程绑定的策略。Spring Security在用户登录时
     * 自动绑定认证信息到当前线程，在用户退出时，自动清除当前线程的认证信息。但这一切前提，是你在web场景下使用
     * Spring Security。
     */

    /*
    * 获取系统用户名称
    * @return 系统用户名称
    * */
    public static String getCurrentUsername(){
        final Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null){
            throw new BabException(HttpStatus.UNAUTHORIZED,"当前登录状态过期");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return  userDetails.getUsername();
    }
    /*
     * 获取当前登录的用户
     * */
    public static UserDetails getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BabException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
            return userDetailsService.loadUserByUsername(userDetails.getUsername());
        }
        throw  new BabException(HttpStatus.UNAUTHORIZED,"找不到当前登录的信息");
    }

    /*
    * 获取系统用户ID
    * @return 系统用户ID
    * */
    public static Long getCurrentUserId(){
        UserDetails userDetails = getCurrentUser();
        return new JSONObject(new JSONObject(userDetails).get("user")).get("id",Long.class);
    }
}
