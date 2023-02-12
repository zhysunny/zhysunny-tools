package com.zhysunny.common.date;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * DateUtils Test
 * @author 章云
 * @date 2020/2/29 18:34
 */
public class DateUtilsTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test DateUtils Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test DateUtils Class End...");
    }

    /**
     * Method: getCurrentDateTime()
     */
    @Test
    public void testGetCurrentDateTime() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getCurrentDateTime(String format)
     */
    @Test
    public void testGetCurrentDateTimeFormat() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getDay(int day)
     */
    @Test
    public void testGetDayDay() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getDay(String format, int day)
     */
    @Test
    public void testGetDayForFormatDay() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getDay(String fromFormat, String fromDate, String toFormat, int day)
     */
    @Test
    public void testGetDayForFromFormatFromDateToFormatDay() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getMonth(String format, int month)
     */
    @Test
    public void testGetMonth() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getYear(String format, int year)
     */
    @Test
    public void testGetYear() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: diffSeconds(String datetime1, String datetime2)
     */
    @Test
    public void testDiffSeconds() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getFirstDayOfMonth(int year, int month)
     */
    @Test
    public void testGetFirstDayOfMonth() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getLastDayOfMonth(int year, int month)
     */
    @Test
    public void testGetLastDayOfMonth() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getStringOfString(String fromFormat, String dateStr, String toFormat)
     */
    @Test
    public void testGetStringOfString() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getStringOfDate(Date date, String format)
     */
    @Test
    public void testGetStringOfDate() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getStringOfLong(long time, String format)
     */
    @Test
    public void testGetStringOfLong() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getDateOfString(String date, String format)
     */
    @Test
    public void testGetDateOfString() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getDateOfLong(long time)
     */
    @Test
    public void testGetDateOfLong() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getLongOfDate(Date date)
     */
    @Test
    public void testGetLongOfDate() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getLongOfString(String dateStr, String format)
     */
    @Test
    public void testGetLongOfString() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getTimeZoneDate(Date date, String formate, String timeZoneId)
     */
    @Test
    public void testGetTimeZoneDateForDateFormateTimeZoneId() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getLengthOfTime(long seconds)
     */
    @Test
    public void testGetLengthOfTime() throws Exception {
        assertEquals(DateUtils.getLengthOfTime(10), "10 秒");
        assertEquals(DateUtils.getLengthOfTime(1000), "16 分钟 40 秒");
        assertEquals(DateUtils.getLengthOfTime(10000), "2 小时 46 分钟 40 秒");
        assertEquals(DateUtils.getLengthOfTime(100000), "1 天 3 小时 46 分钟 40 秒");
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
        //TODO: Test goes here...
    }

} 
