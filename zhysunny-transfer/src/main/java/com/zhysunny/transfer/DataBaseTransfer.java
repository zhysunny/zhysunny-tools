package com.zhysunny.transfer;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.properties.PropertiesReader;
import com.zhysunny.io.xml.XmlReader;
import com.zhysunny.transfer.mapping.Mapping;
import com.zhysunny.transfer.thread.ShutdownHookThread;
import com.zhysunny.transfer.util.TaskConstants;
import com.zhysunny.transfer.util.ThreadPoolUtil;
import com.zhysunny.transfer.thread.TransferThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.List;

/**
 * 数据转移主函数
 * @author 章云
 * @date 2019/8/15 20:21
 */
public class DataBaseTransfer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseTransfer.class);

    private Mapping mapping;

    public DataBaseTransfer(Mapping mapping) {
        this.mapping = mapping;
    }

    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            System.err.println("任务配置文件");
            System.exit(1);
        }
        // 程序终止时的操作
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());
        // 读取任务配置文件，一般是properties文件
        File file = new File(args[0]);
        if (!file.exists()) {
            System.err.println(args[0] + "任务配置文件不存在");
            System.exit(1);
        }
        new PropertiesReader(file).toConstant(TaskConstants.class);
        // 读取映射文件，一般是xml文件
        File mappingFile = new File(TaskConstants.MAPPING_FILE);
        if (!file.exists()) {
            System.err.println(TaskConstants.MAPPING_FILE + "映射文件不存在");
            System.exit(1);
        }
        Mapping mapping = new XmlReader(mappingFile).read(Mapping.class);
        // 开始数据转移
        new DataBaseTransfer(mapping).start();
    }

    private void start() throws Exception {
        // 初始化线程池
        LOGGER.info("=============================== 数据转移任务启动 ===============================");
        LOGGER.info("===== 数据输入类型：" + mapping.getFrom());
        LOGGER.info("===== 数据输出类型：" + mapping.getTo());
        LOGGER.info("===== 输入数据源：" + mapping.getSource());
        LOGGER.info("===== 输出数据源：" + mapping.getTarget());
        LOGGER.info("===== 核心线程数：" + TaskConstants.TRANSFER_PARALLEL);
        LOGGER.info("===== 批处理数：" + TaskConstants.TRANSFER_BATCH);
        // 初始化线程池
        ThreadPoolUtil instance = ThreadPoolUtil.getInstance(20);
        // 数据输入
        DataInput dataInput = DataTransferFactory.getDataInput(mapping);
        // 数据输出
        DataOutput dataOutput = DataTransferFactory.getDataOutput(mapping);
        // 获取第一份数据
        List<JSONObject> input = dataInput.input();
        // 批处理
        DataBatch dataBatch = new DataBatch(input, TaskConstants.TRANSFER_BATCH);
        List<JSONObject> datas = null;
        while (input != null) {
            if (datas != null) {
                dataBatch = new DataBatch(input, TaskConstants.TRANSFER_BATCH, datas);
            }
            while (dataBatch.batch()) {
                datas = dataBatch.getBatchDatas();
                instance.addThread(new TransferThread(dataOutput, datas));
            }
            datas = dataBatch.getBatchDatas();
            input = dataInput.input();
        }
        // 处理最后剩余的数据
        if (datas.size() > 0) {
            instance.addThread(new TransferThread(dataOutput, datas));
        }
        instance.shutdown();
    }

}
