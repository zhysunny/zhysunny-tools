package com.zhysunny.transfer.xml;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.xml.XmlWriter;
import com.zhysunny.transfer.DataOutput;
import com.zhysunny.transfer.mapping.Mapping;
import com.zhysunny.transfer.util.TaskConstants;

import java.io.File;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Xml写入
 * @author 章云
 * @date 2019/8/23 15:19
 */
public class XmlDataOutput implements DataOutput {

    private Mapping mapping;

    public XmlDataOutput(Mapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public int output(List<JSONObject> datas) throws Exception {
        // 如果输出数据类型是xml，那么target必须是目录或者文件名前缀
        String target = mapping.getTarget();
        File path = new File(target);
        if (target.endsWith("/") && !path.exists()) {
            path.mkdirs();
        }
        File file = null;
        if (path.isDirectory()) {
            file = new File(path, System.currentTimeMillis() + ".xml");
            synchronized (file) {
                while (file.exists()) {
                    Thread.sleep(100);
                    file = new File(path, System.currentTimeMillis() + ".xml");
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
            }
        } else {
            file = new File(path.getAbsolutePath() + "-" + System.currentTimeMillis() + ".xml");
            synchronized (file) {
                while (file.exists()) {
                    Thread.sleep(100);
                    file = new File(path.getAbsolutePath() + "-" + System.currentTimeMillis() + ".xml");
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
            }
        }
        new XmlWriter(file).write(new JsonToXml(mapping), datas);
        return datas.size();
    }
}
