package com.studio.music.song.controller;

import com.studio.music.common.model.vo.Render;
import com.studio.music.song.contacts.RedisContacts;
import com.studio.music.song.model.dto.CreateGroupDto;
import com.studio.music.song.model.pojo.TbGroup;
import com.studio.music.song.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("create")
    public Object create(@RequestBody CreateGroupDto createGroupDto) {
        boolean result = groupService.create(createGroupDto);
        if (!result) {
            return Render.fail("create group failed");
        }
        return Render.ok(true);
    }

    @GetMapping("get/{id}")
    public Object findGroupById(@PathVariable("id") Integer id) {
        TbGroup result = groupService.findGroupById(id);
        return Render.ok(result);
    }

    @PostMapping("update")
    public Object update(@RequestBody TbGroup tbGroup) {
        boolean result = groupService.update(tbGroup);
        if (!result) {
            return Render.fail("update group failed");
        }
        return Render.ok(true);
    }

    @PostMapping("delete")
    public Object delete(@RequestParam("groupId") Integer groupId, @RequestParam("admin") Integer admin) {
        boolean result = groupService.delete(groupId, admin);
        if (!result) {
            return Render.fail("delete group failed");
        }
        return Render.ok(true);
    }

}
