package com.zhysunny.io.text;

import com.zhysunny.io.text.bean.Person;
import org.junit.*;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * TextWriter Test
 * @author 章云
 * @date 2019/7/26 22:39
 */
public class TextWriterTest {

    private String input;
    private String output;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test TextWriter Class Start...");
    }

    @Before
    public void before() throws Exception {
        this.input = "src/test/resources/text/input.txt";
        this.output = "src/test/resources/text/output.txt";
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test TextWriter Class End...");
    }

    /**
     * Method: write(List<T> data)
     */
    @Test
    public void testWriteArray() throws Exception {
        TextReader reader = new TextReader(input).setBatch(1000).setHasHead(true).builder();
        String[] heads = reader.getHeads();
        TextWriter writer = new TextWriter(output).setHasHead(true).setHeads(heads);
        List<String[]> list = null;
        while (true) {
            list = reader.readToArray();
            if (list.size() == 0) {
                break;
            }
            writer.writeOfArray(list);
        }
        writer.close();
        reader.close();
    }

    /**
     * Method: write(List<T> data)
     */
    @Test
    public void testWriteMap() throws Exception {
        TextReader reader = new TextReader(input).setBatch(1000).setHasHead(true).builder();
        TextWriter writer = new TextWriter(output);
        List<Map<String, String>> list = null;
        while (true) {
            list = reader.readToMap();
            if (list.size() == 0) {
                break;
            }
            writer.writeOfMap(list);
        }
        writer.close();
        reader.close();
    }

    /**
     * Method: readBatch(Class<E> clz)
     */
    @Test
    public void testWriteBean() throws Exception {
        TextReader reader = new TextReader(input).setBatch(999).setHasHead(true).builder();
        TextWriter writer = new TextWriter(output).setHasHead(true);
        List<Person> list = null;
        while (true) {
            list = reader.readToBean(Person.class);
            if (list.size() == 0) {
                break;
            }
            writer.writeOfBean(list);
        }
        writer.close();
        reader.close();
    }

}
