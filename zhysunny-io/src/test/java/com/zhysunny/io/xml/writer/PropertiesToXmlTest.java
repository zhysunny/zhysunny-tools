package com.zhysunny.io.xml.writer;

import static org.junit.Assert.*;
import com.zhysunny.io.xml.XmlReader;
import com.zhysunny.io.xml.XmlWriter;
import com.zhysunny.io.xml.reader.XmlToProperties;
import org.junit.*;
import java.net.URL;
import java.util.Properties;

/**
 * PropertiesToXml Test
 * @author 章云
 * @date 2019/12/25 19:59
 */
public class PropertiesToXmlTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test PropertiesToXml Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test PropertiesToXml Class End...");
    }

    /**
     * Method: write(Writer out, Object... params)
     */
    @Test
    public void testWrite() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("xml/config.xml");
        Properties properties = (Properties)new XmlReader(url).read(new XmlToProperties());
        System.out.println(properties);
        new XmlWriter("src/test/resources/xml/config_output.xml").write(new PropertiesToXml(), properties);
    }

}
