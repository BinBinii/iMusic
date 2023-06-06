package com.studio.music.song.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.studio.music.common.utils.IdGen;
import com.studio.music.song.mapper.TbGroupMapper;
import com.studio.music.song.mapper.TbGroupToUserMapper;
import com.studio.music.song.model.dto.CreateGroupDto;
import com.studio.music.song.model.pojo.TbGroup;
import com.studio.music.song.model.pojo.TbGroupToUser;
import com.studio.music.song.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: BinBin
 * @Date: 2023/05/21/21:00
 * @Description:
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private TbGroupMapper tbGroupMapper;
    @Autowired
    private TbGroupToUserMapper tbGroupToUserMapper;

    @Override
    public boolean create(CreateGroupDto createGroupDto) {
        if (createGroupDto.getName().isEmpty() || createGroupDto.getAdmin() <= 0) {
            return false;
        }
        int groupId = IdGen.randomInteger();
        TbGroup tbGroup = new TbGroup();
        tbGroup.setId(groupId)
                .setName(createGroupDto.getName())
                .setAdmin(createGroupDto.getAdmin())
                .setCreate_time(new Date());
        TbGroupToUser tbGroupToUser = new TbGroupToUser();
        tbGroupToUser.setGroup_id(groupId)
                .setUser_id(createGroupDto.getAdmin())
                .setJoin_time(new Date());
        if (tbGroupMapper.insert(tbGroup) != 1 && tbGroupToUserMapper.insert(tbGroupToUser) != 1) {
            return false;
        }
        return true;
    }

    @Override
    public TbGroup findGroupById(Integer id) {
        return tbGroupMapper.selectById(id);
    }

    @Override
    public boolean update(TbGroup tbGroup) {
        TbGroup dbGroup = tbGroupMapper.selectById(tbGroup.getId());
        if (tbGroup.getName().isEmpty() || !dbGroup.getAdmin().equals(tbGroup.getAdmin())) {
            return false;
        }
        dbGroup.setName(tbGroup.getName())
                .setNotice(tbGroup.getNotice());
        return tbGroupMapper.updateById(dbGroup) == 1;
    }

    @Override
    public boolean delete(Integer groupId, Integer admin) {
        QueryWrapper<TbGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", groupId).eq("admin", admin);
        return tbGroupMapper.delete(queryWrapper) == 1;
    }
}
