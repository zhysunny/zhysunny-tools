package com.zhysunny.io.excel;

import com.zhysunny.io.excel.data.ModelData;
import com.zhysunny.io.excel.data.ModelSheet1;
import com.zhysunny.io.excel.data.ModelSheet2;
import com.zhysunny.io.excel.listener.AbstractReadListener;
import com.zhysunny.io.excel.reader.Sheet1Reader;
import com.zhysunny.io.excel.reader.Sheet2Reader;
import com.zhysunny.io.excel.writer.AbstractSheetWriter;
import com.zhysunny.io.excel.writer.Sheet1Writer;
import com.zhysunny.io.excel.writer.Sheet2Writer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * PowerExcelReader Test
 */
public class PowerExcel {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test PowerExcel Class Start...");
    }

    @Before
    public void before() throws Exception {
        System.out.println("Write Excel Start...");
        List<AbstractSheetWriter> sheetWriters = new ArrayList<>();
        sheetWriters.add(new Sheet1Writer());
        sheetWriters.add(new Sheet2Writer());

        PowerExcelWriter.writer(ModelData.EXCEL_NAME, sheetWriters);
        Thread.sleep(3000);
    }

    @After
    public void after() throws Exception {
        new File(ModelData.EXCEL_NAME).deleteOnExit();
        System.out.println("Delete File...");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test PowerExcel Class End...");
    }

    /**
     * Method: reader(String excelName, List<ISheetReader> sheetReaders)
     */
    @Test
    public void testReader() throws Exception {
        System.out.println("Read Excel Start...");
        List<AbstractReadListener> readListeners = new ArrayList<>();
        Sheet1Reader sheet1Reader = new Sheet1Reader();
        readListeners.add(sheet1Reader);
        Sheet2Reader sheet2Reader = new Sheet2Reader();
        readListeners.add(sheet2Reader);

        PowerExcelReader.reader(ModelData.EXCEL_NAME, readListeners);

        List<ModelSheet1> data1List = sheet1Reader.getDataList();
        assertThat(ModelData.equals(ModelData.getSheet1(), data1List)).isTrue();
        System.out.println(data1List);

        List<ModelSheet2> data2List = sheet2Reader.getDataList();
        assertThat(ModelData.equals(ModelData.getSheet2(), data2List)).isTrue();
        System.out.println(data2List);
        Thread.sleep(3000);
    }

} 
