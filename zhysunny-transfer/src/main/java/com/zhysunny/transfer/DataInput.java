package com.zhysunny.transfer;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.transfer.util.TaskConstants;
import com.zhysunny.transfer.util.ThreadPoolUtil;
import com.zhysunny.transfer.util.Transfer;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据输入接口
 * @author 章云
 * @date 2019/8/15 21:05
 */
public interface DataInput {

    /**
     * 数据输入
     * @throws Exception
     */
    void input() throws Exception;

    /**
     * 批量处理
     * @param datas
     * @param list
     * @param instance
     * @param output
     * @return
     */
    default List<JSONObject> transfer(List<JSONObject> datas, List<JSONObject> list, ThreadPoolUtil instance, DataOutput output) {
        for (JSONObject json : list) {
            datas.add(json);
            if (datas.size() == TaskConstants.TRANSFER_BATCH) {
                instance.addThread(new Transfer(output, datas));
                datas = new ArrayList<JSONObject>(TaskConstants.TRANSFER_BATCH);
            }
        }
        return datas;
    }

}
