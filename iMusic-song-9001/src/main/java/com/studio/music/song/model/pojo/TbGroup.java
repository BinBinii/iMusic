package com.studio.music.song.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: BinBin
 * @Date: 2023/05/17/14:58
 * @Description:
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TbGroup {

    @TableId
    private Integer id;             // 自增ID

    private String name;            // 群名

    private Integer admin;          // 管理员ID

    private Date create_time;       // 创建时间

    private String notice;          // 群公告

}
