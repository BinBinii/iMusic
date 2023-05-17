package com.studio.music.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.studio.music.auth.mapper.TbRoleMapper;
import com.studio.music.auth.mapper.TbUserMapper;
import com.studio.music.auth.mapper.TbUserRoleMapper;
import com.studio.music.auth.model.pojo.TbRole;
import com.studio.music.auth.model.pojo.TbUser;
import com.studio.music.auth.model.pojo.TbUserRole;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TbUser tbUser = tbUserMapper.selectOne(new QueryWrapper<TbUser>().eq("username", username));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<TbUserRole> tbUserRoles = tbUserRoleMapper.selectList(new QueryWrapper<TbUserRole>().eq("user_id", tbUser.getId()));
        tbUserRoles.forEach(tbUserRole -> {
            TbRole tbRole = tbRoleMapper.selectById(tbUserRole.getRole_id());
            GrantedAuthority grantedAuthority = tbRole::getAuthority;
            grantedAuthorities.add(grantedAuthority);
        });
        return new User(tbUser.getUsername(), tbUser.getPassword(), grantedAuthorities);
    }
}

