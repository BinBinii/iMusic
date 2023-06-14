package com.studio.music.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.studio.music.auth.mapper.TbUserMapper;
import com.studio.music.auth.model.dto.LoginDto;
import com.studio.music.auth.model.pojo.TbRole;
import com.studio.music.auth.model.pojo.TbUser;
import com.studio.music.auth.utils.JwtTokenUtil;
import com.studio.music.common.model.vo.Render;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: BinBin
 * @Date: 2023/04/25/15:24
 * @Description:
 */
@RestController
public class AuthenticationController {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("login")
    public Object login(@RequestBody LoginDto loginDto){
        TbUser tbUser = tbUserMapper.selectOne(new QueryWrapper<TbUser>().eq("email", loginDto.getEmail()));
        if (tbUser != null && bCryptPasswordEncoder.matches(loginDto.getPassword(), tbUser.getPassword())) {
            String jwt = JwtTokenUtil.createToken(loginDto.getEmail(), "ROLE_USER", tbUser);
            return Render.ok(jwt);
        }
        return Render.fail("Login Failed");
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("token")
    public Object token(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        TbUser tbUser = jwtTokenUtil.getUser(token);
        return tbUser;
    }
}