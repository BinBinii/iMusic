package com.studio.music.song.service;

import com.studio.music.song.model.dto.RegisterDto;
import org.springframework.stereotype.Service;

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

}
