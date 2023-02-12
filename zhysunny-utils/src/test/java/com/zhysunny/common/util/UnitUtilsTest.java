package com.zhysunny.common.util;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * UnitUtils Test.
 */
public class UnitUtilsTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test UnitUtils Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test UnitUtils Class End...");
    }

    @Test
    public void getRateUnit() {
        double d = 15651521354531D;
        assertEquals("14.23 TB", UnitUtils.getCapacityUnit(d));
        assertEquals("14576.62 GB", UnitUtils.getCapacityUnit(d, "G"));
        assertEquals(1.56515050717184E12d, UnitUtils.getCapacity("1457.66 GB").doubleValue(), 5);
        assertEquals("1.57 * 10^13", UnitUtils.getNumberUnit(d));
        assertEquals("33.42 %", UnitUtils.getRateUnit(523, 1565));
    }

} 
