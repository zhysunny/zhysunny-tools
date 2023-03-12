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

    public EasyExcelReader(String excelName) {
        this.excelName = excelName;
    }

    public void readSheet(Class<T> clazz, ReadListener readListener) {
        readSheet(clazz, readListener, 0);
    }

    public void readSheet(Class<T> clazz, ReadListener readListener, int sheetNo) {
        EasyExcel.read(this.excelName, clazz, readListener).extraRead(CellExtraTypeEnum.HYPERLINK)
                .extraRead(CellExtraTypeEnum.COMMENT).sheet(sheetNo).doRead();
    }

    public void readSheet(Class<T> clazz, ReadListener readListener, String sheetName) {
        EasyExcel.read(this.excelName, clazz, readListener).extraRead(CellExtraTypeEnum.HYPERLINK)
                .extraRead(CellExtraTypeEnum.COMMENT).sheet(sheetName).doRead();
    }

}
