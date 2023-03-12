package com.zhysunny.io.excel.reader;

import com.zhysunny.io.excel.data.ModelSheet1;
import com.zhysunny.io.excel.listener.AbstractReadListener;

/**
 * @author zhysunny
 * @date 2023/3/12 10:55
 */
public class Sheet1Reader extends AbstractReadListener<ModelSheet1> {

    @Override
    public Class<ModelSheet1> getClazz() {
        return ModelSheet1.class;
    }

    @Override
    public Object getSheetNoOrName() {
        return "sheet1";
    }
}
