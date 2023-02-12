package com.zhysunny.net.ftp;

import com.zhysunny.net.util.NetUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.List;

/**
 * FTP连接服务
 * @author 章云
 * @date 2019/7/27 23:07
 */
public class FtpConnection {

    /**
     * 当前ftp连接
     */
    private FTPClient ftpClient;
    /**
     * 需要连接的ftp服务器，可以是多个ip
     */
    private String hosts;

    /**
     * ip集合
     */
    private List<String> ipList;
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
     * ipList的索引
     */
    private int index = 0;
    /**
     * 连接异常次数
     */
    private int count = 0;

    public FtpConnection(String hosts, String username, String password) {
        this.hosts = hosts;
        this.ipList = NetUtils.splitIp(hosts);
        this.username = username;
        this.password = password;
    }

    /**
     * 循环获取连接
     */
    public FtpConnection getClient() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        FTPClientConfig ftpClientConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
        ftpClient.configure(ftpClientConfig);
        if (index >= ipList.size()) {
            index = 0;
        }
        this.host = ipList.get(index);
        index++;
        try {
            ftpClient.connect(this.host);
            // FTP服务器连接回答
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            }
            ftpClient.login(username, password);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        } catch (Exception e) {
            // 连接异常换下一个IP
            getClient();
            count++;
            if (count >= ipList.size()) {
                count = 0;
                close();
                ftpClient = null;
            }
        }
        count = 0;
        return this;
    }

    /**
     * FTP上传文件
     * @param file   本地文件
     * @param toPath ftp路径
     * @return
     * @throws IOException
     */
    public boolean uploadFile(File file, String toPath) throws IOException {
        if (ftpClient == null) {
            throw new IOException("FTP连接拒绝");
        }
        String[] arr = toPath.split("/");
        int num = 0;
        if (arr.length > 0) {
            for (int i = 0, len = arr.length; i < len; i++) {
                if (arr[i] == null ? false : arr[i].trim().length() == 0 ? false : true) {
                    num++;
                    ftpClient.makeDirectory(arr[i]);
                    ftpClient.changeWorkingDirectory(arr[i]);
                }
            }
        }
        FileInputStream fis = null;
        boolean result = false;
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                result = ftpClient.storeFile(file.getName(), fis);
            }
        } catch (IOException e) {
            result = false;
            throw new IOException("FTP上传异常", e);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        if (num > 0) {
            for (int i = 0; i < num; i++) {
                ftpClient.changeToParentDirectory();
            }
        }
        return result;
    }

    /**
     * FTP上传文件
     * @param file 本地文件
     * @return
     * @throws IOException
     */
    public boolean uploadFile(File file) throws IOException {
        return uploadFile(file, "");
    }

    /**
     * FTP下载文件
     * @param ftpFilePath ftp文件路径
     * @param localFile   本地文件
     * @return
     * @throws IOException
     */
    public boolean downloadFile(String ftpFilePath, File localFile) throws IOException {
        if (ftpClient == null) {
            throw new IOException("FTP连接拒绝");
        }
        InputStream is = null;
        FileOutputStream fos = null;
        boolean result = false;
        try {
            is = ftpClient.retrieveFileStream(ftpFilePath);
            fos = new FileOutputStream(localFile);
            int count = 0;
            byte[] b = new byte[1024];
            while ((count = is.read(b)) > 0) {
                fos.write(b, 0, count);
            }
            result = true;
        } catch (IOException e) {
            result = false;
            throw new IOException("FTP下载异常", e);
        } finally {
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        return result;
    }

    /**
     * FTP下载文件
     * @param ftpFilePath   ftp文件路径
     * @param localFilePath 本地文件路径
     * @return
     * @throws IOException
     */
    public boolean downloadFile(String ftpFilePath, String localFilePath) throws IOException {
        File localFile = new File(localFilePath);
        return downloadFile(ftpFilePath, localFile);
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

    public List<String> getIpList() {
        return ipList;
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (ftpClient != null) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
