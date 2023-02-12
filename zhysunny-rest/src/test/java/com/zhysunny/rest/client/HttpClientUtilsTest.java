package com.zhysunny.rest.client;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * HttpClientUtils Test
 */
public class HttpClientUtilsTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test HttpClientUtils Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test HttpClientUtils Class End...");
    }

    /**
     * Method: get(String url, Class<T> clz)
     */
    @Test
    public void testGet() throws Exception {
        String url = "http://192.168.1.12:7001/plugins";
        RestResponse restResponse = HttpClientUtils.get(url);
        System.out.println(restResponse);
    }

} 
