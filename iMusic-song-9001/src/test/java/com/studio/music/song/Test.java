package com.studio.music.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author: BinBin
 * @Date: 2023/06/04/20:37
 * @Description:
 */
@SpringBootTest
public class Test {
    @Autowired
    private RedisTemplate redisTemplate;

}
