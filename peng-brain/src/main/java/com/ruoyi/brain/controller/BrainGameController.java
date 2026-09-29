package com.ruoyi.brain.controller;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 智域大脑游戏页面Controller
 *
 * @author peng
 * @date 2026-08-24
 */
@Controller
public class BrainGameController
{
    /**
     * 游戏大厅(匿名入口 /index)
     */
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("games", buildGames());
        mmap.put("isLogin", user != null);
        mmap.put("loginName", user != null ? user.getLoginName() : "");
        return "brain/index";
    }

    /**
     * 兼容旧地址 /brain、/brain/ -> 跳转到游戏大厅 /index
     */
    @GetMapping({ "/brain", "/brain/" })
    public String redirectOldBrain()
    {
        return "redirect:/index";
    }

    /**
     * 构建游戏入口列表
     */
    private List<Map<String, String>> buildGames()
    {
        List<Map<String, String>> games = new ArrayList<>();
        games.add(game("schulte", "舒尔特方格", "25个数字打乱排列，按1-25顺序依次点击，挑战专注极限", "专注力", "tag-focus", "#f8a13b", "fa fa-th-large"));
        games.add(game("memory", "数字记忆", "数字串一闪而过，凭记忆准确复述，难度逐级提升", "记忆力", "tag-memory", "#4e6ef2", "fa fa-hashtag"));
        games.add(game("match", "翻牌配对", "翻开卡片找相同图案，考验短期记忆与观察力", "记忆力", "tag-memory", "#4e6ef2", "fa fa-cards"));
        games.add(game("pattern", "图形规律", "观察图形序列，找出规律补全下一项", "逻辑力", "tag-logic", "#22b573", "fa fa-shapes"));
        games.add(game("reaction", "色彩反应", "紧盯颜色，目标色出现瞬间立刻点击", "反应力", "tag-reaction", "#ef5a7a", "fa fa-bolt"));
        games.add(game("sudoku", "数独变体", "经典数独轻量玩法，动脑填满每行每列每宫", "逻辑力", "tag-logic", "#22b573", "fa fa-grid"));
        return games;
    }

    private Map<String, String> game(String url, String name, String desc, String tag, String tagClass, String color, String icon)
    {
        Map<String, String> g = new LinkedHashMap<>();
        g.put("url", "/brain/" + url);
        g.put("name", name);
        g.put("desc", desc);
        g.put("tag", tag);
        g.put("tagClass", tagClass);
        g.put("color", color);
        g.put("icon", icon);
        return g;
    }

    /**
     * 舒尔特方格(专注力)
     */
    @GetMapping("/brain/schulte")
    public String schulte(ModelMap mmap)
    {
        putLogin(mmap);
        return "brain/schulte";
    }

    /**
     * 数字记忆(记忆力)
     */
    @GetMapping("/brain/memory")
    public String memory(ModelMap mmap)
    {
        putLogin(mmap);
        return "brain/memory";
    }

    /**
     * 翻牌配对(记忆力)
     */
    @GetMapping("/brain/match")
    public String match(ModelMap mmap)
    {
        putLogin(mmap);
        return "brain/match";
    }

    /**
     * 图形规律推理(逻辑力)
     */
    @GetMapping("/brain/pattern")
    public String pattern(ModelMap mmap)
    {
        putLogin(mmap);
        return "brain/pattern";
    }

    /**
     * 色彩闪烁反应(反应力)
     */
    @GetMapping("/brain/reaction")
    public String reaction(ModelMap mmap)
    {
        putLogin(mmap);
        return "brain/reaction";
    }

    /**
     * 数独变体(逻辑力)
     */
    @GetMapping("/brain/sudoku")
    public String sudoku(ModelMap mmap)
    {
        putLogin(mmap);
        return "brain/sudoku";
    }

    /**
     * 向页面模型写入登录状态
     */
    private void putLogin(ModelMap mmap)
    {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("isLogin", user != null);
        mmap.put("loginName", user != null ? user.getLoginName() : "");
    }
}
