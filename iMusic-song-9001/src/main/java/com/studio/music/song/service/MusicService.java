package com.studio.music.song.service;

import com.studio.music.song.model.vo.GroupMusicVo;
import org.springframework.stereotype.Service;

/**
 * @Author: BinBin
 * @Date: 2023/06/04/22:01
 * @Description:
 */
@Service
public interface MusicService {

    /**
     * 根据ID获取群组会话信息
     * @param groupId
     * @return
     */
    GroupMusicVo findGroupInfo(Integer groupId);

}
