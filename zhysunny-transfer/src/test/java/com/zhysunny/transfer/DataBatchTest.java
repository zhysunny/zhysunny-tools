package com.zhysunny.transfer;

import static org.junit.Assert.*;
import com.alibaba.fastjson.JSONObject;
import org.junit.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DataBatch Test.
 * @author 章云
 * @date 2019/11/7 16:15
 */
public class DataBatchTest {

    private DataBatch dataBatch;
    private List<JSONObject> datas;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test DataBatch Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test DataBatch Class End...");
    }

    /**
     * Method: batch(List<JSONObject> datas)
     */
    @Test
    public void testBatch() throws Exception {
        // 模拟13条数据
        datas = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            datas.add(new JSONObject());
        }
        // 批处理数设置为10
        dataBatch = new DataBatch(datas, 10);
        // 开始获取数据
        // 第一次调用应该获取10条
        boolean batch = dataBatch.batch();
        assertTrue(batch);
        assertEquals(dataBatch.getBatchDatas().size(), 10);
        // 第二次调用应该获取3条
        batch = dataBatch.batch();
        assertFalse(batch);
        assertEquals(dataBatch.getBatchDatas().size(), 3);
    }

    /**
     * Method: batch(List<JSONObject> datas)
     */
    @Test
    public void testBatch2() throws Exception {
        // 模拟20条数据
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(new JSONObject());
        }
        // 批处理数设置为10
        dataBatch = new DataBatch(datas, 10);
        // 开始获取数据
        // 第一次调用应该获取10条
        boolean batch = dataBatch.batch();
        assertTrue(batch);
        assertEquals(dataBatch.getBatchDatas().size(), 10);
        // 第二次调用应该获取10条
        batch = dataBatch.batch();
        assertFalse(batch);
        assertEquals(dataBatch.getBatchDatas().size(), 10);
    }

    /**
     * Method: batch(List<JSONObject> datas)
     */
    @Test
    public void testBatch3() throws Exception {
        // 模拟8条数据
        datas = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            datas.add(new JSONObject());
        }
        dataBatch = new DataBatch(datas, 10);
        // 第一次调用应该获取8条
        boolean batch = dataBatch.batch();
        assertFalse(batch);
        assertEquals(dataBatch.getBatchDatas().size(), 8);
        // 再模拟8条数据
        List<JSONObject> datas2 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            datas2.add(new JSONObject());
        }
        dataBatch = new DataBatch(datas2, 10, dataBatch.getBatchDatas());
        // 第一次调用应该获取10条
        batch = dataBatch.batch();
        assertTrue(batch);
        assertEquals(dataBatch.getBatchDatas().size(), 10);
        // 第二次调用应该获取10条
        batch = dataBatch.batch();
        assertFalse(batch);
        assertEquals(dataBatch.getBatchDatas().size(), 6);
    }

} 
