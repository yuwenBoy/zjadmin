package cn.zhaojian.system.config.service;

import cn.zhaojian.system.common.exception.BabException;
import cn.zhaojian.system.config.bean.LoginProperties;
import cn.zhaojian.system.modules.base.service.RoleService;
import cn.zhaojian.system.modules.base.service.UserService;
import cn.zhaojian.system.modules.base.vo.JwtUserDto;
import cn.zhaojian.system.modules.base.vo.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("userDetailService")
public class UserDetailsServicelmpl implements UserDetailsService {

    private final LoginProperties loginProperties;
    private final UserService userService;
    private final RoleService roleService;

    public UserDetailsServicelmpl(LoginProperties loginProperties, UserService userService, RoleService roleService) {
        this.loginProperties = loginProperties;
        this.userService = userService;
        this.roleService = roleService;
    }

    static Map<String, JwtUserDto> userDtoMap = new ConcurrentHashMap<>();

    @Override
    public JwtUserDto loadUserByUsername(String username) {
        boolean searchDb = true;
        JwtUserDto jwtUserDto = null;
        if (loginProperties.isCacheEnable() && userDtoMap.containsKey(username)) {
            jwtUserDto = userDtoMap.get(username);
            searchDb = false;
        }
        if (searchDb) {
            UserDto userDto;
            try {
                userDto = userService.findByUserName(username);
            } catch (EntityNotFoundException e) {
                throw new UsernameNotFoundException("用户不存在，请联系系统管理员", e);
            }
            if (userDto == null) {
                return null;
            } else {
                if (userDto.getIsdisabled() == 1) {
                    throw new BabException("账号未激活，请联系管理员开通。");
                }
                jwtUserDto = new JwtUserDto(userDto, null, roleService.mapToGrantedAuthorities(userDto));
                userDtoMap.put(username, jwtUserDto);
            }
        }
        return jwtUserDto;
    }
}