package com.studio.music.song.controller;

import com.studio.music.common.model.vo.Render;
import com.studio.music.song.model.dto.AddGroupDto;
import com.studio.music.song.model.dto.ExamineApplyDto;
import com.studio.music.song.model.dto.InviteGroupDto;
import com.studio.music.song.model.pojo.TbGroupApply;
import com.studio.music.song.service.GroupServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: BinBin
 * @Date: 2023/05/21/23:16
 * @Description:
 */
@RestController
@RequestMapping(value = "/group/server/")
public class GroupServerController {

    @Autowired
    private GroupServerService groupServerService;

    @PostMapping("join")
    public Object joinGroup(@RequestBody AddGroupDto addGroupDto) {
        boolean result = groupServerService.joinGroup(addGroupDto);
        if (!result) {
            return Render.fail("Failed to join the group");
        }
        return Render.ok(true);
    }

    @PostMapping("exit")
    public Object exitGroup(@RequestParam("groupId") Integer groupId, @RequestParam("userId") Integer userId) {
        boolean result = groupServerService.exitGroup(groupId, userId);
        if (!result) {
            return Render.fail("Failed to exit the group");
        }
        return Render.ok(true);
    }

    @PostMapping("invite")
    public Object invite(@RequestBody InviteGroupDto inviteGroupDto) {
        boolean result = groupServerService.invite(inviteGroupDto);
        if (!result) {
            return Render.fail("Invitation failed");
        }
        return Render.ok(true);
    }

    @GetMapping("apply/list")
    public Object findGroupApplyList(@RequestParam("admin") Integer admin) {
        List<TbGroupApply> result = groupServerService.findGroupApplyList(admin);
        return Render.ok(result);
    }

    @PostMapping("examine")
    public Object examine(@RequestBody ExamineApplyDto examineApplyDto) {
        int result = groupServerService.examine(examineApplyDto);
        if (result == 0) {
            return Render.fail("There is no such record");
        }
        if (result == -1) {
            return Render.fail("Insufficient permissions");
        }
        return Render.ok(result);
    }

}
