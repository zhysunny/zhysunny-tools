package com.zhysunny.io.excel.data;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

/**
 * @author zhysunny
 * @date 2023/3/12 10:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelSheet1 implements ModelSheet {

    @ColumnWidth(6)
    @ExcelProperty("姓名")
    private String name;

    @ColumnWidth(6)
    @ExcelProperty("年龄")
    private Integer age;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ModelSheet1 that = (ModelSheet1) o;
        return Objects.equals(name, that.name) && Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
