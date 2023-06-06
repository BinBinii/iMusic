package com.studio.music.song.contacts;

/**
 * @Author: BinBin
 * @Date: 2023/06/02/11:11
 * @Description:
 */
public class RedisContacts {
    /**
     * 群组数据的key
     */
    public static final String GROUP_PRE = "GROUP_PRE";

    public static final Integer GROUP_PRE_EXPIRE = 900;

    /**
     * 群组音乐数据的key
     */
    public static final String GROUP_MUSICS_PRE = "GROUP_MUSICS_PRE";

    public static final Integer GROUP_MUSICS_PRE_EXPIRE = 900;

    /**
     * 用户会话的key
     */
    public static final String USER_SESSION = "USER_SESSION_PRE";

    public static final Integer USER_SESSION_EXPIRE = 900;

}
