package com.zhysunny.net.ssh;

import java.io.File;
import java.io.FileFilter;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import com.zhysunny.net.util.NetUtils;

/**
 * java版scp操作
 * @author 章云
 * @date 2019/7/27 23:30
 */
@Deprecated
public class ScpConnection {
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

    /**
     * scp连接
     */
    private Connection conn;

    public ScpConnection(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public ScpConnection getClient() {
        try {
            conn = new Connection(host);
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
            if (isAuthenticated == false) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 上传文件
     * @param localPath 本地路径
     * @param toPath    上传路径
     * @return
     */
    public void put(String localPath, String toPath) throws Exception {
        File programFile = new File(localPath.replaceAll("\\*", ""));
        if (programFile.isDirectory()) {
            try {
                String newRemoteDir = null;
                if (!localPath.contains("*")) {
                    newRemoteDir = toPath + File.separator + programFile.getName();
                } else {
                    newRemoteDir = toPath;
                }
                Session session = conn.openSession();
                session.execCommand("mkdir -p " + newRemoteDir + File.separator);
                session.close();
                Thread.sleep(1000);// 暂停1s
                File[] files = programFile.listFiles(new IsFileFilter());
                if (null != files && files.length != 0) {
                    String[] fileNames = new String[files.length];
                    for (int i = 0; i < files.length; i++) {
                        fileNames[i] = files[i].getAbsolutePath();
                    }
                    SCPClient scpClient = new SCPClient(conn);
                    scpClient.put(fileNames, newRemoteDir);
                }
                files = programFile.listFiles(new IsDirFilter());
                if (null != files && files.length != 0) {
                    for (File file : files) {
                        put(file.getAbsolutePath(), newRemoteDir);
                    }
                }
            } catch (Exception e) {
                throw new Exception(e);
            }
        } else {
            try {
                SCPClient scpClient = new SCPClient(conn);
                System.out.println(localPath);
                scpClient.put(localPath, toPath);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * 目录过滤器
     */
    class IsDirFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            return pathname.isDirectory();
        }
    }

    /**
     * 文件过滤器
     */
    class IsFileFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            return pathname.isFile();
        }
    }

}
