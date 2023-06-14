package com.studio.music.song.controller;

import com.studio.music.common.model.vo.Render;
import com.studio.music.song.contacts.RedisContacts;
import com.studio.music.song.model.dto.CreateGroupDto;
import com.studio.music.song.model.pojo.TbGroup;
import com.studio.music.song.model.vo.GroupInfoVo;
import com.studio.music.song.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: BinBin
 * @Date: 2023/05/21/21:24
 * @Description:
 */
@RestController
@RequestMapping(value = "/group/")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("create")
    public Object create(@RequestBody CreateGroupDto createGroupDto) {
        boolean result = groupService.create(createGroupDto);
        if (!result) {
            return Render.fail("create group failed");
        }
        return Render.ok(true);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("get/{id}")
    public Object findGroupById(@PathVariable("id") Integer id) {
        TbGroup result = groupService.findGroupById(id);
        return Render.ok(result);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("get/info/{id}")
    public Object findGroupInfoById(@PathVariable("id") Integer id) {
        GroupInfoVo groupInfoVo = groupService.findGroupInfoById(id);
        return Render.ok(groupInfoVo);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("get/list")
    public Object findGroupListByUserId(@RequestParam("userId") Integer userId) {
        List<TbGroup> result = groupService.findGroupListByUserId(userId);
        return Render.ok(result);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("update")
    public Object update(@RequestBody TbGroup tbGroup) {
        boolean result = groupService.update(tbGroup);
        if (!result) {
            return Render.fail("update group failed");
        }
        return Render.ok(true);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("delete")
    public Object delete(@RequestParam("groupId") Integer groupId, @RequestParam("admin") Integer admin) {
        boolean result = groupService.delete(groupId, admin);
        if (!result) {
            return Render.fail("delete group failed");
        }
        return Render.ok(true);
    }

}
