package com.zhysunny.io.xml.reader;

import static org.junit.Assert.*;
import com.zhysunny.io.xml.XmlReader;
import org.junit.*;
import java.net.URL;
import java.util.Properties;

/**
 * XmlToProperties Test.
 * @author 章云
 * @date 2019/11/8 14:43
 */
public class XmlToPropertiesTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test XmlToProperties Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test XmlToProperties Class End...");
    }

    /**
     * Method: read(XmlReader reader, Object... params)
     */
    @Test
    public void testRead() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("xml/config.xml");
        Properties properties = (Properties)new XmlReader(url).read(new XmlToProperties());
        assertEquals(properties.size(), 2);
        assertEquals(properties.getProperty("name1"), "value1");
        assertEquals(properties.getProperty("name2"), "value2");
        System.out.println(properties);
    }

} 
