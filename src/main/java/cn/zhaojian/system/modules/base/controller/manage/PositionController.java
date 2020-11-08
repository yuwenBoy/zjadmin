package cn.zhaojian.system.modules.base.controller.manage;

import cn.zhaojian.system.common.exception.BabException;
import cn.zhaojian.system.common.utils.PageUtil;
import cn.zhaojian.system.common.utils.ResultUtil;
import cn.zhaojian.system.common.vo.PageVo;
import cn.zhaojian.system.common.vo.Result;
import cn.zhaojian.system.common.vo.SearchVo;
import cn.zhaojian.system.config.security.AnonymousGetMapping;
import cn.zhaojian.system.config.security.AnonymousPostMapping;
import cn.zhaojian.system.modules.base.entity.Position;
import cn.zhaojian.system.modules.base.entity.User;
import cn.zhaojian.system.modules.base.service.PositionService;
import cn.zhaojian.system.modules.base.service.UserService;
import cn.zhaojian.system.modules.base.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/jxxqz/api/position")
@Transactional
public class PositionController {
    private static final String ENTITY_NAME = "position";

    @Autowired
    private PositionService positionService;

    /*
     * 获取职位分页列表
     * */
    @AnonymousGetMapping(value = "/getByCondition")
    public Result<Page<Position>> getPositionByPage(Position position, PageVo pageVo) {
        Page<Position> page = positionService.findByCondition(position, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Position>>().setData(page);
    }

    @AnonymousPostMapping("/add")
    public Result<Object> create(@Validated @RequestBody Position resources) {
        if (resources.getId() != null) {
            throw new BabException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        positionService.create(resources);
        return ResultUtil.success("添加成功");
    }

    @AnonymousPostMapping("/edit")
    public Result<Object> update(@Validated @RequestBody Position position) {
        positionService.update(position);
        return ResultUtil.success("修改成功");
    }

    @AnonymousPostMapping("/delete")
    public Result<Object> delete(@RequestBody Set<Long> ids) {
        positionService.delete(ids);
        return ResultUtil.success("删除成功");
    }

    @AnonymousGetMapping("/getPositionList")
    public Result<Object> getPositionList() {
        List<Map<String,Object>> map = positionService.getPositionList(SecurityUtils.getCurrentUsername());
        return ResultUtil.data(map);
    }
}
