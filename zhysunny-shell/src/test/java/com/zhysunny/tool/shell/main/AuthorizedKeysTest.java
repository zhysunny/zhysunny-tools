package com.zhysunny.tool.shell.main;

import static com.zhysunny.tool.shell.main.AuthorizedKeys.*;
import static org.junit.Assert.*;
import com.zhysunny.net.sftp.SftpConnection;
import com.zhysunny.net.ssh.SshConnection;
import com.zhysunny.net.util.NetUtils;
import com.zhysunny.tool.shell.constant.FinalConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * AuthorizedKeys Test.
 * @author 章云
 * @date 2019/10/16 10:02
 */
public class AuthorizedKeysTest {

    private static SshConnection ssh;
    private static String host;
    private static String username;
    private static String password;
    private static String sshPath;
    private static Map<String, Map<String, String>> infoMap;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test AuthorizedKeys Class Start...");
        host = "192.168.1.44";
        username = "root";
        password = "123456";
        ssh = new SshConnection(host, username, password);
        sshPath = "/root/.ssh";
        infoMap = new TreeMap<>();
        System.out.println("登录" + host + "，用户名" + username);
        for (int i = 1; i <= 3; i++) {
            Map<String, String> info = new HashMap<>();
            String host = "192.168.1.4" + i;
            info.put(FinalConstants.HOST, host);
            info.put(FinalConstants.HOSTNAME, "test" + i);
            info.put(FinalConstants.AUTHORIZED_KEYS, UUID.randomUUID().toString());
            infoMap.put(host, info);
        }
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("退出" + host);
        ssh.close();
        System.out.println("Test AuthorizedKeys Class End...");
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
        String[] args = new String[]{ "192.168.1.44", "root", "123456" };
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
        InputStream is = getClass().getClassLoader().getResourceAsStream("etc/hosts");
        File hosts = new File("src/test/resources/tmp/hosts");
        boolean hasHosts = createHostsFile(infoMap, is, hosts);
        File keyFile = new File("src/test/resources/tmp/authorized_keys");
        boolean hasKey = createKeyFile(infoMap, keyFile);
        if (hasHosts && hasKey) {
            for (String host : ipList) {
                SftpConnection sftp = new SftpConnection(host, username, password);
                putFileBySftp(sftp, hosts, keyFile, sshPath);
                sftp.close();
            }
        }
    }

    /**
     * Method: getSshPath(String username)
     */
    @Test
    public void testGetSshPath() throws Exception {
        assertEquals(getSshPath("root"), "/root/.ssh");
        assertEquals(getSshPath("zhy"), "/home/zhy/.ssh");
        assertFalse("/root/.ssh/".equals(getSshPath("root")));
        System.out.println("sshPath:" + getSshPath(username));
    }

    /**
     * Method: createPublicKey(SshConnection ssh, String sshPath)
     */
    @Test
    public void testCreatePublicKey() throws Exception {
        assertTrue(createPublicKey(ssh, sshPath));
    }

    /**
     * Method: existPublicKey(SshConnection ssh, String sshPath)
     */
    @Test
    public void testGetNodeSecretKey() throws Exception {
        Map<String, String> info = getNodeSecretKey(ssh, sshPath);
        assertEquals(info.get(FinalConstants.HOSTNAME), "test4");
        assertEquals(info.get(FinalConstants.HOST), "192.168.1.44");
        assertNotNull(info.get(FinalConstants.AUTHORIZED_KEYS));
        assertFalse("".equals(info.get(FinalConstants.AUTHORIZED_KEYS)));
        info.forEach((key, value) -> System.out.println(key + "=" + value));
    }

    /**
     * Method: createHostsFile(Map<String, Map<String, String>> infoMap, InputStream is, File toFile)
     */
    @Test
    public void testCreateHostsFile() throws Exception {
        // hosts文件需要先从/etc/hosts拷贝到当前位置
        // 读取hosts文件，新增映射关系
        InputStream is = getClass().getClassLoader().getResourceAsStream("etc/hosts");
        File toFile = new File("src/test/resources/tmp/hosts");
        assertTrue(createHostsFile(infoMap, is, toFile));
    }

    /**
     * Method: createKeyFile(Map<String, Map<String, String>> infoMap, File toFile)
     */
    @Test
    public void testCreateKeyFile() throws Exception {
        File toFile = new File("src/test/resources/tmp/authorized_keys");
        assertTrue(createKeyFile(infoMap, toFile));
    }

    /**
     * Method: putFileBySftp()
     */
    @Test
    public void testPutFileBySftp() throws Exception {
        SftpConnection sftp = new SftpConnection(host, username, password);
        File hosts = new File("src/test/resources/tmp/hosts");
        File keyFile = new File("src/test/resources/tmp/authorized_keys");
        assertTrue(putFileBySftp(sftp, hosts, keyFile, sshPath));
        sftp.close();
    }

}
