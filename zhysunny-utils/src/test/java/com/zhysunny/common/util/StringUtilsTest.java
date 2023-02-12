package com.zhysunny.common.util;

import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;

/**
 * StringUtils Test.
 * @author 章云
 * @date 2019/12/30 14:10
 */
public class StringUtilsTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test StringUtils Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test StringUtils Class End...");
    }

    /**
     * Method: isByte(String str)
     */
    @Test
    public void testIsByte() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isShort(String str)
     */
    @Test
    public void testIsShort() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isInteger(String str)
     */
    @Test
    public void testIsInteger() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isLong(String str)
     */
    @Test
    public void testIsLong() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isFloat(String str)
     */
    @Test
    public void testIsFloat() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isDouble(String str)
     */
    @Test
    public void testIsDouble() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isBoolean(String str)
     */
    @Test
    public void testIsBoolean() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isIdcard(String str)
     */
    @Test
    public void testIsIdcard() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isMobilePhone(String str)
     */
    @Test
    public void testIsMobilePhone() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isIpAddress(String str)
     */
    @Test
    public void testIsIpAddress() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isMoney(String str)
     */
    @Test
    public void testIsMoney() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isCarNum(String str)
     */
    @Test
    public void testIsCarNum() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isEmail(String str)
     */
    @Test
    public void testIsEmail() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: containsIdcard(String str)
     */
    @Test
    public void testContainsIdcard() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: containsMobilePhone(String str)
     */
    @Test
    public void testContainsMobilePhone() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: containsIpAddress(String str)
     */
    @Test
    public void testContainsIpAddress() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: containsMoney(String str)
     */
    @Test
    public void testContainsMoney() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: containsCarNum(String str)
     */
    @Test
    public void testContainsCarNum() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: containsEmail(String str)
     */
    @Test
    public void testContainsEmail() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isEmptyParamOne(final Object... params)
     */
    @Test
    public void testIsEmptyParamOne() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: isEmptyParamAll(final Object... params)
     */
    @Test
    public void testIsEmptyParamAll() throws Exception {
        String[] strings = new String[0];
        assertTrue(StringUtils.isEmptyParamOne(strings));
        String string = "";
        assertTrue(StringUtils.isEmptyParamOne(string));
        Map<String, String> map = new HashMap<>();
        assertTrue(StringUtils.isEmptyParamOne(map));
        Set<String> set = new HashSet<>();
        assertTrue(StringUtils.isEmptyParamOne(set));
        List<String> list = new ArrayList<>();
        assertTrue(StringUtils.isEmptyParamOne(list));
        byte[] bytes = new byte[0];
        assertTrue(StringUtils.isEmptyParamOne(bytes));
    }

} 
