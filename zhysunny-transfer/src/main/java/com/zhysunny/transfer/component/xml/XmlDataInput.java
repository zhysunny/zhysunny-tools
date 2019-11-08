package com.zhysunny.transfer.component.xml;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.xml.XmlReader;
import com.zhysunny.transfer.DataInputForFile;
import com.zhysunny.transfer.mapping.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Xml读取
 * @author 章云
 * @date 2019/8/15 21:13
 */
public class XmlDataInput extends DataInputForFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlDataInput.class);

    private Mapping mapping;

    public XmlDataInput(Mapping mapping) {
        super(mapping, ".xml");
        this.mapping = mapping;
    }

    @Override
    public List<JSONObject> input() throws Exception {
        if (index >= files.size()) {
            return null;
        }
        String fileName = files.get(index++);
        LOGGER.info("读取文件：" + fileName);
        return (List<JSONObject>)new XmlReader(fileName).read(new XmlToJson(mapping));
    }

}
