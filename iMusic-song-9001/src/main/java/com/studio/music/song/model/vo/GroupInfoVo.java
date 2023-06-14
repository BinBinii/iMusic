package com.studio.music.song.model.vo;

import com.studio.music.song.model.pojo.TbGroupToUser;
import com.studio.music.song.model.pojo.TbUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: BinBin
 * @Date: 2023/06/06/21:34
 * @Description:
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GroupInfoVo {
    private Integer id;             // 自增ID

    private String name;            // 群名

    private TbUser admin;

    private Date create_time;       // 创建时间

    private String notice;          // 群公告

    private List<GroupUserVo> users;  // 人员
}
