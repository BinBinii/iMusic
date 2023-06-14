package com.studio.music.song.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.studio.music.common.utils.IdGen;
import com.studio.music.song.contacts.RedisContacts;
import com.studio.music.song.mapper.TbGroupMapper;
import com.studio.music.song.mapper.TbGroupToUserMapper;
import com.studio.music.song.mapper.TbUserMapper;
import com.studio.music.song.model.dto.CreateGroupDto;
import com.studio.music.song.model.pojo.TbGroup;
import com.studio.music.song.model.pojo.TbGroupToUser;
import com.studio.music.song.model.pojo.TbUser;
import com.studio.music.song.model.vo.GroupInfoVo;
import com.studio.music.song.model.vo.GroupUserVo;
import com.studio.music.song.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: BinBin
 * @Date: 2023/05/21/21:00
 * @Description:
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private TbGroupMapper tbGroupMapper;
    @Autowired
    private TbGroupToUserMapper tbGroupToUserMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
    public GroupInfoVo findGroupInfoById(Integer id) {
        TbGroup tbGroup = tbGroupMapper.selectById(id);
        GroupInfoVo groupInfoVo = new GroupInfoVo();
        groupInfoVo.setId(tbGroup.getId())
                .setName(tbGroup.getName())
                .setAdmin(tbUserMapper.selectById(tbGroup.getAdmin()).setPassword(""))
                .setCreate_time(tbGroup.getCreate_time())
                .setNotice(tbGroup.getNotice());
        List<GroupUserVo> users = new ArrayList<>();
        LambdaQueryWrapper<TbGroupToUser> tbGroupToUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tbGroupToUserLambdaQueryWrapper.eq(TbGroupToUser::getGroup_id, tbGroup.getId());
        List<TbGroupToUser> tbGroupToUsers = tbGroupToUserMapper.selectList(tbGroupToUserLambdaQueryWrapper)
                .stream().filter(tbGroupToUser -> tbGroupToUser.getGroup_id().equals(tbGroup.getId())).toList();
        for (TbGroupToUser tbGroupToUser:tbGroupToUsers) {
            TbUser tbUser = tbUserMapper.selectById(tbGroupToUser.getUser_id());

            String json = String.valueOf(redisTemplate.opsForValue().get(RedisContacts.USER_SESSION + ":" + tbUser.getId() + ""));
            GroupUserVo groupUserVo = new GroupUserVo();
            groupUserVo.setId(tbUser.getId())
                    .setEmail(tbUser.getEmail())
                    .setImage(tbUser.getImage())
                    .setNickname(tbUser.getNickname())
                    .setStatus(tbUser.getStatus())
                    .setIsLogin(0);
            if (!json.equals("null")) {
                groupUserVo.setIsLogin(1);
            }
            users.add(groupUserVo);
        }
        groupInfoVo.setUsers(users);
        return groupInfoVo;
    }

    @Override
    public List<TbGroup> findGroupListByUserId(Integer userId) {
        LambdaQueryWrapper<TbGroupToUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TbGroupToUser::getUser_id, userId);
        List<TbGroupToUser> tbGroupToUsers = tbGroupToUserMapper.selectList(lambdaQueryWrapper);
        List<TbGroup> groupList = new ArrayList<>();
        for (TbGroupToUser tbGroupToUser:tbGroupToUsers) {
            TbGroup tbGroup = tbGroupMapper.selectById(tbGroupToUser.getGroup_id());
            groupList.add(tbGroup);
        }
        return groupList;
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
