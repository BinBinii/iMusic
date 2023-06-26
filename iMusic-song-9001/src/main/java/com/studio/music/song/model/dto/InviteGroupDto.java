package com.studio.music.song.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: BinBin
 * @Date: 2023/05/21/22:13
 * @Description:
 */
@Data
public class InviteGroupDto implements Serializable {
    private Integer groupId;

    private Integer[] userIds;

    private String userNickname;
}
