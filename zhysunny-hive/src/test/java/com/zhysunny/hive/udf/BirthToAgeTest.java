package com.zhysunny.hive.udf;

import static org.junit.Assert.*;
import com.zhysunny.common.date.DateUtils;
import org.junit.*;

/**
 * BirthToAge AvgBirthToAge.
 * @author 章云
 * @date 2019/11/13 16:06
 */
public class BirthToAgeTest {

    private BirthToAge birthToAge;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("AvgBirthToAge BirthToAge Class Start...");
    }

    @Before
    public void before() throws Exception {
        birthToAge = new BirthToAge();
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("AvgBirthToAge BirthToAge Class End...");
    }

    /**
     * Method: toAge(String date)
     */
    @Test
    public void testToAge() throws Exception {
        int today = Integer.parseInt(DateUtils.getCurrentDateTime("yyyy"));
        assertEquals(birthToAge.evaluate("1994-01-09").get(), today - 1994);
        assertEquals(birthToAge.evaluate("19940109", "yyyyMMdd").get(), today - 1994);
    }

}
