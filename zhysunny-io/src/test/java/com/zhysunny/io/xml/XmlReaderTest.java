package com.zhysunny.io.xml;

import com.zhysunny.io.xml.bean.Topics;
import org.junit.*;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * XmlReader Test.
 * @author 章云
 * @date 2019/7/24 16:29
 */
public class XmlReaderTest {

    private String resource;
    private String path;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test XmlReader Class Start...");
    }

    @Before
    public void before() throws Exception {
        this.resource = "xml/input.xml";
        this.path = "src/test/resources/" + resource;
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test XmlReader Class End...");
    }

    @Test
    public void testConstructor() {
        try {
            XmlReader reader = null;
            reader = new XmlReader(path).builder();
            assertNotNull(reader.getDocument());
            File file = new File(path);
            reader = new XmlReader(file).builder();
            assertNotNull(reader.getDocument());
            URL url = XmlReaderTest.class.getClassLoader().getResource(resource);
            reader = new XmlReader(url).builder();
            assertNotNull(reader.getDocument());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: read(Class<?> clz)
     */
    @Test
    public void testReadForBean() throws Exception {
        Topics topics = new XmlReader(path).read(Topics.class);
        assertTrue(topics.getTopics().size() > 0);
        System.out.println(topics);
    }

    /**
     * Method: read(Class<?> clz)
     */
    @Test
    public void testReadForMap() throws Exception {
        Map<String, Object> rootMap = new XmlReader(path).read();
        System.out.println(rootMap.keySet());
        System.out.println(rootMap.get(XmlReader.ELEMENT));
        System.out.println(rootMap.get(XmlReader.ATTRIBUTES));
        System.out.println(rootMap.get("name"));
        assertEquals("Topics", rootMap.get(XmlReader.ELEMENT));
        List<Map<String, Object>> topicList = (List<Map<String, Object>>) rootMap.get(rootMap.get(XmlReader.ELEMENT));
        assertTrue(topicList.size() > 0);
        for (Map<String, Object> topic : topicList) {
            System.out.println("--------------------------------------");
            System.out.println(topic.keySet());
            System.out.println(topic.get(XmlReader.ELEMENT));
            System.out.println(topic.get(XmlReader.ATTRIBUTES));
            System.out.println(topic.get(topic.get(XmlReader.ELEMENT)));
        }
    }

}
