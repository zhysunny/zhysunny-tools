package com.zhysunny.tool.shell.main;

import com.zhysunny.net.sftp.SftpConnection;
import com.zhysunny.net.ssh.SshConnection;
import com.zhysunny.tool.shell.constant.FinalConstants;
import org.junit.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import static com.zhysunny.tool.shell.main.AuthorizedKeys.*;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * AuthorizedKeys Test.
 * @author 章云
 * @date 2019/10/16 10:02
 */
public class AuthorizedKeysTest {

    private static SftpConnection sftp;
    private static SshConnection ssh;
    private static String host;
    private static String username;
    private static String password;
    private static String sshPath;
    private static Map<String, Map<String, String>> infoMap;
    private static File hosts;
    private static File keyFile;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test AuthorizedKeys Class Start...");
        host = "192.168.1.44";
        username = "root";
        password = "123456";
        ssh = mock(SshConnection.class);
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
        File temp = new File("temp");
        if (!temp.exists()) {
            temp.mkdirs();
        }
        hosts = new File(temp, "hosts");
        keyFile = new File(temp, "authorized_keys");
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
     * 模拟已包含秘钥的情况
     */
    private void mockContainsIdrsa() throws IOException {
        List<String> names = new ArrayList<>();
        names.add(FinalConstants.ID_RSA);
        when(ssh.sendCommand("ls -l " + sshPath + " | grep \"^-\" | awk '{print $9}'")).thenReturn(names);
    }

    /**
     * 模拟未包含秘钥的情况，生成秘钥返回正常信息的情况
     */
    private void mockNotContainsIdrsa() throws IOException {
        List<String> names = new ArrayList<>();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            result.add("");
        }
        when(ssh.sendCommand("ls -l " + sshPath + " | grep \"^-\" | awk '{print $9}'")).thenReturn(names);
        when(ssh.sendCommand("ssh-keygen -t rsa -P '' -f '" + sshPath + "/id_rsa'")).thenReturn(result);
    }

    /**
     * 模拟未包含秘钥的情况，生成秘钥返回正常信息的情况
     */
    private void mockNotContainsIdrsaAndError() throws IOException {
        List<String> names = new ArrayList<>();
        List<String> result = new ArrayList<>();
        result.add("");
        when(ssh.sendCommand("ls -l " + sshPath + " | grep \"^-\" | awk '{print $9}'")).thenReturn(names);
        when(ssh.sendCommand("ssh-keygen -t rsa -P '' -f '" + sshPath + "/id_rsa'")).thenReturn(result);
    }

    @Test
    public void testCreatePublicKeyContainsIdrsa() throws Exception {
        mockContainsIdrsa();
        assertTrue(createPublicKey(ssh, sshPath));
    }

    @Test
    public void testCreatePublicKeyNotContainsIdrsa() throws Exception {
        mockNotContainsIdrsa();
        assertTrue(createPublicKey(ssh, sshPath));
    }

    @Test
    public void testCreatePublicKeyNotContainsIdrsaAndError() throws Exception {
        mockNotContainsIdrsaAndError();
        assertFalse(createPublicKey(ssh, sshPath));
    }

    /**
     * 模拟返回值
     * @throws IOException
     */
    private void mockGetNodeSecretKey() throws IOException {
        when(ssh.sendCmd("hostname")).thenReturn("test4");
        when(ssh.sendCmd("cat " + sshPath + "/id_rsa.pub")).thenReturn("AUTHORIZED_KEYS");
        when(ssh.getHost()).thenReturn("192.168.1.44");
    }

    /**
     * Method: existPublicKey(SshConnection ssh, String sshPath)
     */
    @Test
    public void testGetNodeSecretKey() throws Exception {
        mockGetNodeSecretKey();
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
        File toFile = new File("temp/hosts");
        assertTrue(createHostsFile(infoMap, is, toFile));
    }

    /**
     * Method: createKeyFile(Map<String, Map<String, String>> infoMap, File toFile)
     */
    @Test
    public void testCreateKeyFile() throws Exception {
        assertTrue(createKeyFile(infoMap, keyFile));
    }

    private void mockPutFileBySftpSuccess() throws Exception {
        sftp = mock(SftpConnection.class);
        when(sftp.put(hosts, "/etc")).thenReturn(true);
        when(sftp.put(keyFile, sshPath)).thenReturn(true);
    }

    private void mockPutFileBySftpException() throws Exception {
        when(sftp.put(hosts, "/etc")).thenThrow(Exception.class);
    }

    /**
     * Method: putFileBySftp()
     */
    @Test
    public void testPutFileBySftp() throws Exception {
        // 模拟成功的情况
        mockPutFileBySftpSuccess();
        assertTrue(putFileBySftp(sftp, hosts, keyFile, sshPath));
        // 模拟失败的情况
        mockPutFileBySftpException();
        assertFalse(putFileBySftp(sftp, hosts, keyFile, sshPath));
    }

}
