package com.zhysunny.io.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhysunny
 * @date 2023/3/12 9:52
 */
@Slf4j
public abstract class AbstractReadListener<T> implements ISheetReader<T> {
    private List<T> dataList = new ArrayList<>();

    @Override
    public void onException(Exception e, AnalysisContext analysisContext) throws Exception {
        log.error("read excel error", e);
    }

    @Override
    public void invokeHead(Map<Integer, CellData> map, AnalysisContext analysisContext) {
        dataList.clear();
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        dataList.add(t);
    }

    @Override
    public void extra(CellExtra cellExtra, AnalysisContext analysisContext) {
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    @Override
    public boolean hasNext(AnalysisContext analysisContext) {
        return true;
    }

    @Override
    public List<T> getDataList() {
        return dataList;
    }

}
