package com.zhysunny.driver.util;

import org.junit.*;

/**
 * Constants Test.
 * @author 章云
 * @date 2019/8/15 11:45
 */
public class ConstantsTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test Constants Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test Constants Class End...");
    }

    @Test
    public void testDriverConstants() throws Exception {
        Constants.Driver[] values = Constants.Driver.values();
        for (Constants.Driver driver : values) {
            System.out.println("----------------------------");
            System.out.println(driver.getClassName());
            System.out.println(driver.getName());
            System.out.println(driver.getIndex());
        }
    }

}
