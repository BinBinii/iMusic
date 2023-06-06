package com.studio.music.song.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: BinBin
 * @Date: 2023/05/21/22:35
 * @Description:
 */
@Data
public class ExamineApplyDto implements Serializable {
    private Integer applyId;

    private Integer admin;

    private Integer status;
}
