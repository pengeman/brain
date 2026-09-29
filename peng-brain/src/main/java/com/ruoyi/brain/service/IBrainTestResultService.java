package com.ruoyi.brain.service;

import com.ruoyi.brain.domain.BrainTestResult;

import java.util.List;

/**
 * 脑力测试结果Service接口
 *
 * @author peng
 * @date 2026-08-01
 */
public interface IBrainTestResultService
{
    /**
     * 新增脑力测试结果
     *
     * @param brainTestResult 脑力测试结果
     * @return 结果
     */
    public int insertBrainTestResult(BrainTestResult brainTestResult);

    /**
     * 查询用户最近的历史成绩
     *
     * @param userId 用户ID
     * @param limit 条数限制
     * @return 成绩列表
     */
    public List<BrainTestResult> selectRecentByUserId(Long userId, int limit);
}
