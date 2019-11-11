package com.zhysunny.transfer.component.text;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.text.TextWriter;
import com.zhysunny.transfer.DataOutput;
import com.zhysunny.transfer.DataOutputForFile;
import com.zhysunny.transfer.component.xml.XmlDataOutput;
import com.zhysunny.transfer.constant.Constants;
import com.zhysunny.transfer.mapping.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * 文本类数据输出
 * @author 章云
 * @date 2019/8/27 15:45
 */
public class TextDataOutput extends DataOutputForFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextDataOutput.class);

    private Mapping mapping;

    public TextDataOutput(Mapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public int output(List<JSONObject> datas) throws Exception {
        File file = getFile(mapping, ".txt");
        TextWriter writer = new TextWriter(file).setHasHead(Constants.TEXT_HAS_HEADS_TO).setSplit(Constants.TEXT_LINE_SPLIT_TO);
        writer.write(new JsonToText(datas, mapping), datas);
        writer.close();
        LOGGER.info("文件：{}写入数据量：{}", file.getAbsolutePath(), datas.size());
        return datas.size();
    }

}
