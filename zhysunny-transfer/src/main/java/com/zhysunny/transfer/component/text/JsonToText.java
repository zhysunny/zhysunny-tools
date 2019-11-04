package com.zhysunny.transfer.component.text;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.text.writer.BaseAnyToText;
import com.zhysunny.transfer.mapping.Mapping;

import java.util.List;

/**
 * json转text
 * @author 章云
 * @date 2019/8/27 16:00
 */
public class JsonToText extends BaseAnyToText {

    private Mapping mapping;
    private String[] heads;

    public JsonToText(List<JSONObject> data, Mapping mapping) {
        JSONObject json = data.get(0);
        this.heads = new String[json.size()];
        this.heads = json.keySet().toArray(heads);
        this.mapping = mapping;
    }

    @Override
    public String[] write(Object... params) throws Exception {
        if (params != null && params.length > 0) {
            JSONObject json = (JSONObject) params[0];
            String[] data = new String[json.size()];
            for (int i = 0; i < heads.length; i++) {
                data[i] = (String) json.get(heads[i]);
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
