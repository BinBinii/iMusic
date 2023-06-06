package com.studio.music.song.controller;

import com.studio.music.common.model.vo.Render;
import com.studio.music.song.model.dto.RegisterDto;
import com.studio.music.song.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: BinBin
 * @Date: 2022/12/12/02:11
 * @Description:
 */
@RestController
@RequestMapping(value = "/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public Object register(@RequestBody RegisterDto registerDto) {
        int result = userService.register(registerDto);
        if (result == 0) {
            return Render.fail("The user has already been registered");
        }
        if (result == -1) {
            return Render.fail("Parameter cannot be empty");
        }
        if (result == -2) {
            return Render.fail("Verification code error");
        }
        return Render.ok(result);
    }

}
