package com.zhysunny.io.image;

import com.zhysunny.io.util.FileUtils;

import java.io.*;

/**
 * 检测是否是完整图片
 * @author 章云
 * @date 2019/7/27 22:23
 */
public class ImageUtils {

    /**
     * 查看是否是完整图片
     * @param image
     * @return
     */
    public static boolean isImage(File image) {
        boolean flag = false;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(image);
        } catch (FileNotFoundException e) {
            return false;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        try {
            while ((len = fis.read(buff)) != -1) {
                bos.write(buff, 0, len);
            }
        } catch (IOException e) {
            return false;
        }
        byte[] result = bos.toByteArray();
        String str = toHexString(result);
        if (str.endsWith("ffd9")) {
            flag = true;
        }
        FileUtils.close(fis);
        return flag;
    }

    private static String toHexString(byte[] fieldData) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldData.length; i++) {
            int v = (fieldData[i] & 0xFF);
            if (v <= 0xF) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

}
