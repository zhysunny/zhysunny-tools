package com.zhysunny.io.excel.writer;

import com.zhysunny.io.excel.data.ModelData;
import com.zhysunny.io.excel.data.ModelSheet1;
import java.util.List;

/**
 * @author zhysunny
 * @date 2023/3/12 10:55
 */
public class Sheet1Writer extends AbstractSheetWriter<ModelSheet1> {
    @Override
    public List<ModelSheet1> getDataList() {
        return ModelData.getSheet1();
    }

    @Override
    public Class<ModelSheet1> getClazz() {
        return ModelSheet1.class;
    }

    @Override
    public String getSheetName() {
        return "sheet1";
    }
}
