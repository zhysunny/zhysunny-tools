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

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
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
