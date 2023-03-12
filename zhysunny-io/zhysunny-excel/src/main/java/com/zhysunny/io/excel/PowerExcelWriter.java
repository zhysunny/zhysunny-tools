package com.zhysunny.io.excel;

import com.zhysunny.io.excel.writer.ISheetWriter;
import java.util.List;

/**
 * @author zhysunny
 * @date 2023/3/12 10:22
 */
public class PowerExcelWriter {
    public static void writer(String excelName, List<? extends ISheetWriter> sheetWriters) {
        EasyExcelWriter writer = new EasyExcelWriter(excelName);

        for (ISheetWriter sheetWriter : sheetWriters) {
            writer.writerSheet(sheetWriter.getSheetNo(), sheetWriter.getSheetName()).writerTable(sheetWriter.getClazz())
                    .writer(sheetWriter.getDataList());
        }

        writer.finish();
    }
}
