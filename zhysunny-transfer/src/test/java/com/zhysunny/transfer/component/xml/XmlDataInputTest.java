package com.zhysunny.transfer.component.xml;

import static org.junit.Assert.*;

import com.zhysunny.io.xml.XmlReader;
import com.zhysunny.transfer.DataInput;
import com.zhysunny.transfer.DataTransferFactory;
import com.zhysunny.transfer.mapping.Mapping;
import com.zhysunny.transfer.util.TaskConstants;
import com.zhysunny.transfer.util.ThreadPoolUtil;
import org.junit.*;

/**
 * XmlDataInput Test.
 * @author 章云
 * @date 2019/8/23 9:52
 */
public class XmlDataInputTest {

    private String resource;
    private String path;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test XmlDataInput Class Start...");
    }

    @Before
    public void before() throws Exception {
        this.resource = "xml/input2.xml";
        this.path = "src/test/resources/" + resource;
        TaskConstants.TRANSFER_BATCH = 2;
        TaskConstants.TRANSFER_PARALLEL = 2;
        ThreadPoolUtil.getInstance(TaskConstants.TRANSFER_PARALLEL);
    }

    @After
    public void after() throws Exception {
        ThreadPoolUtil.getInstance().join();
        ThreadPoolUtil.getInstance().shutdown();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test XmlDataInput Class End...");
    }

    /**
     * Method: input()
     */
    @Test
    public void testInput1() throws Exception {
        Mapping mapping = null;
        try {
            mapping = new XmlReader("src/test/resources/xml/1/xml_to_xml.xml").read(Mapping.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataInput dataInput = DataTransferFactory.getDataInput(mapping);
        dataInput.input();
        ThreadPoolUtil.getInstance().join();
        ThreadPoolUtil.getInstance().shutdown();
    }

    /**
     * Method: input()
     */
    @Test
    public void testInput2() throws Exception {
        Mapping mapping = null;
        try {
            mapping = new XmlReader("src/test/resources/xml/2/xml_to_xml.xml").read(Mapping.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TaskConstants.TRANSFER_BATCH = 2;
        TaskConstants.TRANSFER_PARALLEL = 2;
        DataInput dataInput = DataTransferFactory.getDataInput(mapping);
        dataInput.input();
    }

    /**
     * Method: input()
     */
    @Test
    public void testInput3() throws Exception {
        Mapping mapping = null;
        try {
            mapping = new XmlReader("src/test/resources/xml/3/xml_to_xml.xml").read(Mapping.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataInput dataInput = DataTransferFactory.getDataInput(mapping);
        dataInput.input();
    }

}
