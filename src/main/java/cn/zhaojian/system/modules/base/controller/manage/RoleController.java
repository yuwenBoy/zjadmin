package cn.zhaojian.system.modules.base.controller.manage;

import cn.zhaojian.system.common.exception.BabException;
import cn.zhaojian.system.common.utils.PageUtil;
import cn.zhaojian.system.common.utils.ResultUtil;
import cn.zhaojian.system.common.vo.PageVo;
import cn.zhaojian.system.common.vo.Result;
import cn.zhaojian.system.config.security.AnonymousGetMapping;
import cn.zhaojian.system.config.security.AnonymousPostMapping;
import cn.zhaojian.system.modules.base.entity.Position;
import cn.zhaojian.system.modules.base.entity.Role;
import cn.zhaojian.system.modules.base.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/jxxqz/api/role")
@Transactional
public class RoleController {
    @Autowired
    private RoleService roleService;

    /*
     * 获取分页列表
     * */
    @AnonymousGetMapping(value = "/getByCondition")
    public Result<Page<Role>> getRoleByPage(Role role, PageVo pageVo) {
        Page<Role> page = roleService.findByCondition(role, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Role>>().setData(page);
    }

    @AnonymousPostMapping("/add")
    public Result<Object> create(@Validated @RequestBody Role resources) {
        roleService.create(resources);
        return ResultUtil.success("添加成功");
    }

    @AnonymousPostMapping("/edit")
    public Result<Object> update(@Validated @RequestBody Role role) {
        roleService.update(role);
        return ResultUtil.success("修改成功");
    }

    @AnonymousPostMapping("/delete")
    public Result<Object> delete(@RequestBody Set<Long> ids) {
        roleService.delete(ids);
        return ResultUtil.success("删除成功");
    }

    @AnonymousGetMapping("/getRoleAll")
    public Result<Object> getRoleAll(){
        List<Map<String,Object>> mapList = roleService.getRoleAll();
        return ResultUtil.data(mapList);
    }


    @AnonymousGetMapping("/findByUserId")
    public Result<Object> findByUserId(Long userId){
        List<Map<String,Object>> mapList = roleService.findByUserId(userId);
        return ResultUtil.data(mapList);
    }
}
