package com.studio.music.song.model.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: BinBin
 * @Date: 2023/05/17/15:16
 * @Description:
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TbPointTo {

    private Integer user_id;        // 用户ID

    private Integer group_id;       // 群ID

}
