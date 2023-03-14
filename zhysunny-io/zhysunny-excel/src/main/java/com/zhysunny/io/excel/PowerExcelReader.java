package com.zhysunny.io.excel;

import com.zhysunny.io.excel.listener.ISheetReader;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author zhysunny
 * @date 2023/3/12 10:22
 */
public class PowerExcelReader {

    public static void read(List<? extends ISheetReader> sheetReaders) {
        sheetReaders.stream().collect(groupingBy(ISheetReader::getExcelName)).forEach((key, value) -> read(key, value));
    }

    private static void read(String excelName, List<? extends ISheetReader> sheetReaders) {
        EasyExcelReader reader = new EasyExcelReader(excelName);

        for (ISheetReader sheetReader : sheetReaders) {
            Object sheetNoOrName = sheetReader.getSheetNoOrName();
            if (sheetNoOrName instanceof String) {
                reader.readSheet(sheetReader.getClazz(), sheetReader, (String) sheetNoOrName);
            } else if (sheetNoOrName instanceof Integer) {
                reader.readSheet(sheetReader.getClazz(), sheetReader, (int) sheetNoOrName);
            } else {
                throw new RuntimeException("[sheetNoOrName] must be String or Integer");
            }
        }
    }

    public static void read(ISheetReader sheetReader) {
        List<ISheetReader> sheetReaders = new ArrayList<>();
        sheetReaders.add(sheetReader);
        read(sheetReaders);
    }

}
