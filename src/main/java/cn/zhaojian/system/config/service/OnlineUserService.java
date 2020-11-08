package cn.zhaojian.system.config.service;

import cn.zhaojian.system.common.utils.EncryptUtils;
import cn.zhaojian.system.common.utils.StringUtils;
import cn.zhaojian.system.config.bean.SecurityProperties;
import cn.zhaojian.system.modules.base.vo.JwtUserDto;
import cn.zhaojian.system.modules.base.vo.OnlineUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;
@Slf4j
@Service
public class OnlineUserService {
    private final SecurityProperties properties;
    @Autowired
    private StringRedisTemplate redisTemplate;
    public OnlineUserService(SecurityProperties properties){
        this.properties=properties;
    }

    /**
     * 查询用户
     * @param key /
     * @return /
     */
    public String getOne(String key) {

        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 保存在线用户信息
     * @param jwtUserDto /
     * @param token /
     * @param request /
     */
    public void save(JwtUserDto jwtUserDto, String token, HttpServletRequest request){
        String dept = "某某部门";//jwtUserDto.getUser().getDept().getName();
        String ip = StringUtils.getIp(request);
        String browser = StringUtils.getBrowser(request);
        String address ="";// StringUtils.getCityInfo(ip);
        OnlineUserDto onlineUserDto = null;
        try {
            onlineUserDto = new OnlineUserDto(jwtUserDto.getUsername(), jwtUserDto.getUser().getCname(), dept, browser , ip, address, EncryptUtils.desEncrypt(token), new Date());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        redisTemplate.opsForValue().set(properties.getOnlineKey() + token, String.valueOf(onlineUserDto), properties.getTokenValidityInSeconds()/1000, TimeUnit.SECONDS);
    }

    /*
    * 退出登录
    * */
    public void logout(String token){
        String key = properties.getOnlineKey()+token;
        redisTemplate.delete(key);
    }
}
