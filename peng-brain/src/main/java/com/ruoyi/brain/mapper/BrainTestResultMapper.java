package com.ruoyi.brain.mapper;

import com.ruoyi.brain.domain.BrainTestResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 脑力测试结果Mapper接口
 *
 * @author peng
 * @date 2026-08-01
 */
@Mapper
public interface BrainTestResultMapper
{
    /**
     * 新增脑力测试结果
     *
     * @param brainTestResult 脑力测试结果
     * @return 结果
     */
    public int insertBrainTestResult(BrainTestResult brainTestResult);

    /**
     * 查询用户最近的历史成绩(按测试时间倒序)
     *
     * @param userId 用户ID
     * @param limit 条数限制
     * @return 成绩列表
     */
    public List<BrainTestResult> selectRecentByUserId(@Param("userId") Long userId, @Param("limit") int limit);
}
