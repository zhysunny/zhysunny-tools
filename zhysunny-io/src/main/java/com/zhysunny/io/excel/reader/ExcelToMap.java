package com.zhysunny.io.excel.reader;

import com.zhysunny.io.excel.ExcelReader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel转成Map(内存消耗较大)
 * @author 章云
 * @date 2019/7/26 15:02
 */
public class ExcelToMap {

    private ExcelReader reader;

    public ExcelToMap(ExcelReader reader) {
        this.reader = reader;
    }

    @Deprecated
    public Map<String, List<Map<String, String>>> read() {
        // 转成map对象，数据异常值忽略
        Map<String, List<Map<String, String>>> dataMap = new LinkedHashMap<String, List<Map<String, String>>>(reader.getSheetCount());
        for (int i = 0; i < reader.getSheetCount(); i++) {
            try {
                reader.readTitle(i);
                String sheetName = reader.getSheetName();
                List<Map<String, String>> dataList = new ArrayList<>();
                Map<String, String> map = null;
                while ((map = reader.readLine()) != null) {
                    if (map.size() == 0) {
                        continue;
                    }
                    dataList.add(map);
                }
                if (dataList.size() != 0) {
                    dataMap.put(sheetName, dataList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        reader.close();
        return dataMap;
    }

    public Map<String, Map<String, List<String>>> readLess() {
        // 转成map对象
        Map<String, Map<String, List<String>>> dataMap = new LinkedHashMap<String, Map<String, List<String>>>(reader.getSheetCount());
        for (int i = 0; i < reader.getSheetCount(); i++) {
            try {
                reader.readTitle(i);
                String sheetName = reader.getSheetName();
                Map<String, List<String>> childMap = new LinkedHashMap<String, List<String>>();
                Map<String, String> map = null;
                String key = null;
                String value = null;
                while ((map = reader.readLine()) != null) {
                    if (map.size() == 0) {
                        continue;
                    }
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        key = entry.getKey();
                        if (key == null || key.length() == 0) {
                            // 标题为空的列不读取
                            continue;
                        }
                        if (childMap.get(key) == null) {
                            childMap.put(key, new ArrayList<>());
                        }
                        value = entry.getValue();
                        if (value == null) {
                            value = "";
                        }
                        childMap.get(key).add(value);
                    }
                }
                if (childMap.size() > 0) {
                    dataMap.put(sheetName, childMap);
                }
            } catch (Exception e) {
                // 数据异常值忽略
                e.printStackTrace();
            }
        }
        reader.close();
        return dataMap;
    }

}
