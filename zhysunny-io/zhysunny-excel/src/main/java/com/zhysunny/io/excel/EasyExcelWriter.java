package com.zhysunny.io.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.zhysunny.io.excel.writer.DefaultWriterHandler;
import java.util.List;

/**
 * @author zhysunny
 * @date 2023/3/12 10:05
 */
public class EasyExcelWriter<T> {
    private final ExcelWriter excelWriter;

    private WriteSheet writeSheet;

    private WriteTable writeTable;

    private static final DefaultWriterHandler DEFAULT_WRITER_HANDLER = new DefaultWriterHandler();

    public EasyExcelWriter(String excelName) {
        this(excelName, null);
    }

    public EasyExcelWriter(String excelName, WriteHandler writeHandler) {
        if (writeHandler == null) {
            writeHandler = DEFAULT_WRITER_HANDLER;
        }
        this.excelWriter = EasyExcel.write(excelName).registerWriteHandler(writeHandler).build();
    }

    public EasyExcelWriter writerSheet(Integer sheetNo, String sheetName) {
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
