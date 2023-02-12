package com.zhysunny.io.properties;

import com.zhysunny.io.properties.constant.Constant;
import org.junit.*;

import java.util.Arrays;
import java.util.Properties;
import static org.junit.Assert.*;

/**
 * PropertiesReader Test.
 * @author 章云
 * @date 2019/7/27 12:04
 */
public class PropertiesReaderTest {

    private String resource;
    private String path;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test PropertiesReader Class Start...");
    }

    @Before
    public void before() throws Exception {
        this.resource = "properties/input.properties";
        this.path = "src/test/resources/" + resource;
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test PropertiesReader Class End...");
    }

    /**
     * Method: toConstant(Class<T> clz)
     */
    @Test
    public void testToConstant() throws Exception {
        PropertiesReader reader = new PropertiesReader(path).builder();
        Properties prop = reader.getProps();
        System.out.println(prop);
        assertEquals(prop.getProperty("address"), "192.168.8.2:2181:/hbase");
        reader.toConstant(Constant.class);
        System.out.println(Constant.address);
        System.out.println(Constant.value);
        System.out.println(Constant.value1);
        System.out.println(Constant.value2);
        System.out.println(Arrays.toString(Constant.array));
    }

}
