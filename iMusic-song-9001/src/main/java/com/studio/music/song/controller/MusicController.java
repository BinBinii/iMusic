package com.studio.music.song.controller;

import com.studio.music.common.model.vo.Render;
import com.studio.music.song.model.vo.GroupMusicVo;
import com.studio.music.song.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: BinBin
 * @Date: 2023/06/04/22:04
 * @Description:
 */
@RestController
@RequestMapping(value = "/music/")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("info/{groupId}")
    public Object findGroupInfo(@PathVariable("groupId") Integer groupId) {
        GroupMusicVo result = musicService.findGroupInfo(groupId);
        return Render.ok(result);
    }

}
