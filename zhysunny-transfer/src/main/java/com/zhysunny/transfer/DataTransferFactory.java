package com.zhysunny.transfer;

import com.zhysunny.transfer.constant.Constants;
import com.zhysunny.transfer.mapping.Mapping;
import com.zhysunny.transfer.component.xml.XmlDataInput;
import com.zhysunny.transfer.component.xml.XmlDataOutput;
import java.lang.invoke.ConstantCallSite;

/**
 * 输入输出工厂类
 * @author 章云
 * @date 2019/8/23 11:03
 */
public class DataTransferFactory {

    public static DataInput getDataInput(Mapping mapping) {
        if ("xml".equalsIgnoreCase(Constants.DATA_TYPE_FROM)) {
            return new XmlDataInput(mapping);
        }
        return null;
    }

    public static DataOutput getDataOutput(Mapping mapping) {
        if ("xml".equalsIgnoreCase(Constants.DATA_TYPE_FROM)) {
            return new XmlDataOutput(mapping);
        }
        return null;
    }

}
