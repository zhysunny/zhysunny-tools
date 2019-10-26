package com.zhysunny.transfer.mapping;

import com.zhysunny.io.xml.XmlBean;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 映射文件root实体类
 * @author 章云
 * @date 2019/8/15 20:55
 */
@XmlRootElement(name = "Mapping")
@XmlAccessorType(XmlAccessType.FIELD)
public class Mapping implements XmlBean {

    /**
     * 输入数据类型
     */
    @XmlAttribute(name = "from")
    private String from;
    /**
     * 输出数据类型
     */
    @XmlAttribute(name = "to")
    private String to;
    /**
     * 输入数据源(表名，目录，文件等)
     */
    @XmlAttribute(name = "source")
    private String source;
    /**
     * 输出数据源(表名，目录，文件等)
     */
    @XmlAttribute(name = "target")
    private String target;

    /************************* xml字段属性 start *************************/
    /**
     * 针对xml需要输入数据节点
     */
    @XmlAttribute(name = "xml-from-data-node")
    private String xmlFromDataNode;
    /**
     * 针对xml需要输入数据节点
     */
    @XmlAttribute(name = "xml-to-data-node")
    private String xmlToDataNode;
    /**
     * 针对xml需要输出的root节点名
     */
    @XmlAttribute(name = "xml-root-name")
    private String xmlRootName;
    /************************* xml字段属性 end *************************/

    /************************* text字段属性 start *************************/
    /**
     * 输入数据是否包含字段名(默认不包含)
     */
    @XmlAttribute(name = "text-has-from-heads")
    private boolean fromHeads;
    /**
     * 输出数据是否包含字段名(默认不包含)
     */
    @XmlAttribute(name = "text-has-to-heads")
    private boolean toHeads;
    /**
     * 输入数据分隔符(默认逗号)
     */
    @XmlAttribute(name = "text-from-split")
    private String fromSplits = ",";
    /**
     * 输出数据分隔符(默认逗号)
     */
    @XmlAttribute(name = "text-to-split")
    private String toSplits = ",";
    /************************* text字段属性 end *************************/

    @XmlElement(name = "Field")
    private List<Field> fields;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getXmlFromDataNode() {
        return xmlFromDataNode;
    }

    public void setXmlFromDataNode(String xmlFromDataNode) {
        this.xmlFromDataNode = xmlFromDataNode;
    }

    public String getXmlToDataNode() {
        return xmlToDataNode;
    }

    public void setXmlToDataNode(String xmlToDataNode) {
        this.xmlToDataNode = xmlToDataNode;
    }

    public String getXmlRootName() {
        return xmlRootName;
    }

    public void setXmlRootName(String xmlRootName) {
        this.xmlRootName = xmlRootName;
    }

    public boolean isFromHeads() {
        return fromHeads;
    }

    public void setFromHeads(boolean fromHeads) {
        this.fromHeads = fromHeads;
    }

    public boolean isToHeads() {
        return toHeads;
    }

    public void setToHeads(boolean toHeads) {
        this.toHeads = toHeads;
    }

    public String getFromSplits() {
        return fromSplits;
    }

    public void setFromSplits(String fromSplits) {
        this.fromSplits = fromSplits;
    }

    public String getToSplits() {
        return toSplits;
    }

    public void setToSplits(String toSplits) {
        this.toSplits = toSplits;
    }

    /**
     * 通过输入key找到输出key
     * @return
     */
    public String valueToName(String value) {
        for (Field field : fields) {
            if (value.equals(field.getFromName())) {
                return field.getToName();
            }
        }
        return null;
    }

    /**
     * 通过name找到type值
     * @param name
     */
    public String getFieldType(String name) {
        for (Field field : fields) {
            if (name.equals(field.getToName())) {
                return field.getType();
            }
        }
        return null;
    }
}
