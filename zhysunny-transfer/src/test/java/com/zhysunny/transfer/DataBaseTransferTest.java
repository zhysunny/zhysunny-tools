package com.zhysunny.transfer;

import static org.junit.Assert.*;

import org.junit.*;

import java.io.File;

/**
 * DataBaseTransfer Test.
 * @author 章云
 * @date 2019/8/23 11:21
 */
public class DataBaseTransferTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test DataBaseTransfer Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test DataBaseTransfer Class End...");
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMainXml1() throws Exception {
        System.out.println(new File("").getAbsolutePath());
        String[] args = new String[]{"src/test/resources/xml/1/xml_to_xml.properties"};
        DataBaseTransfer.main(args);
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMainXml2() throws Exception {
        System.out.println(new File("").getAbsolutePath());
        String[] args = new String[]{"src/test/resources/xml/2/xml_to_xml.properties"};
        DataBaseTransfer.main(args);
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMainXml3() throws Exception {
        System.out.println(new File("").getAbsolutePath());
        String[] args = new String[]{"src/test/resources/xml/3/xml_to_xml.properties"};
        DataBaseTransfer.main(args);
    }

}
