package com.studio.music.auth.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: BinBin
 * @Date: 2023/04/25/11:30
 * @Description:
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TbUser {
    @TableId
    private String id;          // 自增ID

    private String username;        // 用户名（账号）

    private String password;        // 密码

    private Integer sex;            // 性别

    private String image;           // 头像

    private String nickname;        // 昵称

    private Integer status;         // 状态
}
