package com.studio.music.song;

import com.studio.music.song.netty.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * @Author: BinBin
 * @Date: 2023/05/03/09:13
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.studio.music.song.mapper")
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SongApplication {

    @Autowired
    private NettyServer nettyServer;
    public static void main(String[] args) {
        SpringApplication.run(SongApplication.class, args);
    }


}
