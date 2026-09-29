package com.ruoyi.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 邮箱验证码服务
 * 生成、发送、校验注册邮箱验证码
 *
 * @author peng
 * @date 2026-08-26
 */
@Service
public class EmailCodeService
{
    private static final Logger log = LoggerFactory.getLogger(EmailCodeService.class);

    /** 验证码有效期(毫秒):5分钟 */
    private static final long EXPIRE_MILLIS = 5 * 60 * 1000L;

    /** 邮箱 -> 验证码 */
    private static final Map<String, String> CODE_CACHE = new ConcurrentHashMap<>();

    /** 邮箱 -> 过期时间 */
    private static final Map<String, Long> EXPIRE_CACHE = new ConcurrentHashMap<>();

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.enabled:true}")
    private boolean mailEnabled;

    @Value("${spring.mail.username:}")
    private String from;

    /**
     * 生成并发送验证码
     *
     * @param email 目标邮箱
     * @return 成功与否
     */
    public boolean sendCode(String email)
    {
        String code = String.format("%06d", (int) (Math.random() * 1000000));
        CODE_CACHE.put(email, code);
        EXPIRE_CACHE.put(email, System.currentTimeMillis() + EXPIRE_MILLIS);

        if (!mailEnabled || mailSender == null)
        {
            // 未配置真实邮箱时,验证码打印在日志中方便本地调试
            log.warn("[智域大脑] 邮箱验证码未通过邮件发送(邮件服务未配置), email={}, code={}", email, code);
            return true;
        }
        try
        {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(email);
            message.setSubject("【智域大脑】邮箱验证码");
            message.setText("您的注册验证码为：" + code + "，5分钟内有效。请勿泄露给他人。");
            mailSender.send(message);
            return true;
        }
        catch (Exception e)
        {
            log.error("发送邮箱验证码失败: email={}", email, e);
            return false;
        }
    }

    /**
     * 校验验证码是否正确且未过期
     *
     * @param email 邮箱
     * @param code 验证码
     * @return 校验结果
     */
    public boolean verifyCode(String email, String code)
    {
        Long expire = EXPIRE_CACHE.get(email);
        String cached = CODE_CACHE.get(email);
        if (expire == null || cached == null)
        {
            return false;
        }
        if (System.currentTimeMillis() > expire)
        {
            removeCode(email);
            return false;
        }
        boolean ok = cached.equals(code);
        if (ok)
        {
            // 校验通过后立即清除,防止重复使用
            removeCode(email);
        }
        return ok;
    }

    /**
     * 移除验证码
     */
    public void removeCode(String email)
    {
        CODE_CACHE.remove(email);
        EXPIRE_CACHE.remove(email);
    }
}
