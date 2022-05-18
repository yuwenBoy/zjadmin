package cn.zhaojian.system.modules.base.controller.manage;

import cn.zhaojian.system.common.utils.PageUtil;
import cn.zhaojian.system.common.utils.ResultUtil;
import cn.zhaojian.system.common.vo.PageVo;
import cn.zhaojian.system.common.vo.Result;
import cn.zhaojian.system.config.security.AnonymousGetMapping;
import cn.zhaojian.system.config.security.AnonymousPostMapping;
import cn.zhaojian.system.modules.base.entity.User;
import cn.zhaojian.system.modules.base.service.UserService;
import cn.zhaojian.system.modules.base.vo.UserRoleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/jxxqz/api/user")
@Transactional
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    public void exportUser(HttpServlet response,User user) throws IOException {
//        userService.download(userService.queryAll(user),response);
    }
    /*
     * 分页列表
     * */
    @AnonymousGetMapping(value = "/getByCondition")
    public Result<Page<User>> getByCondition(User user, PageVo pageVo) {
        Page<User> page = userService.findByCondition(user, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<User>>().setData(page);
    }

    @AnonymousPostMapping("/uploadAvatar")
    public Result<Object> uploadAvatar(@RequestParam MultipartFile avatar){
        Map<Object,String> map= userService.uploadAvatar(avatar);
         return ResultUtil.data(map);
    }

    /*
    * 新增用户
    * */
    @AnonymousPostMapping("/add")
    public Result<Object> create(@Validated @RequestBody User  resources) {
        // 默认密码 jxxqz123
        resources.setPassword(passwordEncoder.encode("jxxqz123"));
        userService.create(resources);
        return ResultUtil.success("添加成功");
    }

    @AnonymousPostMapping("/edit")
    public Result<Object> update(@Validated @RequestBody User user) {
        userService.update(user);
        return ResultUtil.success("修改成功");
    }

    @AnonymousPostMapping("/delete")
    public Result<Object> delete(@RequestBody Set<Long> ids) {
        userService.delete(ids);
        return ResultUtil.success("删除成功");
    }
    /*
    * 设置角色
    * */
    @AnonymousPostMapping("/setRoles")
    public Result<Object> setRoles(@RequestBody UserRoleDto userRoleDto)
    {
        userService.setRoles(userRoleDto);
        return ResultUtil.success("设置成功");
    }
}
