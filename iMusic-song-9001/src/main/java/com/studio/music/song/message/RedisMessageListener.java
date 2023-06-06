package com.studio.music.song.message;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: BinBin
 * @Date: 2023/06/06/08:39
 * @Description:
 */
@Component
public class RedisMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] channel = message.getChannel();
        byte[] body = message.getBody();

        String title = new String(channel, StandardCharsets.UTF_8);
        String content = new String(body, StandardCharsets.UTF_8);
        System.out.println(title);
        System.out.println(content);
    }
}
