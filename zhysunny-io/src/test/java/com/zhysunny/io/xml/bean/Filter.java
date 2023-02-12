package com.zhysunny.io.xml.bean;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Filter")
@XmlAccessorType(XmlAccessType.FIELD)
public class Filter {

    @XmlElement(name = "Column")
    private List<Column> columns;

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "columns=" + columns +
                '}';
    }
}
