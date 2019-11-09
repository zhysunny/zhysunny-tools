package com.zhysunny.transfer.component.xml;

import static org.junit.Assert.*;
import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.xml.XmlReader;
import com.zhysunny.transfer.constant.Constants;
import com.zhysunny.transfer.mapping.Mapping;
import org.junit.*;
import java.util.List;

/**
 * XmlToJson Test.
 * @author 章云
 * @date 2019/11/8 10:21
 */
public class XmlToJsonTest {

    private Mapping mapping;
    private ClassLoader classLoader = XmlToJson.class.getClassLoader();

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test XmlToJson Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test XmlToJson Class End...");
    }

    /**
     * Method: read(XmlReader reader, Object... params)
     */
    @Test
    public void testRead1() throws Exception {
        mapping = new XmlReader("example/xml/1/xml_to_xml.xml").read(Mapping.class);
        List<JSONObject> datas = (List<JSONObject>)new XmlReader(classLoader.getResource("example/xml/1/input.xml"))
        .read(new XmlToJson(mapping));
        System.out.println(datas);
        assertEquals(datas.size(), 1);
        JSONObject json = datas.get(0);
        assertEquals(json.getString("name"), "name");
        assertEquals(json.getString("topic"), "this is topics");
    }

    /**
     * Method: read(XmlReader reader, Object... params)
     */
    @Test
    public void testRead2() throws Exception {
        Constants.XML_DATA_NODE_FROM = "property";
        mapping = new XmlReader("example/xml/2/xml_to_xml.xml").read(Mapping.class);
        List<JSONObject> datas = (List<JSONObject>)new XmlReader("example/xml/2/input.xml").read(new XmlToJson(mapping));
        System.out.println(datas);
        assertEquals(datas.size(), 3);
        JSONObject json = datas.get(0);
        assertEquals(json.getString("key"), "property1");
        assertEquals(json.getString("name"), "name1");
        assertEquals(json.getString("value"), "value1");
    }

    /**
     * Method: read(XmlReader reader, Object... params)
     */
    @Test
    public void testRead3() throws Exception {
        Constants.XML_DATA_NODE_FROM = "Topic";
        mapping = new XmlReader(classLoader.getResource("example/xml/3/xml_to_xml.xml")).read(Mapping.class);
        List<JSONObject> datas = (List<JSONObject>)new XmlReader(classLoader.getResource("example/xml/3/input.xml"))
        .read(new XmlToJson(mapping));
        System.out.println(datas);
        assertEquals(datas.size(), 3);
        JSONObject json = datas.get(0);
        assertEquals(json.getString("topicName"), "COMMON");
        assertEquals(json.getString("filter.Column.Key"), "content1");
        assertEquals(json.getString("filter.Column.Value"), "\\u62A5\\u6848");
    }

}
