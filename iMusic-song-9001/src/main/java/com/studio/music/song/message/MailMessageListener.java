package com.studio.music.song.message;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.studio.music.song.config.RabbitmqConfig;
import com.studio.music.song.model.vo.MailVo;
import com.studio.music.song.service.MailService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: BinBin
 * @Date: 2022/11/05/12:11
 * @Description:
 */
@Component
public class MailMessageListener {

    @Autowired
    private MailService mailService;

    @RabbitListener(queues = RabbitmqConfig.QUEUE_INFORM_EMAIL)
    public void mail_message(Object msg, Message message, Channel channel) {
        MailVo mailVo = new Gson().fromJson(new String(message.getBody()), MailVo.class);
        boolean result = mailService.sendMail(mailVo);
        if (!result) {
            // TODO 重新进入mq队列，再次执行发送邮箱操作
        }
    }

}
