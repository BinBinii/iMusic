package com.studio.music.song.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: BinBin
 * @Date: 2023/05/21/21:05
 * @Description:
 */
@Data
public class CreateGroupDto implements Serializable {
    private String name;            // 群名

    private Integer admin;          // 管理员ID
}
