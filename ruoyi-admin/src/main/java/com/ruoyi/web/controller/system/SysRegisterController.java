package com.ruoyi.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.shiro.service.SysRegisterService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.web.service.EmailCodeService;

/**
 * 注册验证
 * 
 * @author ruoyi
 */
@Controller
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private EmailCodeService emailCodeService;

    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    /**
     * 发送邮箱验证码
     */
    @PostMapping("/register/sendEmailCode")
    @ResponseBody
    public AjaxResult sendEmailCode(@RequestParam String email)
    {
        if (StringUtils.isEmpty(email))
        {
            return error("邮箱不能为空");
        }
        if (!email.matches("^[\\w.\\-]+@[\\w\\-]+(\\.[\\w\\-]+)+$"))
        {
            return error("邮箱格式不正确");
        }
        boolean ok = emailCodeService.sendCode(email);
        return ok ? success("验证码已发送") : error("验证码发送失败，请检查邮箱配置或稍后重试");
    }

    @PostMapping("/register")
    @ResponseBody
    public AjaxResult ajaxRegister(SysUser user, @RequestParam(required = false) String emailCode)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        if (StringUtils.isEmpty(user.getEmail()))
        {
            return error("邮箱不能为空");
        }
        if (!user.getEmail().matches("^[\\w.\\-]+@[\\w\\-]+(\\.[\\w\\-]+)+$"))
        {
            return error("邮箱格式不正确");
        }
        if (!emailCodeService.verifyCode(user.getEmail(), emailCode))
        {
            return error("邮箱验证码错误或已过期");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
