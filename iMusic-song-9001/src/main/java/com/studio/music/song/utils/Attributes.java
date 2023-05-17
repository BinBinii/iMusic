package com.studio.music.song.utils;

import com.studio.music.song.model.pojo.TbUser;
import io.netty.util.AttributeKey;

/**
 * 设置channel的属性来判断有没有登录   ---》 channel.attr()
 * @author holiday
 * 2020-10-22
 */
public interface Attributes {
    AttributeKey<TbUser> SESSION = AttributeKey.newInstance("session");
}
