package com.zhysunny.io.properties;

import com.zhysunny.io.properties.constant.Constant;
import org.junit.*;

import java.util.Properties;

/**
 * PropertiesWriter Test.
 * @author 章云
 * @date 2019/7/27 14:21
 */
public class PropertiesWriterTest {

    private String input;
    private String output;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test PropertiesWriter Class Start...");
    }

    @Before
    public void before() throws Exception {
        this.input = "src/test/resources/properties/input.properties";
        this.output = "src/test/resources/properties/output.properties";
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test PropertiesWriter Class End...");
    }

    /**
     * Method: write(Properties prop, String comment)
     */
    @Test
    public void testWriteForPropComment() throws Exception {
        PropertiesReader reader = new PropertiesReader(input).builder();
        Properties prop = reader.getProps();
        System.out.println(prop);
        PropertiesWriter writer = new PropertiesWriter(output);
        writer.write(prop, "prop test");
    }

    /**
     * Method: write(PropertiesConstant constant, String comment)
     */
    @Test
    public void testWriteForConstantComment() throws Exception {
        PropertiesReader reader = new PropertiesReader(input).builder();
        reader.toConstant(Constant.class);
        PropertiesWriter writer = new PropertiesWriter(output);
        writer.write(Constant.class, "Constant test");
    }

}
