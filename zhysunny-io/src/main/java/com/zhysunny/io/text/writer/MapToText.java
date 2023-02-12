package com.zhysunny.io.text.writer;

import java.util.List;
import java.util.Map;

/**
 * map数据转text
 * @author 章云
 * @date 2019/8/27 15:05
 */
public class MapToText extends BaseAnyToText {

    private String[] heads;

    public MapToText(List<Map<String, String>> data) {
        Map<String, String> map = data.get(0);
        this.heads = new String[map.size()];
        this.heads = map.keySet().toArray(heads);
    }

    @Override
    public String[] write(Object... params) throws Exception {
        if (params != null && params.length > 0) {
            Map<String, String> map = (Map<String, String>) params[0];
            String[] data = new String[map.size()];
            for (int i = 0; i < heads.length; i++) {
                data[i] = map.get(heads[i]);
            }
            return data;
        }
        return null;
    }

    @Override
    public String[] getHeads() {
        return this.heads;
    }
}
