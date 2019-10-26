package com.zhysunny.transfer.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 工具类
 * @author 章云
 * @date 2019/8/15 21:25
 */
public class Utils {

    public static void getFiles(File path, String suffix, List<String> files) {
        if (path.isFile()) {
            if (path.getName().endsWith(suffix)) {
                files.add(path.getAbsolutePath());
            }
        } else {
            String[] names = path.list();
            File file = null;
            for (String name : names) {
                file = new File(path, name);
                getFiles(file, suffix, files);
            }
        }
    }

}
