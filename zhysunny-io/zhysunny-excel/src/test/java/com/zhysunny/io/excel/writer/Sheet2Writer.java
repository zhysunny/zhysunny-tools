package com.zhysunny.io.excel.writer;

import com.zhysunny.io.excel.data.ModelData;
import com.zhysunny.io.excel.data.ModelSheet2;
import java.util.List;

/**
 * @author zhysunny
 * @date 2023/3/12 10:55
 */
public class Sheet2Writer extends AbstractSheetWriter<ModelSheet2> {
    @Override
    public List<ModelSheet2> getDataList() {
        return ModelData.getSheet2();
    }

    @Override
    public Class<ModelSheet2> getClazz() {
        return ModelSheet2.class;
    }

    @Override
    public String getSheetName() {
        return "sheet2";
    }
}
