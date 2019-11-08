package com.zhysunny.transfer.component.xml;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.xml.XmlWriter;
import com.zhysunny.transfer.DataOutput;
import com.zhysunny.transfer.DataOutputForFile;
import com.zhysunny.transfer.mapping.Mapping;
import com.zhysunny.transfer.thread.TransferThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Xml写入
 * @author 章云
 * @date 2019/8/23 15:19
 */
public class XmlDataOutput extends DataOutputForFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlDataOutput.class);

    private Mapping mapping;

    public XmlDataOutput(Mapping mapping) {
        super(mapping);
        this.mapping = mapping;
    }

    @Override
    public int output(List<JSONObject> datas) throws Exception {
        File file = new File(path, UUID.randomUUID().toString() + ".xml");
        synchronized (mapping) {
            while (file.exists()) {
                Thread.sleep(100);
                file = new File(path, UUID.randomUUID().toString() + ".xml");
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        }
        new XmlWriter(file).write(new JsonToXml(mapping), datas);
        LOGGER.info("文件：{}写入数据量：{}", file.getAbsolutePath(), datas.size());
        return datas.size();
    }

}
