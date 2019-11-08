package com.zhysunny.transfer;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据批处理
 * @author 章云
 * @date 2019/11/7 16:05
 */
public class DataBatch {

    /**
     * 批处理集合
     */
    private List<JSONObject> batchDatas;

    /**
     * 实际数据
     */
    private List<JSONObject> datas;

    /**
     * 不足批量的数据
     */
    private List<JSONObject> previous;

    /**
     * 批处理数
     */
    private int batch;

    /**
     * 指针
     */
    private int index;

    public DataBatch(List<JSONObject> datas, int batch, List<JSONObject> previous) {
        this.index = 0;
        this.datas = datas;
        this.batch = batch;
        this.previous = previous;
    }

    public DataBatch(List<JSONObject> datas, int batch) {
        this(datas, batch, null);
    }

    /**
     * 将datas数据放入batchDatas
     * 当index小于datas.size()，返回true，可以继续执行batch方法
     * 当index等于datas.size()，返回false
     * 当datas数据不足batch，返回false
     * @return
     */
    public boolean batch() {
        batchDatas = new ArrayList<>(batch);
        if (previous != null && previous.size() > 0) {
            batchDatas.addAll(previous);
            previous = null;
        }
        int count = batch - batchDatas.size();
        if (datas.size() - index >= count) {
            batchDatas.addAll(datas.subList(index, index + count));
            index += count;
            if (index == datas.size()) {
                return false;
            } else {
                return true;
            }
        } else {
            batchDatas.addAll(datas.subList(index, datas.size()));
            return false;
        }
    }

    public List<JSONObject> getBatchDatas() {
        return batchDatas;
    }

}
