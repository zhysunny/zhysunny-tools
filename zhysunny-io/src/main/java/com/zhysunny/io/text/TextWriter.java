package com.zhysunny.io.text;

import com.zhysunny.io.BaseWriter;
import com.zhysunny.io.text.writer.ArrayToText;
import com.zhysunny.io.text.writer.BaseAnyToText;
import com.zhysunny.io.text.writer.BeanToText;
import com.zhysunny.io.text.writer.MapToText;
import com.zhysunny.io.util.StringUtils;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 文档输出类
 * @author 章云
 * @date 2019/7/27 14:42
 */
public class TextWriter extends BaseWriter implements Closeable {

    /**
     * 列分隔符,默认逗号
     */
    private String split = ",";
    /**
     * 第一行是否需要写字段名,默认不是
     */
    private boolean hasHead = false;

    /**
     * 字段名
     */
    private String[] heads;

    /**
     * 输出流
     */
    private BufferedWriter bw;

    public TextWriter(String path) {
        super(path);
    }

    public TextWriter(File file) {
        super(file);
    }

    public TextWriter setSplit(String split) {
        this.split = split;
        return this;
    }

    public TextWriter setHasHead(boolean hasHead) {
        this.hasHead = hasHead;
        return this;
    }

    public TextWriter setHeads(String[] heads) {
        this.heads = heads;
        return this;
    }

    public void writeOfArray(List<String[]> data) throws Exception {
        write(new ArrayToText(), data);
    }

    public void writeOfArray(List<String[]> data, boolean append) throws Exception {
        write(new ArrayToText(), data, append);
    }

    public void writeOfMap(List<Map<String, String>> data) throws Exception {
        write(new MapToText(data), data);
    }

    public void writeOfMap(List<Map<String, String>> data, boolean append) throws Exception {
        write(new MapToText(data), data, append);
    }

    public <T> void writeOfBean(List<T> data) throws Exception {
        write(new BeanToText(data), data);
    }

    public void writeOfBean(List<TextBean> data, boolean append) throws Exception {
        write(new BeanToText(data), data, append);
    }

    /**
     * 批量写入数据
     * @param data
     * @throws Exception
     */
    public <T> void write(BaseAnyToText baseAnyToText, List<T> data) throws Exception {
        write(baseAnyToText, data, false);
    }

    public <T> void write(BaseAnyToText baseAnyToText, List<T> data, boolean append) throws Exception {
        if (bw == null) {
            // bw只初始化一次，用于同一文件写入的追加操作
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append)));
            if (hasHead) {
                if (heads == null && data.size() > 0) {
                    heads = baseAnyToText.getHeads();
                }
                if (heads != null) {
                    bw.write(StringUtils.join(heads, split));
                    bw.newLine();
                }
            }
        }
        for (T t : data) {
            String[] line = baseAnyToText.write(t);
            bw.write(StringUtils.join(line, split));
            bw.newLine();
        }
    }

    /**
     * 关闭流
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        if (bw != null) {
            bw.close();
        }
    }

}
