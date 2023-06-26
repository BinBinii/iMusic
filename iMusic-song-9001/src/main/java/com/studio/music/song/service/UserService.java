package com.studio.music.song.service;

import com.studio.music.song.model.dto.RegisterDto;
import com.studio.music.song.model.pojo.TbUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BinBin
 * @Date: 2023/05/31/20:51
 * @Description:
 */
@Service
public interface UserService {

    /**
     * 注册
     * @param registerDto
     * @return
     * @throws InterruptedException
     */
    int register(RegisterDto registerDto);

    /**
     * 根据账号ID或昵称或邮箱搜索用户
     * @param search
     * @return
     */
    List<TbUser> searchUser(String search);

}
