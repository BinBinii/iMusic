package com.studio.music.song.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: BinBin
 * @Date: 2023/06/06/22:08
 * @Description:
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GroupUserVo {
    private Integer id;              // 自增ID

    private String email;           // 用户名（账号）

    private String image;           // 头像

    private String nickname;        // 昵称

    private Integer status;         // 状态

    private Integer isLogin;        // 是否为登录状态
}
