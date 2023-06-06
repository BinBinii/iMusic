package com.studio.music.song.controller;

import com.studio.music.song.model.vo.MailVo;
import com.studio.music.song.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author: BinBin
 * @Date: 2022/12/13/15:50
 * @Description:
 */
@RestController
@RequestMapping(value = "/mail/")
public class MailController {

    @Autowired
    private MailService mailService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${spring.mail.code.pre}")
    private String CODE_PRE;
    @Value("${spring.mail.code.expire}")
    private Integer CODE_PRE_EXPIRE;

    @PostMapping("/send/register/verification/code")
    public boolean verificationCode(@RequestParam("mail") String mail) {
        MailVo mailVo = new MailVo();
        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        String title = "[iMusic] Please verify your device";
        String content = "Hey Guy!\n\n" +
                "Welcome to iMusic!" + "\n\n" +
                "Verification code: " + code + "\n\n" +
                "If you have not applied for iBlog service, please ignore this email." +
                "If you still have problems, please contact us through this email: 847024724@qq.com\n\n" +
                "Thanks,\n" +
                "The 603-Studio Team.";
        mailVo.setMail(mail)
                .setTitle(title)
                .setContent(content);
        // 验证码存入redis 5min生命周期
        redisTemplate.opsForValue().set(CODE_PRE + ":" + mailVo.getMail() + "", code);
        redisTemplate.expire(CODE_PRE + ":" + mailVo.getMail() + "", CODE_PRE_EXPIRE, TimeUnit.SECONDS);
        return mailService.launchMailRequest(mailVo);
    }

}
