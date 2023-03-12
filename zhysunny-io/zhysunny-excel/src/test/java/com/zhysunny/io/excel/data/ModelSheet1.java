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
public class ModelSheet1 implements ModelSheet {

    private String name;

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
