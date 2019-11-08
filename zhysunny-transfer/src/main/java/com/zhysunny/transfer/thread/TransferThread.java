package com.zhysunny.transfer.thread;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.transfer.BaseDataTransform;
import com.zhysunny.transfer.DataOutput;
import com.zhysunny.transfer.util.TaskConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 数据传输线程
 * @author 章云
 * @date 2019/8/24 10:13
 */
public class TransferThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferThread.class);

    private DataOutput output;
    private List<JSONObject> datas;

    public TransferThread(DataOutput output, List<JSONObject> datas) {
        this.output = output;
        this.datas = datas;
    }

    @Override
    public void run() {
        LOGGER.info(this.getName() + "处理线程启动。。。");
        try {
            // 数据转换
            String className = TaskConstants.TRANSFER_WAY;
            Class<?> clz = Class.forName(className);
            BaseDataTransform transform = (BaseDataTransform) clz.newInstance();
            datas = transform.transform(datas);
            output.output(datas);
            LOGGER.info(this.getName() + "处理线程完成。。。");
        } catch (Exception e) {
            // TODO 后续做异常处理
            LOGGER.error(this.getName() + "数据输出失败", e);
        }
    }

}
