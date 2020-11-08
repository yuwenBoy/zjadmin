package cn.zhaojian.system.modules.base.controller.common;

import cn.zhaojian.system.common.exception.BabException;
import cn.zhaojian.system.common.utils.ResultUtil;
import cn.zhaojian.system.common.utils.RsaUtils;
import cn.zhaojian.system.modules.base.utils.SecurityUtils;
import cn.zhaojian.system.common.utils.StringUtils;
import cn.zhaojian.system.common.vo.Result;
import cn.zhaojian.system.config.bean.SecurityProperties;
import cn.zhaojian.system.config.properties.RsaProperties;
import cn.zhaojian.system.config.security.AnonymousGetMapping;
import cn.zhaojian.system.config.security.AnonymousPostMapping;
import cn.zhaojian.system.config.security.TokenProvider;
import cn.zhaojian.system.config.service.OnlineUserService;
import cn.zhaojian.system.modules.base.vo.AuthUserDto;
import cn.zhaojian.system.modules.base.vo.JwtUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/jxxqz/auth")
public class AuthorizationController {
    private final SecurityProperties properties;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthorizationController(SecurityProperties properties, OnlineUserService onlineUserService, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.properties = properties;
        this.onlineUserService = onlineUserService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @AnonymousPostMapping(value = "/login")
    public Result<Object> login(@Validated @RequestBody AuthUserDto authUserDto,HttpServletRequest request) throws Exception {

        // 密码解密
        String passwrod = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUserDto.getPassword());
        // 查询验证码
        String code = redisTemplate.opsForValue().get(authUserDto.getUuid());
        // 清除验证码
        redisTemplate.delete(authUserDto.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BabException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(authUserDto.getCode()) || !authUserDto.getCode().equalsIgnoreCase((code))) {
            throw new BabException("验证码错误");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authUserDto.getUsername(), passwrod);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // 保存在线信息
                onlineUserService.save(jwtUserDto, token, request);
        Map<String, Object> authInfo = new HashMap<String, Object>();
        authInfo.put("token", properties.getTokenStartWith() + token);
        authInfo.put("user", jwtUserDto);

        return ResultUtil.data(authInfo);
    }

    @AnonymousGetMapping("/logout")
    public Result<Object> logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
        return ResultUtil.success("退出成功");
    }

    /*
     * 获取当前用户信息
     * */
    @GetMapping("/getUserInfo")
    public Result<Object> getCurrent() {
        return ResultUtil.data(SecurityUtils.getCurrentUser());
    }
}
