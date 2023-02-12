package com.zhysunny.io.excel;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.List;
import java.util.Map;

/**
 * ExcelWriter Test.
 * @author 章云
 * @date 2019/7/26 9:26
 */
public class ExcelWriterTest {

    private String input;
    private String output;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test ExcelWriter Class Start...");
    }

    @Before
    public void before() throws Exception {
        this.input = "src/test/resources/excel/input.xlsx";
        this.output = "src/test/resources/excel/output.xls";
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test ExcelWriter Class End...");
    }

    /**
     * Method: write(String encoding)
     */
    @Test
    public void testWrite() throws Exception {
        ExcelReader reader = new ExcelReader(input);
        Map<String, List<Map<String, String>>> dataMap = reader.readToMap();
        System.out.println(dataMap);
        new ExcelWriter(output).writeMap(dataMap);
        Map<String, Map<String, List<String>>> dataLessMap = reader.readToLessMap();
        System.out.println(dataLessMap);
        new ExcelWriter(output).writeLessMap(dataLessMap);
    }

}
