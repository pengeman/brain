package com.ruoyi.brain.service.impl;

import com.ruoyi.brain.domain.BrainTestResult;
import com.ruoyi.brain.mapper.BrainTestResultMapper;
import com.ruoyi.brain.service.IBrainTestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 脑力测试结果Service业务层处理
 *
 * @author peng
 * @date 2026-08-01
 */
@Service
public class BrainTestResultServiceImpl implements IBrainTestResultService
{
    @Autowired
    private BrainTestResultMapper brainTestResultMapper;

    /**
     * 新增脑力测试结果
     *
     * @param brainTestResult 脑力测试结果
     * @return 结果
     */
    @Override
    public int insertBrainTestResult(BrainTestResult brainTestResult)
    {
        return brainTestResultMapper.insertBrainTestResult(brainTestResult);
    }

    /**
     * 查询用户最近的历史成绩
     *
     * @param userId 用户ID
     * @param limit 条数限制
     * @return 成绩列表
     */
    @Override
    public List<BrainTestResult> selectRecentByUserId(Long userId, int limit)
    {
        return brainTestResultMapper.selectRecentByUserId(userId, limit);
    }
}
