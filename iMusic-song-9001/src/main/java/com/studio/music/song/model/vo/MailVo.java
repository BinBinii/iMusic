package com.studio.music.song.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: BinBin
 * @Date: 2022/12/13/15:28
 * @Description: 邮件
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MailVo implements Serializable {
    private String mail;

    private String title;

    private String content;
}

