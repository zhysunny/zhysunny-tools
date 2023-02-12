package com.zhysunny.net.sftp;

import java.io.File;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * Sftp连接服务
 * @author 章云
 * @date 2019/7/27 15:35
 */
public class SftpConnection {
    private String host;
    private String username;
    private String password;

    private Session session;
    private Channel channel;
    private ChannelSftp channelSftp;

    public SftpConnection(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
        try {
            JSch jSch = new JSch();
            session = jSch.getSession(username, host, 22);
            session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setTimeout(5000);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
        } catch (Exception e) {
            close();
            channelSftp = null;
        }
    }

    /**
     * 上传文件或目录
     * @param file
     * @param toPath
     * @return
     * @throws Exception
     */
    public boolean put(File file, String toPath) throws Exception {
        if (channelSftp == null) {
            throw new Exception("SFTP连接拒绝");
        }
        boolean flag = false;
        try {
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    for (File f : files) {
                        put(f, toPath + "/" + file.getName());
                    }
                } else {
                    try {
                        // 如果目录存在会异常
                        channelSftp.mkdir(toPath);
                    } catch (Exception e) {
                    }
                    channelSftp.put(file.getAbsolutePath(), toPath + "/" + file.getName());
                    flag = true;
                }
            } else {
                throw new Exception("本地文件不存在" + file.getAbsolutePath());
            }
        } catch (SftpException e) {
            throw new Exception("SFTP上传异常", e);
        }
        return flag;
    }

    public boolean put(String filePath, String toPath) throws Exception {
        File file = new File(filePath);
        return put(file, toPath);
    }

    /**
     * 下载文件
     * @param filePath
     * @param localFile
     * @return
     * @throws Exception
     */
    public boolean get(String filePath, File localFile) throws Exception {
        if (channelSftp == null) {
            throw new Exception("SFTP连接拒绝");
        }
        boolean flag = false;
        try {
            channelSftp.get(filePath, localFile.getAbsolutePath());
            flag = true;
        } catch (SftpException e) {
            throw new Exception("SFTP下载异常", e);
        }
        return flag;
    }

    public void close() {
        if (channelSftp != null) {
            channelSftp.quit();
        }
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
