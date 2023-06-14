package com.studio.music.auth.controller;

import com.studio.music.auth.model.pojo.TbUser;
import com.studio.music.auth.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: BinBin
 * @Date: 2023/04/26/15:31
 * @Description:
 */
@RestController
public class TestController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin/hello")
    public String test2() {
        return "Hello Admin";
    }

    @GetMapping("/user/test")
    public String test3() {
        return "Hello public";
    }
}
