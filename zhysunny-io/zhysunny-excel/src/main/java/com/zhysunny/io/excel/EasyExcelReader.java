package com.zhysunny.io.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.listener.ReadListener;

/**
 * @author zhysunny
 * @date 2023/3/12 9:46
 */
public class EasyExcelReader<T> {
    private final String excelName;

    private final ReadListener readListener;

    public EasyExcelReader(String excelName, ReadListener readListener) {
        this.excelName = excelName;
        this.readListener = readListener;
    }

    public void readSheet(Class<T> clazz) {
        readSheet(clazz, 0);
    }

    public void readSheet(Class<T> clazz, int sheetNo) {
        EasyExcel.read(this.excelName, clazz, this.readListener).extraRead(CellExtraTypeEnum.HYPERLINK)
                .extraRead(CellExtraTypeEnum.COMMENT).sheet(sheetNo).doRead();
    }

    public void readSheet(Class<T> clazz, String sheetName) {
        EasyExcel.read(this.excelName, clazz, this.readListener).extraRead(CellExtraTypeEnum.HYPERLINK)
                .extraRead(CellExtraTypeEnum.COMMENT).sheet(sheetName).doRead();
    }

}
