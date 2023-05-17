package com.studio.music.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * @Author: BinBin
 * @Date: 2023/04/24/19:20
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.studio.music.auth.mapper")
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
