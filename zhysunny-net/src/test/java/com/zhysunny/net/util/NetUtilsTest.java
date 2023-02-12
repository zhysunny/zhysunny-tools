package com.zhysunny.net.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static com.zhysunny.net.util.NetUtils.getLocalIp;
import static com.zhysunny.net.util.NetUtils.splitIp;

/**
 * NetUtils Test
 */
public class NetUtilsTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test NetUtils Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test NetUtils Class End...");
    }

    /**
     * Method: getLocalIp()
     */
    @Test
    public void testGetLocalIp() throws Exception {
        System.out.println(getLocalIp());
    }

    /**
     * Method: splitIp(String ipString)
     */
    @Test
    public void testSplitIp() throws Exception {
        String ipString = "192.168.31.10,192.168.31.11-192.168.31.50,192.168.31.129-138";
        System.out.println(splitIp(ipString));
    }

} 
