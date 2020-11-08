package cn.zhaojian.system.config.security;

import cn.zhaojian.system.config.bean.SecurityProperties;
import cn.zhaojian.system.config.service.OnlineUserService;
import cn.zhaojian.system.config.service.UserCacheClean;
import cn.zhaojian.system.modules.base.serviceimpl.UserServiceImpl;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;
    private final SecurityProperties properties;
    private final OnlineUserService onlineUserService;
    private final UserCacheClean userCacheClean;

    public TokenConfigurer(TokenProvider tokenProvider, SecurityProperties properties, OnlineUserService onlineUserService, UserCacheClean userCacheClean) {
        this.tokenProvider = tokenProvider;
        this.properties = properties;
        this.onlineUserService = onlineUserService;
        this.userCacheClean = userCacheClean;
    }

    @Override
    public void configure(HttpSecurity http) {
        TokenFilter customFilter = new TokenFilter(tokenProvider, onlineUserService,properties,userCacheClean);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
