package com.zhysunny.io.excel.reader;

import com.zhysunny.io.excel.data.ModelData;
import com.zhysunny.io.excel.data.ModelSheet2;
import com.zhysunny.io.excel.listener.AbstractReadListener;

/**
 * @author zhysunny
 * @date 2023/3/12 10:55
 */
public class Sheet2Reader extends AbstractReadListener<ModelSheet2> {

    @Override
    public Class<ModelSheet2> getClazz() {
        return ModelSheet2.class;
    }

    @Override
    public Object getSheetNoOrName() {
        return 1;
    }

    @Override
    public String getExcelName() {
        return ModelData.EXCEL_NAME;
    }
}
