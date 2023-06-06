package com.studio.music.song.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: BinBin
 * @Date: 2023/06/05/20:19
 * @Description:
 */
@Data
@AllArgsConstructor
public class ChatMessage {
    private String name;

    private String message;
}
