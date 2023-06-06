package com.studio.music.song.service;

import com.studio.music.song.model.vo.MailVo;
import org.springframework.stereotype.Service;

/**
 * @Author: BinBin
 * @Date: 2022/12/13/15:27
 * @Description:
 */
@Service
public interface MailService {
    /**
     * 发送邮箱请求
     * @param mailVo
     * @return
     */
    boolean launchMailRequest(MailVo mailVo);

    /**
     * 发送邮箱
     * @param mailVo
     * @return
     */
    boolean sendMail(MailVo mailVo);
}
