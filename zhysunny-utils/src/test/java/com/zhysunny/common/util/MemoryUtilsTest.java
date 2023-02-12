package com.zhysunny.common.util;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MemoryUtils Test.
 * @author 章云
 * @date 2019/12/9 16:34
 */
public class MemoryUtilsTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test MemoryUtils Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test MemoryUtils Class End...");
    }

    @Test
    public void testGetMemorySize() throws Exception {
        System.out.println(MemoryUtils.getMemorySize(false));
        System.out.println(UnitUtils.getCapacityUnit(Runtime.getRuntime().totalMemory()));
        System.out.println(UnitUtils.getCapacityUnit(Runtime.getRuntime().freeMemory()));
        System.out.println(UnitUtils.getCapacityUnit(Runtime.getRuntime().maxMemory()));
    }

}
