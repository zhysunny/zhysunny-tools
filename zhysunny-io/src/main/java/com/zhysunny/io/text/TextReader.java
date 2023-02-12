package com.zhysunny.io.text;

import com.zhysunny.io.BaseReader;
import com.zhysunny.io.text.reader.BaseTextToAny;
import com.zhysunny.io.text.reader.TextToArray;
import com.zhysunny.io.text.reader.TextToBean;
import com.zhysunny.io.text.reader.TextToMap;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 文本类读取，以行作为一条记录
 * @author 章云
 * @date 2019/7/26 9:32
 */
public class TextReader extends BaseReader {

    /**
     * 列分隔符,默认逗号
     */
    private String split = ",";
    /**
     * 第一行是否是字段名,默认不是
     */
    private boolean hasHead = false;

    /**
     * 字段名
     */
    private String[] heads;

    /**
     * 批量读取数量，默认是int最大值，表示一个文件全部读完，如果设置小于1的数也会改成1
     */
    private int batch = Integer.MAX_VALUE;

    /**
     * 缓冲输入流
     */
    private BufferedReader br;

    public TextReader(File file) {
        super(file);
    }

    public TextReader(String path) {
        super(path);
    }

    public TextReader(URL url) {
        super(url);
    }

    public TextReader setSplit(String split) {
        this.split = split;
        return this;
    }

    public TextReader setHasHead(boolean hasHead) {
        this.hasHead = hasHead;
        return this;
    }

    public TextReader setHeads(String[] heads) {
        this.heads = heads;
        return this;
    }

    public String[] getHeads() {
        return this.heads;
    }

    public TextReader setBatch(int batch) {
        this.batch = batch;
        return this;
    }

    @Override
    public TextReader builder() throws Exception {
        Object resource = resources.get(0);
        if (resource instanceof URL) {
            URL url = (URL)resource;
            if (url != null) {
                br = new BufferedReader(new InputStreamReader(url.openStream()));
            }
        } else if (resource instanceof File) {
            File file = (File)resource;
            if (file.exists()) {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            }
        } else if (resource instanceof String) {
            File file = new File((String)resource);
            URL url = Thread.currentThread().getContextClassLoader().getResource((String)resource);
            if (url != null) {
                br = new BufferedReader(new InputStreamReader(url.openStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            }
        } else if (resource instanceof InputStream) {
            InputStream is = (InputStream)resource;
            br = new BufferedReader(new InputStreamReader(is));
        } else {
            throw new RuntimeException("不支持的资源配置类型：" + resource.getClass());
        }
        if (this.hasHead) {
            String line = br.readLine();
            if (line == null) {
                throw new Exception("第一行不能为空");
            }
            this.setHeads(line.split(this.split));
        }
        if (batch < 1) {
            batch = 1;
        }
        return this;
    }

    /**
     * 批量读取text数据
     * @param baseTextToAny
     * @param params
     * @return
     * @throws Exception
     */
    public <T> List<T> read(BaseTextToAny baseTextToAny, Object... params) throws Exception {
        if (br == null) {
            builder();
        }
        List<T> list = new ArrayList<T>(batch);
        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.trim().length() == 0) {
                continue;
            }
            String[] data = line.split(this.split, -1);
            Object read = baseTextToAny.read(data, params);
            list.add((T)read);
            if (list.size() == batch) {
                break;
            }
        }
        return list;
    }

    public List<String[]> readToArray() throws Exception {
        return read(new TextToArray());
    }

    public List<Map<String, String>> readToMap() throws Exception {
        return read(new TextToMap(), heads);
    }

    public <T> List<T> readToBean(Class<T> clz) throws Exception {
        return read(new TextToBean(), clz);
    }

    /**
     * 关闭流
     * @throws IOException
     */
    public void close() throws IOException {
        if (br != null) {
            br.close();
        }
    }

}
