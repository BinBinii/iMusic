package com.studio.music.song.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.studio.music.common.utils.IdGen;
import com.studio.music.song.mapper.TbUserMapper;
import com.studio.music.song.model.dto.RegisterDto;
import com.studio.music.song.model.pojo.TbUser;
import com.studio.music.song.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: BinBin
 * @Date: 2023/05/31/20:53
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${spring.mail.code.pre}")
    private String CODE_PRE;

    @Override
    public int register(RegisterDto registerDto) {
        TbUser tbUser = new TbUser();
        if (registerDto.getEmail().isEmpty() || registerDto.getPassword().isEmpty() || registerDto.getCode().isEmpty()) {
            return -1;
        }
        // 从redis获取该用户的验证码
        String code = String.valueOf(redisTemplate.opsForValue().get(CODE_PRE + ":" + registerDto.getEmail() + ""));
        if (!code.equals(registerDto.getCode())) {
            return -2;
        }
        boolean result = checkData(registerDto.getEmail());
        if (!result) {
            return 0;
        }
        // 写入用户表
        tbUser.setId(IdGen.randomInteger())
                .setEmail(registerDto.getEmail())
                .setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()))
                .setImage("")
                .setNickname("游客" + IdGen.randomInteger())
                .setStatus(1);
        if (tbUserMapper.insert(tbUser) != 1) {
            return -2;
        }
        // 注册成功后redis删除验证码记录
        redisTemplate.delete(CODE_PRE + ":" + registerDto.getEmail() + "");
        return 1;
    }

    @Override
    public List<TbUser> searchUser(String search) {
        if (search.isEmpty()) {
            return null;
        }
        LambdaQueryWrapper<TbUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (search.matches("\\d+")) {
            lambdaQueryWrapper.eq(TbUser::getId, Integer.parseInt(search));
        }
        lambdaQueryWrapper.eq(TbUser::getEmail, search)
                .or().eq(TbUser::getNickname, search);
        List<TbUser> list = tbUserMapper.selectList(lambdaQueryWrapper);
        list.forEach(tbUser -> tbUser.setPassword(""));
        return list;
    }

    private boolean checkData(String email) {
        boolean result = true;
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        List<TbUser> list = tbUserMapper.selectList(queryWrapper);
        if (list != null  && list.size() > 0) {
            result = false;
        }
        return result;
    }
}
