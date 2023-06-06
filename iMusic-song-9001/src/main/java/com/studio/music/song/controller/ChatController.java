package com.studio.music.song.controller;

import com.studio.music.common.model.vo.Render;
import com.studio.music.song.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: BinBin
 * @Date: 2023/06/06/08:33
 * @Description:
 */
@RestController
@RequestMapping(value = "/chat/")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("group/join")
    public Object joinGroup(@RequestParam("userId") Integer userId,
                            @RequestParam("groupId") Integer groupId) {
        boolean result = chatService.joinGroup(userId, groupId);
        if (!result) {
            return Render.fail("Failed to join the group");
        }
        return Render.ok(true);
    }

    @PostMapping("group/leave")
    public Object leaveGroup(@RequestParam("userId") Integer userId,
                             @RequestParam("groupId") Integer groupId) {
        boolean result = chatService.leaveGroup(userId, groupId);
        if (!result) {
            return Render.fail("Failed to leave the group");
        }
        return Render.ok(true);
    }

//    public Object sendMessage(@RequestParam("userId") Integer userId,
//                              @RequestParam("groupId") Integer groupId,
//                              @RequestParam("message") String message) {
//
//    }

}
