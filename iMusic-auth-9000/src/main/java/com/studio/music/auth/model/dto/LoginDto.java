package com.studio.music.auth.model.dto;

import lombok.Data;

/**
 * @Author: BinBin
 * @Date: 2023/04/26/09:50
 * @Description:
 */
@Data
public class LoginDto {
    private String email;

    private String password;
}
