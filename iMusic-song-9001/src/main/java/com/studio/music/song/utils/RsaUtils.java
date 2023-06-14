package com.studio.music.song.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;

public class RsaUtils {

    public static SecretKey getSecretKey(String filename) throws Exception {
        byte[] bytes = readFile(filename);
        return getSecretKey(bytes);
    }


    public static SecretKey getSecretKey(byte[] bytes) {
        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    /**
     * 根据密文，生成密钥，并写入指定文件
     *
     * @param secretKeyFilename  密钥文件路径
     * @param secret             生成密钥的密文
     */
    public static void generateKey(String secretKeyFilename, String secret) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        keyGenerator.init(secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();

        byte[] secretKeyBytes = secretKey.getEncoded();
        writeFile(secretKeyFilename, secretKeyBytes);
    }

    private static byte[] readFile(String fileName) throws Exception {
        return Files.readAllBytes(new File(fileName).toPath());
    }

    private static void writeFile(String destPath, byte[] bytes) throws IOException {
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);
    }
}