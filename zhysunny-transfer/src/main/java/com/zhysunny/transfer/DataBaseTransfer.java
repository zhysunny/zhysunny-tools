package com.zhysunny.transfer;

import com.zhysunny.io.properties.PropertiesReader;
import com.zhysunny.io.xml.XmlReader;
import com.zhysunny.transfer.mapping.Mapping;
import com.zhysunny.transfer.util.TaskConstants;
import com.zhysunny.transfer.util.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

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
        ThreadPoolUtil instance = ThreadPoolUtil.getInstance(TaskConstants.TRANSFER_PARALLEL);
        DataInput dataInput = DataTransferFactory.getDataInput(mapping);
        dataInput.input();
        // 关闭线程池
        instance.shutdown();
        LOGGER.info("=============================== 数据转移任务完成 ===============================");
    }

}
