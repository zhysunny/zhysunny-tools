package com.zhysunny.transfer.component.text;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.text.TextReader;
import com.zhysunny.io.xml.XmlReader;
import com.zhysunny.transfer.DataInput;
import com.zhysunny.transfer.DataInputForFile;
import com.zhysunny.transfer.DataOutput;
import com.zhysunny.transfer.component.xml.XmlToJson;
import com.zhysunny.transfer.mapping.Mapping;
import com.zhysunny.transfer.thread.TransferThread;
import com.zhysunny.transfer.constant.Constants;
import com.zhysunny.transfer.util.ThreadPoolUtil;
import com.zhysunny.transfer.util.Utils;
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
public class TextDataInput extends DataInputForFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextDataInput.class);

    private Mapping mapping;
    private TextReader reader;

    public TextDataInput(Mapping mapping) {
        super("");
        this.mapping = mapping;
    }

    @Override
    public List<JSONObject> input() throws Exception {
        if (index >= files.size()) {
            return null;
        }
        if (reader == null) {
            String fileName = files.get(index);
            LOGGER.info("读取文件：" + fileName);
            reader = new TextReader(fileName).setHasHead(Constants.TEXT_HAS_HEADS_FROM).setSplit(Constants.TEXT_LINE_SPLIT_FROM)
            .setBatch(Constants.TRANSFER_BATCH).builder();
        }
        List<JSONObject> result = reader.read(new TextToJson(mapping), (Object[])reader.getHeads());
        if (result.size() == 0) {
            index++;
            reader = null;
            return input();
        }
        return result;
    }

}
