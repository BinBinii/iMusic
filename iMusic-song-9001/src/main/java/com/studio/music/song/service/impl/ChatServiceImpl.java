package com.studio.music.song.service.impl;

import com.alibaba.fastjson.JSON;
import com.studio.music.song.contacts.RedisContacts;
import com.studio.music.song.mapper.TbGroupMapper;
import com.studio.music.song.mapper.TbUserMapper;
import com.studio.music.song.model.pojo.TbGroup;
import com.studio.music.song.model.pojo.TbUser;
import com.studio.music.song.model.vo.ChatMessage;
import com.studio.music.song.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author: BinBin
 * @Date: 2023/06/05/20:11
 * @Description:
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private TbGroupMapper tbGroupMapper;

    @Override
    public boolean joinGroup(Integer userId, Integer groupId) {
        TbUser tbUser = tbUserMapper.selectById(userId);
        TbGroup tbGroup = tbGroupMapper.selectById(groupId);
        if (tbUser == null || tbGroup == null) {
            return false;
        }
        redisTemplate.opsForSet().add(RedisContacts.GROUP_PRE + ":" + groupId, tbUser);
        return true;
    }

    @Override
    public boolean leaveGroup(Integer userId, Integer groupId) {
        TbUser tbUser = tbUserMapper.selectById(userId);
        TbGroup tbGroup = tbGroupMapper.selectById(groupId);
        if (tbUser == null || tbGroup == null) {
            return false;
        }
        redisTemplate.opsForSet().remove(RedisContacts.GROUP_PRE + ":" + groupId, tbUser);
        return true;
    }

    @Override
    public boolean sendMessage(Integer userId, Integer groupId, String message) {
        TbUser tbUser = tbUserMapper.selectById(userId);
        TbGroup tbGroup = tbGroupMapper.selectById(groupId);
        ChatMessage chatMessage = new ChatMessage(tbUser.getNickname(), message);
        String json = JSON.toJSONString(chatMessage);

        // 广播消息给群组成员
        Set<Object> groupMembers = redisTemplate.opsForSet().members(RedisContacts.GROUP_PRE + ":" + groupId);
        for (Object member:groupMembers) {
            TbUser user = (TbUser) member;

            // 发送消息到用户的消息队列（使用用户ID作为队列名称）
            redisTemplate.convertAndSend(user.getId().toString(), json);
        }
        return true;
    }


}
