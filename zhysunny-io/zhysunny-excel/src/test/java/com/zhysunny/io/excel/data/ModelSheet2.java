package com.zhysunny.io.excel.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Objects;

/**
 * @author zhysunny
 * @date 2023/3/12 10:41
 */
@Data
@AllArgsConstructor
public class ModelSheet2 implements ModelSheet {

    private String name;

    private Integer mainIncome;

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
