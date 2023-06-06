package com.studio.music.song.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.studio.music.song.contacts.RedisContacts;
import com.studio.music.song.model.vo.GroupMusicVo;
import com.studio.music.song.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: BinBin
 * @Date: 2023/06/04/22:02
 * @Description:
 */
@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public GroupMusicVo findGroupInfo(Integer groupId) {
        String json = String.valueOf(redisTemplate.opsForValue().get(RedisContacts.GROUP_MUSICS_PRE + ":" + groupId + ""));
        GroupMusicVo groupMusicVo = JSON.parseObject(json, GroupMusicVo.class);
        return groupMusicVo;
    }
}
