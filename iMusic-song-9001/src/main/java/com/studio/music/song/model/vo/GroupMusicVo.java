package com.studio.music.song.model.vo;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @Author: BinBin
 * @Date: 2023/06/01/11:19
 * @Description:
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GroupMusicVo {

    private Integer groupId;                // 群ID

    private List<MusicVo> musicVos;

}
