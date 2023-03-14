package com.zhysunny.io.excel.writer;

import com.zhysunny.io.excel.data.ModelData;

/**
 * @author zhysunny
 * @date 2023/3/12 10:54
 */
public abstract class AbstractSheetWriter<T> implements ISheetWriter<T> {
    @Override
    public String getExcelName() {
        return ModelData.EXCEL_NAME;
    }
}
