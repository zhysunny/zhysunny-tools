package com.zhysunny.io.excel.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhysunny
 * @date 2023/3/12 10:44
 */
public class ModelData {

    public static final String EXCEL_NAME = "test.xlsx";

    public static List<ModelSheet1> getSheet1() {
        List<ModelSheet1> data = new ArrayList<>();
        data.add(new ModelSheet1("张三", 20));
        data.add(new ModelSheet1("李四", 18));
        data.add(new ModelSheet1("王五", 25));
        return data;
    }

    public static List<ModelSheet2> getSheet2() {
        List<ModelSheet2> data = new ArrayList<>();
        data.add(new ModelSheet2("张三", 1000, 1000));
        data.add(new ModelSheet2("李四", 1000, 1000));
        data.add(new ModelSheet2("王五", 1000, 1000));
        return data;
    }

    public static boolean equals(List<? extends ModelSheet> data1, List<? extends ModelSheet> data2) {
        if (data1 == null || data2 == null) {
            return false;
        }
        if (data1.size() != data2.size()) {
            return false;
        }
        Set<ModelSheet> set = new HashSet<>(data1);
        set.addAll(data2);
        return data1.size() == set.size();
    }

}
