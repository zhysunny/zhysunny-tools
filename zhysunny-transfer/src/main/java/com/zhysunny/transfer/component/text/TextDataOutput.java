package com.zhysunny.transfer.component.text;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.text.TextWriter;
import com.zhysunny.transfer.DataOutput;
import com.zhysunny.transfer.mapping.Mapping;

import java.io.File;
import java.util.List;

/**
 * 文本类数据输出
 * @author 章云
 * @date 2019/8/27 15:45
 */
public class TextDataOutput implements DataOutput {
    private Mapping mapping;

    public TextDataOutput(Mapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public int output(List<JSONObject> datas) throws Exception {
        // 如果输出数据类型是text，那么target必须是目录或者文件名前缀
        String target = mapping.getTarget();
        File path = new File(target);
        if (target.endsWith("/") && !path.exists()) {
            path.mkdirs();
        }
        File file = null;
        if (path.isDirectory()) {
            file = new File(path, System.currentTimeMillis() + ".txt");
            synchronized (file) {
                while (file.exists()) {
                    Thread.sleep(100);
                    file = new File(path, System.currentTimeMillis() + ".txt");
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
            }
        } else {
            file = new File(path.getAbsolutePath() + "-" + System.currentTimeMillis() + ".txt");
            synchronized (file) {
                while (file.exists()) {
                    Thread.sleep(100);
                    file = new File(path.getAbsolutePath() + "-" + System.currentTimeMillis() + ".txt");
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
            }
        }
        new TextWriter(file)
                .setHasHead(mapping.isToHeads())
                .setSplit(mapping.getToSplits())
                .write(new JsonToText(datas, mapping), datas);
        return datas.size();
    }
}
