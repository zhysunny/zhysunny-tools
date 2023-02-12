package com.zhysunny.io.xml;

import static org.junit.Assert.*;

import com.zhysunny.io.xml.bean.Topics;
import org.junit.*;

import java.io.Writer;
import java.util.Map;

/**
 * XmlWriter Test.
 * @author 章云
 * @date 2019/7/25 11:30
 */
public class XmlWriterTest {

    private String input;
    private String output;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test XmlWriter Class Start...");
    }

    @Before
    public void before() throws Exception {
        this.input = "src/test/resources/xml/input.xml";
        this.output = "src/test/resources/xml/output.xml";
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test XmlWriter Class End...");
    }

    /**
     * Method: write(Object pojo, Writer out)
     */
    @Test
    public void testWrite() throws Exception {
        Topics topics = new XmlReader(input).builder().read(Topics.class);
        assertTrue(topics.getTopics().size() > 0);
        System.out.println(topics);
        new XmlWriter(output).write(topics);
    }

    @Test
    public void testWriteForMap() throws Exception {
        Map<String, Object> rootMap = new XmlReader(input).builder().read();
        System.out.println(rootMap);
        new XmlWriter(output).write(rootMap);
    }

} 
