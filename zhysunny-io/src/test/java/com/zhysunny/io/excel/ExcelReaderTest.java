package com.zhysunny.io.excel;

import static org.junit.Assert.*;

import com.zhysunny.io.xml.XmlReaderTest;
import org.junit.*;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * ExcelReader Test.
 * @author 章云
 * @date 2019/7/25 18:11
 */
public class ExcelReaderTest {

    private String resource;
    private String path;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test ExcelReader Class Start...");
    }

    @Before
    public void before() throws Exception {
        this.resource = "excel/input.xlsx";
        this.path = "src/test/resources/" + resource;
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test ExcelReader Class End...");
    }

    /**
     * Method: read(Class<T> clz)
     */
    @Test
    public void testRead() throws Exception {
        ExcelReader reader = new ExcelReader(path);
        Map<String, List<Map<String, String>>> dataMap = reader.readToMap();
        System.out.println(dataMap);
        Map<String, Map<String, List<String>>> dataLessMap = reader.readToLessMap();
        System.out.println(dataLessMap);
    }

    /**
     * Method: readLine()
     */
    @Test
    public void testReadLine() throws Exception {
        URL url = ExcelReaderTest.class.getClassLoader().getResource(resource);
        ExcelReader reader = new ExcelReader(url).builder();
        for (int i = 0; i < reader.getSheetCount(); i++) {
            reader.readTitle(i);
            System.out.println(reader.getSheetName());
            Map<String, String> data = null;
            while ((data = reader.readLine()) != null) {
                System.out.println(data);
            }
        }
    }

}
