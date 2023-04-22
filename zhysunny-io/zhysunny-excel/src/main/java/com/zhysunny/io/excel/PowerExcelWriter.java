package com.zhysunny.io.excel;

import com.alibaba.excel.write.handler.WriteHandler;
import com.zhysunny.io.excel.writer.ISheetWriter;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author zhysunny
 * @date 2023/3/12 10:22
 */
public class PowerExcelWriter {
    public static void write(List<? extends ISheetWriter> sheetWriters) {
        write(sheetWriters, null);
    }

    public static void write(List<? extends ISheetWriter> sheetWriters, WriteHandler writeHandler) {
        sheetWriters.stream().collect(groupingBy(ISheetWriter::getExcelName))
                .forEach((key, value) -> write(key, value, writeHandler));
    }

    public static void write(ISheetWriter sheetWriter) {
        write(sheetWriter, null);
    }

    public static void write(ISheetWriter sheetWriter, WriteHandler writeHandler) {
        List<ISheetWriter> sheetWriters = new ArrayList<>();
        sheetWriters.add(sheetWriter);
        write(sheetWriters, writeHandler);
    }

    private static void write(String excelName, List<? extends ISheetWriter> sheetWriters, WriteHandler writeHandler) {
        EasyExcelWriter writer = new EasyExcelWriter(excelName, writeHandler);

        for (ISheetWriter sheetWriter : sheetWriters) {
            writer.writerSheet(sheetWriter.getSheetNo(), sheetWriter.getSheetName()).writerTable(sheetWriter.getClazz())
                    .writer(sheetWriter.getDataList());
        }

        writer.finish();
    }
}
