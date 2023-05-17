package com.studio.music.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: BinBin
 * @Date: 2023/04/26/15:31
 * @Description:
 */
@RestController
public class TestController {

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user/hello")
    public String test() {
        return "Hello ";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin/hello")
    public String test2() {
        return "Hello Admin";
    }

    @GetMapping("/public/hello")
    public String test3() {
        return "Hello public";
    }
}
