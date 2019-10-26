package com.zhysunny.transfer.text;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.text.TextReader;
import com.zhysunny.io.xml.XmlReader;
import com.zhysunny.transfer.DataInput;
import com.zhysunny.transfer.DataOutput;
import com.zhysunny.transfer.mapping.Mapping;
import com.zhysunny.transfer.util.TaskConstants;
import com.zhysunny.transfer.util.ThreadPoolUtil;
import com.zhysunny.transfer.util.Transfer;
import com.zhysunny.transfer.util.Utils;
import com.zhysunny.transfer.xml.XmlToJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文本类数据输入
 * @author 章云
 * @date 2019/8/27 15:45
 */
public class TextDataInput implements DataInput {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextDataInput.class);

    private Mapping mapping;
    private DataOutput output;

    public TextDataInput(Mapping mapping, DataOutput output) {
        this.mapping = mapping;
        this.output = output;
    }

    @Override
    public void input() throws Exception {
        // 如果输入数据类型是text，那么source必须是目录或者文件
        String source = mapping.getSource();
        File path = new File(source);
        List<String> files = new ArrayList<String>();
        // 读取text数据时不过滤掉，对任务文本类型都会读取
        Utils.getFiles(path, "", files);
        List<JSONObject> datas = new ArrayList<JSONObject>(TaskConstants.TRANSFER_BATCH);
        ThreadPoolUtil instance = ThreadPoolUtil.getInstance();
        List<JSONObject> list = null;
        for (String fileName : files) {
            LOGGER.info("读取文件：" + fileName);
            TextReader reader = new TextReader(fileName)
                    .setHasHead(mapping.isFromHeads())
                    .setSplit(mapping.getFromSplits())
                    .setBatch(TaskConstants.TRANSFER_BATCH)
                    .builder();
            while (true) {
                list = reader.read(new TextToJson(mapping), (Object[]) reader.getHeads());
                if (list.size() == 0) {
                    break;
                }
                datas = transfer(datas, list, instance, output);
            }
            reader.close();
        }
        if (datas.size() > 0) {
            instance.addThread(new Transfer(output, datas));
        }
    }

}
