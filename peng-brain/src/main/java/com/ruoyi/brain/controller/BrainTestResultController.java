package com.ruoyi.brain.controller;

import com.ruoyi.brain.domain.BrainTestResult;
import com.ruoyi.brain.service.IBrainTestResultService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * 脑力测试结果Controller
 *
 * @author peng
 * @date 2026-08-01
 */
@RestController
@RequestMapping("/brain/test")
public class BrainTestResultController extends BaseController
{
    @Autowired
    private IBrainTestResultService brainTestResultService;

    /**
     * 新增脑力测试结果(用户ID取自当前登录用户;匿名访问不保存)
     */
    @PostMapping("/save")
    public AjaxResult add(@RequestBody BrainTestResult brainTestResult)
    {
        SysUser user = ShiroUtils.getSysUser();
        if (user == null)
        {
            return error("未登录，成绩不保存");
        }
        brainTestResult.setUserId(user.getUserId().longValue());
        return toAjax(brainTestResultService.insertBrainTestResult(brainTestResult));
    }

    /**
     * 查询当前登录用户最近的历史成绩(匿名访问返回空列表)
     */
    @GetMapping("/history")
    public AjaxResult history(@RequestParam(defaultValue = "20") int limit)
    {
        SysUser user = ShiroUtils.getSysUser();
        if (user == null)
        {
            return success(Collections.emptyList());
        }
        List<BrainTestResult> list = brainTestResultService.selectRecentByUserId(user.getUserId().longValue(), limit);
        return success(list);
    }
}
