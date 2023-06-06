package com.studio.music.song.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: BinBin
 * @Date: 2022/12/12/01:57
 * @Description:
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RegisterDto implements Serializable {

    private String email;

    private String password;

    private String code;
}
