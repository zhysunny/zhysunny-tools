package com.zhysunny.transfer;

import com.zhysunny.transfer.mapping.Mapping;
import com.zhysunny.transfer.util.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件类输入源
 * @author 章云
 * @date 2019/11/7 17:59
 */
public abstract class DataInputForFile implements DataInput {

    protected List<String> files;
    protected int index;

    public DataInputForFile(Mapping mapping, String suffix) {
        this.index = 0;
        String source = mapping.getSource();
        File path = new File(source);
        files = new ArrayList<>();
        Utils.getFiles(path, suffix, files);
    }

}
