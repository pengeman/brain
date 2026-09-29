package com.ruoyi.brain.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 脑力测试结果对象 brain_test_result
 *
 * @author peng
 * @date 2026-08-01
 */
public class BrainTestResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 游戏类型 (schulte/memory/match/pattern/reaction/sudoku) */
    private String gameType;

    /** 测试类型 (例如：记忆力、专注力、逻辑力) */
    private String testType;

    /** 得分 */
    private Double score;

    /** 用时(秒) */
    private Integer duration;

    /** 难度等级 */
    private Integer level;

    /** 扩展信息(JSON,如失误次数) */
    private String extra;

    /** 测试时间 */
    private Date testTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    @Override
    public String toString() {
        return "BrainTestResult{" +
                "id=" + id +
                ", userId=" + userId +
                ", gameType='" + gameType + '\'' +
                ", testType='" + testType + '\'' +
                ", score=" + score +
                ", duration=" + duration +
                ", level=" + level +
                ", testTime=" + testTime +
                '}';
    }
}
