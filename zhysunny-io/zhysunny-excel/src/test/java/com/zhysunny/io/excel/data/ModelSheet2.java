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
public class ModelSheet2 implements ModelSheet {
    @ColumnWidth(6)
    @ExcelProperty("姓名")
    private String name;

    @ColumnWidth(9)
    @ExcelProperty({"收入", "主收入"})
    private Integer mainIncome;

    @ColumnWidth(9)
    @ExcelProperty({"收入", "副收入"})
    private Integer secondaryIncome;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ModelSheet2 that = (ModelSheet2) o;
        return Objects.equals(name, that.name) && Objects.equals(mainIncome, that.mainIncome) && Objects
                .equals(secondaryIncome, that.secondaryIncome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mainIncome, secondaryIncome);
    }
}
