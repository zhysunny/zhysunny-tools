package com.zhysunny.transfer;

import com.zhysunny.transfer.constant.Constants;
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
public abstract class AbstractDataInputForFile implements DataInput {

    protected List<String> files;
    protected int index;

    public AbstractDataInputForFile(String suffix) {
        this.index = 0;
        String[] sources = Constants.DATA_SOURCE_FROM;
        files = new ArrayList<>();
        for (String source : sources) {
            File path = new File(source);
            Utils.getFiles(path, suffix, files);
        }
    }

}
