package com.studio.music.song.service;

import org.springframework.stereotype.Service;

/**
 * @Author: BinBin
 * @Date: 2023/06/06/09:06
 * @Description:
 */
@Service
public interface ChatService {

    /**
     * 加入群聊
     * @param userId
     * @param groupId
     * @return
     */
    boolean joinGroup(Integer userId, Integer groupId);

    /**
     * 离开群聊
     * @param userId
     * @param groupId
     * @return
     */
    boolean leaveGroup(Integer userId, Integer groupId);

    /**
     * 发送消息
     * @param userId
     * @param groupId
     * @param message
     * @return
     */
    boolean sendMessage(Integer userId, Integer groupId, String message);
}
