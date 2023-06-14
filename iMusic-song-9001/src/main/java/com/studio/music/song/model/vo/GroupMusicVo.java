package com.studio.music.song.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: BinBin
 * @Date: 2023/06/01/11:19
 * @Description: 群组音乐
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GroupMusicVo {

    private Integer groupId;                // 群ID

    private List<MusicVo> musicVos;

}
