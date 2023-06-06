package com.studio.music.song.service.impl;

import com.studio.music.song.config.RabbitmqConfig;
import com.studio.music.song.model.vo.MailVo;
import com.studio.music.song.service.MailService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @Author: BinBin
 * @Date: 2022/12/13/15:27
 * @Description:
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public boolean launchMailRequest(MailVo mailVo) {
        if (mailVo.getMail().isEmpty() || mailVo.getTitle().isEmpty() || mailVo.getContent().isEmpty()) {
            return false;
        }
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, "inform.email", mailVo);
        return true;
    }

    @Override
    public boolean sendMail(MailVo mailVo) {
        if (mailVo.getMail().isEmpty()) {
            return false;
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(mailVo.getMail());
        mailMessage.setSubject(mailVo.getTitle());
        mailMessage.setText(mailVo.getContent());
        mailSender.send(mailMessage);
        return true;
    }
}
