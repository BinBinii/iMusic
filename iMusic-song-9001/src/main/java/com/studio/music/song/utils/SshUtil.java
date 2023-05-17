package com.studio.music.song.utils;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: BinBin
 * @Date: 2023/05/08/22:29
 * @Description:
 */
public class SshUtil {
    public String executeCommand(String host, String user, String password, String command) throws JSchException, IOException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);
        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);

        InputStream inputStream = channel.getInputStream();
        channel.connect();

        String output = readInputStream(inputStream);

        channel.disconnect();
        session.disconnect();

        return output;
    }

    private String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        byte[] buffer = new byte[1024];
        while (true) {
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1) {
                break;
            }
            output.append(new String(buffer, 0, bytesRead));
        }
        return output.toString();
    }
}
