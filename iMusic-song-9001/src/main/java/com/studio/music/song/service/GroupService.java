package com.studio.music.song.service;

import com.studio.music.song.model.dto.CreateGroupDto;
import com.studio.music.song.model.pojo.TbGroup;
import com.studio.music.song.model.vo.GroupInfoVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BinBin
 * @Date: 2023/05/21/21:00
 * @Description:
 */
@Service
public interface GroupService {

    /**
     * 创建群聊
     * @param createGroupDto
     * @return
     */
    boolean create(CreateGroupDto createGroupDto);

    /**
     * 根据ID查询群信息
     * @param id
     * @return
     */
    TbGroup findGroupById(Integer id);

    /**
     * 根据ID查询群信息与人员信息
     * @param id
     * @return
     */
    GroupInfoVo findGroupInfoById(Integer id);

    /**
     * 根据用户ID查询已加入的群列表
     * @param userId
     * @return
     */
    List<TbGroup> findGroupListByUserId(Integer userId);

    /**
     * 编辑群信息
     * @param tbGroup
     * @return
     */
    boolean update(TbGroup tbGroup);

    /**
     * 删除群
     * @param groupId
     * @param admin
     * @return
     */
    boolean delete(Integer groupId, Integer admin);


}
