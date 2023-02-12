package com.zhysunny.driver.bean;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 表结构配置
 * @author 章云
 * @date 2019/8/16 21:49
 */
@XmlRootElement(name = "Table")
@XmlAccessorType(XmlAccessType.FIELD)
public class Table {

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "Column")
    private List<Column> columns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", columns=" + columns +
                '}';
    }
}
