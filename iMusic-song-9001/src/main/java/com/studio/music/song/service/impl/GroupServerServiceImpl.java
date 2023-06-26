package com.studio.music.song.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.studio.music.song.mapper.TbGroupApplyMapper;
import com.studio.music.song.mapper.TbGroupMapper;
import com.studio.music.song.mapper.TbGroupToUserMapper;
import com.studio.music.song.mapper.TbUserMapper;
import com.studio.music.song.model.dto.AddGroupDto;
import com.studio.music.song.model.dto.ExamineApplyDto;
import com.studio.music.song.model.dto.InviteGroupDto;
import com.studio.music.song.model.pojo.TbGroup;
import com.studio.music.song.model.pojo.TbGroupApply;
import com.studio.music.song.model.pojo.TbGroupToUser;
import com.studio.music.song.model.vo.GroupApplyVo;
import com.studio.music.song.service.GroupServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: BinBin
 * @Date: 2023/05/21/22:27
 * @Description:
 */
@Service
public class GroupServerServiceImpl implements GroupServerService {
    @Autowired
    private TbGroupApplyMapper tbGroupApplyMapper;
    @Autowired
    private TbGroupMapper tbGroupMapper;
    @Autowired
    private TbGroupToUserMapper tbGroupToUserMapper;
    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public boolean joinGroup(AddGroupDto addGroupDto) {
        if (addGroupDto.getGroupId() <= 0 || addGroupDto.getUserId() <= 0) {
            return false;
        }
        TbGroupApply tbGroupApply = new TbGroupApply();
        tbGroupApply.setGroup_id(addGroupDto.getGroupId())
                .setUser_id(addGroupDto.getUserId())
                .setApply_time(new Date())
                .setStatus(0);
        return tbGroupApplyMapper.insert(tbGroupApply) == 1;
    }

    @Override
    public boolean exitGroup(Integer groupId, Integer userId) {
        QueryWrapper<TbGroupToUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", groupId).eq("user_id", userId);
        return tbGroupToUserMapper.delete(queryWrapper) == 1;
    }

    @Override
    public boolean invite(InviteGroupDto inviteGroupDto) {
        TbGroup tbGroup = tbGroupMapper.selectById(inviteGroupDto.getGroupId());
        if (tbGroup == null) {
            return false;
        }
        for (Integer userId:inviteGroupDto.getUserIds()) {
            TbGroupToUser tbGroupToUser = new TbGroupToUser();
            tbGroupToUser.setGroup_id(inviteGroupDto.getGroupId())
                    .setUser_id(userId)
                    .setJoin_time(new Date())
                    .setNickname(inviteGroupDto.getUserNickname());
            tbGroupToUserMapper.insert(tbGroupToUser);
        }
        return true;
    }

    @Override
    public List<GroupApplyVo> findGroupApplyList(Integer admin, Integer status) {
        List<GroupApplyVo> list = new ArrayList<>();
        QueryWrapper<TbGroup> tbGroupQueryWrapper = new QueryWrapper<>();
        tbGroupQueryWrapper.eq("admin", admin);
        List<TbGroup> groups = tbGroupMapper.selectList(tbGroupQueryWrapper);
        for (TbGroup group:groups) {
            QueryWrapper<TbGroupApply> tbGroupApplyQueryWrapper = new QueryWrapper<>();
            tbGroupApplyQueryWrapper.eq("group_id", group.getId()).eq("status", status);
            List<TbGroupApply> tbGroupApplies = tbGroupApplyMapper.selectList(tbGroupApplyQueryWrapper);
            for (TbGroupApply tbGroupApply:tbGroupApplies) {
                GroupApplyVo groupApplyVo = new GroupApplyVo();
                groupApplyVo.setId(tbGroupApply.getId())
                        .setTbUser(tbUserMapper.selectById(tbGroupApply.getUser_id()))
                        .setTbGroup(tbGroupMapper.selectById(tbGroupApply.getGroup_id()))
                        .setApply_time(tbGroupApply.getApply_time())
                        .setStatus(tbGroupApply.getStatus());
                list.add(groupApplyVo);
            }
        }
        return list;
    }

    @Override
    public Integer examine(ExamineApplyDto examineApplyDto) {
        TbGroupApply tbGroupApply = tbGroupApplyMapper.selectById(examineApplyDto.getApplyId());
        if (tbGroupApply == null) {
            return 0;
        }
        TbGroup tbGroup = tbGroupMapper.selectById(tbGroupApply.getGroup_id());
        if (tbGroup == null) {
            return 0;
        }
        if (!examineApplyDto.getAdmin().equals(tbGroup.getAdmin())) {
            return -1;
        }
        tbGroupApply.setStatus(examineApplyDto.getStatus());
        if (examineApplyDto.getStatus().equals(1)) {
            TbGroupToUser tbGroupToUser = new TbGroupToUser();
            tbGroupToUser.setGroup_id(tbGroupApply.getGroup_id())
                    .setUser_id(tbGroupApply.getUser_id())
                    .setJoin_time(new Date())
                    .setNickname(tbUserMapper.selectById(tbGroupApply.getUser_id()).getNickname());
            tbGroupToUserMapper.insert(tbGroupToUser);
        }
        tbGroupApplyMapper.updateById(tbGroupApply);
        return 1;
    }
}
