package com.zhysunny.transfer.component.text;

import static org.junit.Assert.*;

import com.zhysunny.io.xml.XmlReader;
import com.zhysunny.transfer.DataInput;
import com.zhysunny.transfer.DataTransferFactory;
import com.zhysunny.transfer.mapping.Mapping;
import org.junit.*;

/**
 * TextDataInput Test.
 * @author 章云
 * @date 2019/8/27 16:07
 */
public class TextDataInputTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test TextDataInput Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test TextDataInput Class End...");
    }

    /**
     * Method: input()
     */
    @Test
    public void testInput1() throws Exception {
        Mapping mapping = null;
        try {
            mapping = new XmlReader("src/test/resources/text/1/text_to_text.xml").read(Mapping.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataInput dataInput = DataTransferFactory.getDataInput(mapping);
        dataInput.input();
    }


} 
