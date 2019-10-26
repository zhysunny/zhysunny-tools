package com.zhysunny.transfer.mapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 映射文件字段配置
 * @author 章云
 * @date 2019/8/15 20:56
 */
@XmlRootElement(name = "Field")
@XmlAccessorType(XmlAccessType.FIELD)
public class Field {

    /**
     * 输出表的字段名
     */
    @XmlAttribute(name = "name")
    private String toName;
    /**
     * 输入表的字段名
     */
    @XmlAttribute(name = "value")
    private String fromName;
    /**
     * 字段类型，不做数据处理，字段类型必须一致或者可转换
     */
    @XmlAttribute(name = "type")
    private String type;

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Field{" +
                "toName='" + toName + '\'' +
                ", fromName='" + fromName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
