package com.studio.music.song.service;

import com.studio.music.song.model.dto.AddGroupDto;
import com.studio.music.song.model.dto.ExamineApplyDto;
import com.studio.music.song.model.dto.InviteGroupDto;
import com.studio.music.song.model.pojo.TbGroupApply;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BinBin
 * @Date: 2023/05/21/22:00
 * @Description:
 */
@Service
public interface GroupServerService {

    /**
     * 加群
     * @param addGroupDto
     * @return
     */
    boolean joinGroup(AddGroupDto addGroupDto);

    /**
     * 退群
     * @param groupId
     * @param userId
     * @return
     */
    boolean exitGroup(Integer groupId, Integer userId);

    /**
     * 邀请
     * @param inviteGroupDto
     * @return
     */
    boolean invite(InviteGroupDto inviteGroupDto);

    /**
     * 获取群申请列表
     * @param admin
     * @return
     */
    List<TbGroupApply> findGroupApplyList(Integer admin);

    /**
     * 审核
     * @param examineApplyDto
     * @return
     */
    Integer examine(ExamineApplyDto examineApplyDto);


}
