package com.zhysunny.io.text;

import static org.junit.Assert.*;

import com.zhysunny.io.excel.ExcelReaderTest;
import com.zhysunny.io.text.bean.Person;
import org.junit.*;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * TextReader Test.
 * @author 章云
 * @date 2019/7/26 11:18
 */
public class TextReaderTest {

    private String resource;
    private String path;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test TextReader Class Start...");
    }

    @Before
    public void before() throws Exception {
        this.resource = "text/input.txt";
        this.path = "src/test/resources/" + resource;
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test TextReader Class End...");
    }

    /**
     * Method: readBatch(Class<E> clz)
     */
    @Test
    public void testReadArray() throws Exception {
        TextReader reader = new TextReader(path).setBatch(999).setHasHead(true).builder();
        List<String[]> list = reader.readToArray();
        System.out.println(Arrays.toString(list.get(0)));
        while (true) {
            list = reader.readToArray();
            if (list.size() == 0) {
                break;
            }
            System.out.println(list.size());
        }
        reader.close();
    }

    /**
     * Method: readBatch(Class<E> clz)
     */
    @Test
    public void testReadMap() throws Exception {
        URL url = TextReaderTest.class.getClassLoader().getResource(resource);
        TextReader reader = new TextReader(url).setBatch(1000).setHasHead(true).builder();
        List<Map<String, String>> list = reader.readToMap();
        System.out.println(list.get(0));
        while (true) {
            list = reader.readToMap();
            if (list.size() == 0) {
                break;
            }
            System.out.println(list.size());
        }
        reader.close();
    }

    /**
     * Method: readBatch(Class<E> clz)
     */
    @Test
    public void testReadBean() throws Exception {
        TextReader reader = new TextReader(path).setBatch(999).setHasHead(true).builder();
        List<Person> list = reader.readToBean(Person.class);
        System.out.println(list.get(0));
        while (true) {
            list = reader.readToBean(Person.class);
            if (list.size() == 0) {
                break;
            }
            System.out.println(list.size());
        }
        reader.close();
    }

}
