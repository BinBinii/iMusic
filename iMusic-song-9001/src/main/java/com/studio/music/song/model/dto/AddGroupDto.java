package com.studio.music.song.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: BinBin
 * @Date: 2023/05/21/22:10
 * @Description:
 */
@Data
public class AddGroupDto implements Serializable {
    private Integer groupId;   // 群ID

    private Integer userId;    // 申请人ID
}
