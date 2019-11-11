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

    @XmlElement(name = "Field")
    private List<Field> fields;

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
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
