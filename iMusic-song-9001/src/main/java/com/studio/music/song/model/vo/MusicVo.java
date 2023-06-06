package com.studio.music.song.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: BinBin
 * @Date: 2023/06/06/10:40
 * @Description:
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MusicVo {

    private String nickname;         // 点歌人昵称

    private Integer musicId;        // 歌曲ID
}
