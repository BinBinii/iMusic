package com.studio.music.song.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: BinBin
 * @Date: 2023/03/28/14:53
 * @Description: 消息队列传输实体
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MqMessageVo<T> implements Serializable {
    private String title;

    private T data;
}
