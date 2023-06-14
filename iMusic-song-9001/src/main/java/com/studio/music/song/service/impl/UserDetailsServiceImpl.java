package com.studio.music.song.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.studio.music.song.mapper.TbRoleMapper;
import com.studio.music.song.mapper.TbUserMapper;
import com.studio.music.song.mapper.TbUserRoleMapper;
import com.studio.music.song.model.pojo.TbRole;
import com.studio.music.song.model.pojo.TbUser;
import com.studio.music.song.model.pojo.TbUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: BinBin
 * @Date: 2023/04/25/16:27
 * @Description: 验证token中信息是否允许访问
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private TbUserRoleMapper tbUserRoleMapper;
    @Autowired
    private TbRoleMapper tbRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LambdaQueryWrapper<TbUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TbUser::getEmail, "847024724@qq.com");
        TbUser tbUser = tbUserMapper.selectOne(lambdaQueryWrapper);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<TbUserRole> tbUserRoles = tbUserRoleMapper.selectList(new QueryWrapper<TbUserRole>().eq("user_id", tbUser.getId()));
        tbUserRoles.forEach(tbUserRole -> {
            TbRole tbRole = tbRoleMapper.selectById(tbUserRole.getRole_id());
            GrantedAuthority grantedAuthority = tbRole::getAuthority;
            grantedAuthorities.add(grantedAuthority);
        });
        return new User(tbUser.getEmail(), tbUser.getPassword(), grantedAuthorities);
    }
}

