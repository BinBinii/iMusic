package com.studio.music.song.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: BinBin
 * @Date: 2023/05/21/22:08
 * @Description:
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TbGroupApply {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;         // 自增ID

    private Integer group_id;   // 群ID

    private Integer user_id;    // 申请人ID

    private Date apply_time;    // 申请时间

    private Integer status;     // 0:未通过 1:已通过 2:已拒绝

}
