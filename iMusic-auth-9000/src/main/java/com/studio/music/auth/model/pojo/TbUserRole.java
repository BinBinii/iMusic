package com.studio.music.auth.model.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: BinBin
 * @Date: 2023/03/15/15:14
 * @Description: 用户权限表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TbUserRole implements Serializable {
    private Integer user_id;        // 用户ID

    private Integer role_id;        // 规则ID
}
