package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.JwtUtil;
import com.ruoyi.framework.shiro.service.SysLoginService;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 移动端登录（JSON 格式，无验证码）
 */
@RestController
public class MobileLoginController {

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysPasswordService passwordService;

    @PostMapping("/api/login")
    public AjaxResult mobileLogin(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return AjaxResult.error("用户名或密码不能为空");
        }

        // 从数据库查询用户
        SysUser user = loginService.selectUserByLoginName(username);
        if (user == null) {
            return AjaxResult.error("用户名或密码错误");
        }

        // 校验密码
        if (!passwordService.matches(user, password)) {
            return AjaxResult.error("用户名或密码错误");
        }

        // 检查账号状态
        if ("1".equals(user.getStatus())) {
            return AjaxResult.error("账号已停用");
        }

        // 生成 JWT token
        String jwt = JwtUtil.createToken(user.getUserId(), user.getLoginName());
        AjaxResult result = AjaxResult.success("登录成功");
        result.put("token", jwt);
        return result;
    }
}
