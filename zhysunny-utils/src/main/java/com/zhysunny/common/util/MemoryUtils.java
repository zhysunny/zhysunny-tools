package com.zhysunny.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * 内存计算工具
 * @author 章云
 * @date 2019/12/9 16:33
 */
public class MemoryUtils {

    public static long getMemorySize(Object obj) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(bos);) {
            oos.writeObject(obj);
            System.out.println(Arrays.toString(bos.toByteArray()));
            return bos.toByteArray().length;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

}
