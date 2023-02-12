package com.zhysunny.io.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;

/**
 * NIO读写
 * @author 章云
 * @date 2019/12/27 20:48
 */
public class NioFileReadWrite implements FileReadWrite<String> {

    private File file;
    private boolean append;

    public NioFileReadWrite(File file, boolean append) {
        this.file = file;
        this.append = append;
    }

    public NioFileReadWrite(File file) {
        this(file, false);
    }

    public NioFileReadWrite(String filepath) {
        this(new File(filepath), false);
    }

    public NioFileReadWrite(String filepath, boolean append) {
        this(new File(filepath), append);
    }

    @Override
    public List<String> read() throws IOException {
        final List<String> datas = new ArrayList<>();
        if (!file.exists()) {
            return datas;
        }
        try {
            datas.addAll(Files.lines(Paths.get(file.getAbsolutePath())).collect(toList()));
        } catch (IOException e) {
            throw e;
        }
        return datas;
    }

    @Override
    public Object write(List<String> datas) throws IOException {
        OpenOption option;
        if (append) {
            option = StandardOpenOption.APPEND;
        } else {
            option = StandardOpenOption.WRITE;
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        try {
            Files.write(Paths.get(file.getAbsolutePath()), datas.stream().collect(toList()), option);
        } catch (IOException e) {
            throw e;
        }
        return datas.size();
    }

    @Override
    public Object write(Map<String, String> datas) throws IOException {
        List<String> collect = datas.entrySet().stream().map(entry -> entry.getKey() + "\t" + entry.getValue()).collect(toList());
        return write(collect);
    }

    @Override
    public File getFile() {
        return this.file;
    }

}
