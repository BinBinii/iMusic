package com.studio.music.common.utils;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class IdGen implements Serializable {
    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    public static int randomInteger() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 8; i++){
            str.append(random.nextInt(10));
        }
        int num = Integer.parseInt(str.toString());
        return num;
    }
}
