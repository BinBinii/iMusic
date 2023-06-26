package com.studio.music.song.model.vo;

import com.studio.music.song.model.pojo.TbGroup;
import com.studio.music.song.model.pojo.TbUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: BinBin
 * @Date: 2023/06/25/22:37
 * @Description:
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GroupApplyVo {

    private Integer id;         // 自增ID

    private TbGroup tbGroup;

    private TbUser tbUser;

    private Date apply_time;    // 申请时间

    private Integer status;     // 0:未通过 1:已通过 2:已拒绝

}
