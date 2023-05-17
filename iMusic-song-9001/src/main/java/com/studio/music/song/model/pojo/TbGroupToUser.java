package com.studio.music.song.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: BinBin
 * @Date: 2023/05/17/14:59
 * @Description:
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TbGroupToUser {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;             // 自增ID

    private Integer group_id;       // 群ID

    private Integer user_id;        // 用户ID

    private Date join_time;         // 进群时间

    private String nickname;        // 群内昵称

}
