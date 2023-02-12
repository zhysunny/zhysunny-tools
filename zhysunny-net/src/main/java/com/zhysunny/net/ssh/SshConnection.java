package com.zhysunny.net.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.zhysunny.net.util.NetUtils;

/**
 * 远程发送shell命令
 * @author 章云
 * @date 2019/7/27 23:44
 */
public class SshConnection {

    /**
     * 当前正在连接的ip
     */
    private String host;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    private Connection conn;
    private Session session;

    public SshConnection(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
        getClient();
    }

    public SshConnection getClient() {
        try {
            conn = new Connection(host);
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
            if (isAuthenticated == false) {
                close();
                throw new IOException(host + "SSH密码验证失败");
            }
        } catch (IOException e) {
            close();
            conn = null;
        }
        return this;
    }

    /**
     * 发送命令，只获取第一行结果
     * @param command
     * @return
     * @throws IOException
     */
    public String sendCmd(String command) throws IOException {
        List<String> list = sendCommand(command);
        String result = "";
        if (list.size() > 0) {
            result = list.get(0);
        }
        return result;
    }

    /**
     * 发送命令，结果可能是多行
     * @param command
     * @return
     * @throws IOException
     */
    public List<String> sendCommand(String command) throws IOException {
        if (conn == null) {
            throw new IOException("SSH连接拒绝");
        }
        List<String> result = null;
        BufferedReader br = null;
        try {
            // 每个命令创建一个session
            session = conn.openSession();
            session.execCommand(command);
            br = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout())));
            String line = null;
            result = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                if (line.trim().length() != 0) {
                    result.add(line.trim());
                }
            }
        } catch (IOException e) {
            throw new IOException("发送SSH命令失败", e);
        } finally {
            if (br != null) {
                br.close();
            }
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public void close() {
        if (conn != null) {
            conn.close();
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
