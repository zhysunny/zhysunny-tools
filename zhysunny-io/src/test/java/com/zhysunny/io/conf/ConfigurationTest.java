package com.zhysunny.io.conf;

import static org.junit.Assert.*;
import org.junit.*;

import java.util.Arrays;

/**
 * Configuration Test.
 * @author 章云
 * @date 2019/8/2 10:03
 */
public class ConfigurationTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test Configuration Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test Configuration Class End...");
    }

    @Test
    public void testSetDefaultValue() throws Exception {
        Configuration conf = Configuration.getInstance();
        conf.addDefaultResource("properties/input.properties");
        assertEquals(conf.getString(Constant.VALUE), "value");
        assertNull(conf.getString(Constant.DEFAULT));
        conf.setDefaultValue(Constant.class);
        assertEquals(conf.getString(Constant.DEFAULT), "20");
        assertEquals(conf.getInt(Constant.DEFAULT), 20);
    }

}
