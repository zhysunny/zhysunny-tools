package com.zhysunny.transfer.xml;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.xml.XmlReader;
import com.zhysunny.transfer.DataBaseTransfer;
import com.zhysunny.transfer.DataInput;
import com.zhysunny.transfer.DataOutput;
import com.zhysunny.transfer.mapping.Field;
import com.zhysunny.transfer.mapping.Mapping;
import com.zhysunny.transfer.util.TaskConstants;
import com.zhysunny.transfer.util.ThreadPoolUtil;
import com.zhysunny.transfer.util.Transfer;
import com.zhysunny.transfer.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 * Xml读取
 * @author 章云
 * @date 2019/8/15 21:13
 */
public class XmlDataInput implements DataInput {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlDataInput.class);

    private Mapping mapping;
    private DataOutput output;

    public XmlDataInput(Mapping mapping, DataOutput output) {
        this.mapping = mapping;
        this.output = output;
    }

    @Override
    public void input() throws Exception {
        // 如果输入数据类型是xml，那么source必须是目录或者文件
        String source = mapping.getSource();
        File path = new File(source);
        List<String> files = new ArrayList<String>();
        // 读取数据时会过滤掉非xml文件
        Utils.getFiles(path, ".xml", files);
        List<JSONObject> datas = new ArrayList<JSONObject>(TaskConstants.TRANSFER_BATCH);
        ThreadPoolUtil instance = ThreadPoolUtil.getInstance();
        for (String fileName : files) {
            LOGGER.info("读取文件：" + fileName);
            List<JSONObject> list = (List<JSONObject>) new XmlReader(fileName).read(new XmlToJson(mapping));
            datas = transfer(datas, list, instance, output);
        }
        if (datas.size() > 0) {
            instance.addThread(new Transfer(output, datas));
        }
    }

}
