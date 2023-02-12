package com.zhysunny.io.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 解压zip包
 * @author 章云
 * @date 2019/7/27 18:48
 */
@Deprecated
public class ZipUtils {
    /**
     * 解压zip包
     * @param zipFile
     * @param dirPath
     */
    public static List<String> unzip(File zipFile, String dirPath) {
        List<String> list = new ArrayList<String>();
        try {
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = null;
            byte[] b = new byte[1024];
            while ((ze = zis.getNextEntry()) != null) {
                File dir = new File(dirPath);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File zfile = new File(dir, ze.getName());
                File fpath = new File(zfile.getParentFile().getPath());
                if (ze.isDirectory()) {
                    if (!zfile.exists()) {
                        zfile.mkdirs();
                    }
                } else {
                    if (!fpath.exists()) {
                        fpath.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(zfile);
                    int i;
                    list.add(zfile.getAbsolutePath());
                    while ((i = zis.read(b)) != -1) {
                        fos.write(b, 0, i);
                    }
                    fos.close();
                }
            }
            fis.close();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解压zip包
     * @param zip
     */
    public static void releaseZip(File zip) {
        String dirPath = zip.getParent() + File.separator + zip.getName().split("\\.")[0];
        unzip(zip, dirPath);
    }
}
