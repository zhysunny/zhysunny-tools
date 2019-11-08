package com.zhysunny.transfer;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.transfer.constant.Constants;
import com.zhysunny.transfer.util.ThreadPoolUtil;
import com.zhysunny.transfer.thread.TransferThread;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据输入接口
 * @author 章云
 * @date 2019/8/15 21:05
 */
public interface DataInput {

    /**
     * 文件的话读取整个文件并返回
     * 数据库返回一次批量的数据
     * @return
     * @throws Exception
     */
    List<JSONObject> input() throws Exception;

    /**
     * 批量处理
     * @param datas
     * @param list
     * @param instance
     * @param output
     * @return
     */
    @Deprecated
    default List<JSONObject> transfer(List<JSONObject> datas, List<JSONObject> list, ThreadPoolUtil instance, DataOutput output) {
        for (JSONObject json : list) {
            datas.add(json);
            if (datas.size() == Constants.TRANSFER_BATCH) {
                instance.addThread(new TransferThread(output, datas));
                datas = new ArrayList<JSONObject>(Constants.TRANSFER_BATCH);
            }
        }
        return datas;
    }

}
