package cn.zhaojian.system.modules.base.controller.common;

import cn.hutool.core.util.IdUtil;
import cn.zhaojian.system.common.utils.ResultUtil;
import cn.zhaojian.system.common.vo.Result;
import cn.zhaojian.system.config.bean.LoginProperties;
import cn.zhaojian.system.config.bean.SecurityProperties;
import cn.zhaojian.system.config.security.AnonymousGetMapping;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequestMapping("/jxxqz/common/captcha")
@RestController
@Transactional
public class CaptchaController {
    private final SecurityProperties properties;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Resource
    private LoginProperties loginProperties;

    public CaptchaController(SecurityProperties properties){
        this.properties=properties;
    }
    @AnonymousGetMapping(value = "code")
    public Result<Object> getCode(){
        Captcha captcha =loginProperties.getCaptcha();
        String uuid =properties.getCodeKey()+ IdUtil.simpleUUID();
        redisTemplate.opsForValue().set(uuid,captcha.text(),loginProperties.getLoginCode().getExpiration(),TimeUnit.MINUTES);
        Map<String,Object> imgResult = new HashMap<String,Object>();
        imgResult.put("img",captcha.toBase64());
        imgResult.put("uuid",uuid);
        return ResultUtil.data(imgResult);
    }
}
