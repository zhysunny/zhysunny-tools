package com.zhysunny.transfer.component.text;

import static org.junit.Assert.*;
import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.conf.Configuration;
import com.zhysunny.io.text.TextReader;
import com.zhysunny.io.xml.XmlReader;
import com.zhysunny.transfer.component.xml.XmlToJson;
import com.zhysunny.transfer.constant.Constants;
import com.zhysunny.transfer.mapping.Mapping;
import org.junit.*;
import java.io.File;
import java.util.List;

/**
 * TextToJson Test.
 * @author 章云
 * @date 2019/11/11 11:03
 */
public class TextToJsonTest {

    private Mapping mapping;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test TextToJson Class Start...");
        Configuration conf = Configuration.getInstance();
        conf.addDefaultResource("default/transfer-default.xml");
        conf.toConstant(Constants.class);
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test TextToJson Class End...");
    }

    /**
     * Method: read(String[] data, Object... params)
     */
    @Test
    public void testRead1() throws Exception {
        Constants.TEXT_HAS_HEADS_FROM = true;
        mapping = new XmlReader("example/text/1/text_to_text.xml").read(Mapping.class);
        TextReader reader = new TextReader("example/text/1/input.txt").setHasHead(Constants.TEXT_HAS_HEADS_FROM)
        .setSplit(Constants.TEXT_LINE_SPLIT_FROM).setBatch(Constants.TRANSFER_BATCH).builder();
        List<JSONObject> datas = reader.read(new TextToJson(mapping), (Object[])reader.getHeads());
        assertEquals(datas.size(), 5000);
        JSONObject json = datas.get(0);
        assertEquals(json.getString("NAME"), "A664JgtQRG");
        assertEquals(json.getIntValue("AGE"), 33);
        assertEquals(json.getIntValue("SEX"), 0);
    }

    @Test
    public void testRead2() throws Exception {
        Constants.TEXT_HAS_HEADS_FROM = false;
        mapping = new XmlReader("example/text/2/text_to_text.xml").read(Mapping.class);
        TextReader reader = new TextReader("example/text/2/input.txt").setHasHead(Constants.TEXT_HAS_HEADS_FROM)
        .setSplit(Constants.TEXT_LINE_SPLIT_FROM).setBatch(Constants.TRANSFER_BATCH).builder();
        List<JSONObject> datas = reader.read(new TextToJson(mapping), (Object[])reader.getHeads());
        assertEquals(datas.size(), 5000);
        JSONObject json = datas.get(0);
        assertEquals(json.getString("NAME"), "A664JgtQRG");
        assertEquals(json.getIntValue("AGE"), 33);
        assertEquals(json.getIntValue("SEX"), 0);
    }

} 
