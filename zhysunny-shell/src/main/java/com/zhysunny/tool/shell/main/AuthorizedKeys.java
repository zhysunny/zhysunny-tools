package com.zhysunny.tool.shell.main;

import com.zhysunny.net.sftp.SftpConnection;
import com.zhysunny.net.ssh.SshConnection;
import com.zhysunny.net.util.NetUtils;
import com.zhysunny.tool.shell.constant.FinalConstants;
import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.util.*;

/**
 * 自动化配置免秘钥
 * @author 章云
 * @date 2019/10/15 11:07
 */
public class AuthorizedKeys {

    public static void main(String[] args) throws IOException {
        if (args == null || args.length < 3) {
            System.err.println("Usage: ");
            System.err.println("Params: <ip_string> <username:root> <password>");
            System.err.println("多个IP地址以','分隔。可输入ip范围，以'-'分隔。");
            System.err.println("如：192.9.200.164,192.9.200.190-192.9.200.194,192.9.200.198");
            System.err.println("username 用户名 password 用户名对应的密码 username默认是root");
            System.exit(1);
        }
        List<String> ipList = NetUtils.splitIp(args[0]);
        String username = args[1];
        if (StringUtils.isEmpty(username)) {
            username = FinalConstants.ROOT;
        }
        String password = args[2];
        System.out.println("免密配置节点：" + ipList);
        System.out.println("用户名：" + username);
        System.out.println("密码：" + password);
        String sshPath = getSshPath(username);
        Map<String, Map<String, String>> infoMap = new TreeMap<>();
        for (String host : ipList) {
            SshConnection ssh = new SshConnection(host, username, password);
            if (createPublicKey(ssh, sshPath)) {
                Map<String, String> info = getNodeSecretKey(ssh, sshPath);
                infoMap.put(info.get(FinalConstants.HOST), info);
            }
            ssh.close();
        }
        InputStream is = new FileInputStream("/etc/hosts");
        File hosts = new File("hosts");
        boolean hasHosts = createHostsFile(infoMap, is, hosts);
        File keyFile = new File("authorized_keys");
        boolean hasKey = createKeyFile(infoMap, keyFile);
        if (hasHosts && hasKey) {
            for (String host : ipList) {
                SftpConnection sftp = new SftpConnection(host, username, password);
                putFileBySftp(sftp, hosts, keyFile, sshPath);
                sftp.close();
            }
        }
        hosts.delete();
        keyFile.delete();
    }

    /**
     * 秘钥所在目录
     * @param username 用户名
     * @return
     */
    public static String getSshPath(String username) {
        if (StringUtils.isEmpty(username) || FinalConstants.ROOT.equals(username)) {
            return "/root/.ssh";
        } else {
            return "/home/" + username + "/.ssh";
        }
    }

    /**
     * 判断公钥是否已经存在
     * @param ssh     ssh连接
     * @param sshPath 秘钥所在目录
     * @return 返回是否存在公钥配置文件
     */
    public static boolean createPublicKey(SshConnection ssh, String sshPath) throws IOException {
        List<String> names = ssh.sendCommand("ls -l " + sshPath + " | grep \"^-\" | awk '{print $9}'");
        if (names.contains(FinalConstants.ID_RSA)) {
            return true;
        }
        // 如果不存在id_rsa，则生成秘钥
        List<String> result = ssh.sendCommand("ssh-keygen -t rsa -P '' -f '" + sshPath + "/id_rsa'");
        if (result.size() > 10) {
            // 测试centos7 结果是17行
            return true;
        }
        return false;
    }

    /**
     * 获取节点的信息
     * @param ssh
     * @param sshPath
     * @return
     * @throws IOException
     */
    public static Map<String, String> getNodeSecretKey(SshConnection ssh, String sshPath) throws IOException {
        Map<String, String> info = new HashMap<>(4);
        String hostname = ssh.sendCmd("hostname");
        String key = ssh.sendCmd("cat " + sshPath + "/id_rsa.pub");
        String host = ssh.getHost();
        info.put(FinalConstants.HOSTNAME, hostname);
        info.put(FinalConstants.HOST, host);
        info.put(FinalConstants.AUTHORIZED_KEYS, key);
        return info;
    }

    /**
     * 生成新的hosts文件
     * @param infoMap
     * @param is
     * @param toFile
     * @return
     */
    public static boolean createHostsFile(Map<String, Map<String, String>> infoMap, InputStream is, File toFile) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is)); FileOutputStream fos = new FileOutputStream(toFile);) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (StringUtils.isEmpty(line)) {
                    continue;
                }
                StringTokenizer tokenizer = new StringTokenizer(line, " \t");
                String host = tokenizer.nextToken();
                if (!infoMap.containsKey(host)) {
                    lines.add(line);
                }
            }
            infoMap.forEach((host, info) -> lines.add(host + " " + info.get(FinalConstants.HOSTNAME)));
            lines.forEach(str -> {
                try {
                    fos.write((str + '\n').getBytes());
                } catch (IOException e) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 生成新的authorized_keys文件
     * @param infoMap
     * @param toFile
     * @return
     */
    public static boolean createKeyFile(Map<String, Map<String, String>> infoMap, File toFile) {
        try (FileOutputStream fos = new FileOutputStream(toFile);) {
            infoMap.forEach((host, info) -> {
                try {
                    fos.write((info.get(FinalConstants.AUTHORIZED_KEYS) + '\n').getBytes());
                } catch (IOException e) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 上传文件
     * @param sftp
     * @param hosts
     * @param keyFile
     * @param sshPath
     * @return
     */
    public static boolean putFileBySftp(SftpConnection sftp, File hosts, File keyFile, String sshPath) {
        try {
            sftp.put(hosts, "/etc");
            sftp.put(keyFile, sshPath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
