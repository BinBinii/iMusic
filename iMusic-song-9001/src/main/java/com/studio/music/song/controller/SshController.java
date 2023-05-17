package com.studio.music.song.controller;

import com.studio.music.song.service.SshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: BinBin
 * @Date: 2023/05/09/12:08
 * @Description:
 */
@RestController
@RequestMapping(value = "/ssh/")
public class SshController {

    @Autowired
    private SshService sshService;

    @GetMapping("command")
    public String execCommand(String command) {
        return sshService.execCommand(command);
    }

}
