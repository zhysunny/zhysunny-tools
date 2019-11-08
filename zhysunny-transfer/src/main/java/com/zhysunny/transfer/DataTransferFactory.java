package com.zhysunny.transfer;

import com.zhysunny.transfer.mapping.Mapping;
import com.zhysunny.transfer.component.xml.XmlDataInput;
import com.zhysunny.transfer.component.xml.XmlDataOutput;

/**
 * 输入输出工厂类
 * @author 章云
 * @date 2019/8/23 11:03
 */
public class DataTransferFactory {

    public static DataInput getDataInput(Mapping mapping) {
        if ("xml".equalsIgnoreCase(mapping.getFrom())) {
            return new XmlDataInput(mapping);
        }
        return null;
    }

    public static DataOutput getDataOutput(Mapping mapping) {
        if ("xml".equalsIgnoreCase(mapping.getFrom())) {
            return new XmlDataOutput(mapping);
        }
        return null;
    }

}
