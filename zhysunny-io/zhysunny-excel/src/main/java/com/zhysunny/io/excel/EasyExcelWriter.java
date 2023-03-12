package com.zhysunny.io.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import java.util.List;

/**
 * @author zhysunny
 * @date 2023/3/12 10:05
 */
public class EasyExcelWriter<T> {
    private final ExcelWriter excelWriter;

    private WriteSheet writeSheet;

    private WriteTable writeTable;

    public EasyExcelWriter(String excelName) {
        this.excelWriter = EasyExcel.write(excelName).build();
    }

    public EasyExcelWriter writerSheet(int sheetNo, String sheetName) {
        this.writeSheet = EasyExcel.writerSheet(sheetNo, sheetName).needHead(false).build();
        return this;
    }

    public EasyExcelWriter writerTable(Class<T> clazz) {
        this.writeTable = EasyExcel.writerTable(0).needHead(true).head(clazz).build();
        return this;
    }

    public void writer(List<T> dataList) {
        excelWriter.write(dataList, writeSheet, writeTable);
    }

    public void finish() {
        excelWriter.finish();
    }

}
