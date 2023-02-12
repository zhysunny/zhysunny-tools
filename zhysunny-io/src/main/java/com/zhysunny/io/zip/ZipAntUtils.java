package com.zhysunny.io.zip;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;

/**
 * ant解压zip包
 * @author 章云
 * @date 2019/7/27 18:47
 */
public class ZipAntUtils {

    private static final int LENGTH = 8192;

    /**
     * 解压zip包
     * @param zip     zip文件
     * @param dirPath 解压到目录
     * @throws IOException
     */
    public static void unzip(File zip, String dirPath) throws IOException {
        // 读取zip压缩文件
        try {
            ZipFile zipFile = new ZipFile(zip, "GB18030");
            // ZipFile zipFile = new ZipFile(file, "UTF-8");
            Enumeration<ZipEntry> e = zipFile.getEntries();
            ZipEntry zipEntry = null;
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            while (e.hasMoreElements()) {
                zipEntry = e.nextElement();
                File zfile = new File(dir, zipEntry.getName());
                File fpath = new File(zfile.getParentFile().getPath());
                if (zipEntry.isDirectory()) {
                    if (!zfile.exists()) {
                        zfile.mkdirs();
                    }
                } else {
                    if (!fpath.exists()) {
                        fpath.mkdirs();
                    }
                    InputStream is = zipFile.getInputStream(zipEntry);
                    OutputStream os = new FileOutputStream(zfile);
                    byte[] buf = new byte[1024];
                    int i;
                    while ((i = is.read(buf)) > 0) {
                        os.write(buf, 0, i);
                    }
                    os.close();
                    is.close();
                }
            }
            zipFile.close();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * 解压zip包，创建zip命名的目录，并解压到这个目录下
     * @param zip
     */
    public static void unzip(File zip) throws IOException {
        String dirPath = zip.getParent() + File.separator + zip.getName().split("\\.")[0];
        unzip(zip, dirPath);
    }

    /**
     * 将目录下的文件压缩，不包括第一级目录
     * @param dir
     * @param zipFile
     */
    public static void zip(File dir, File zipFile) throws IOException {
        if (!dir.exists()) {
            throw new FileNotFoundException(dir.getAbsolutePath() + "不存在！");
        }
        Project project = new Project();
        Zip zip = new Zip();
        zip.setProject(project);
        zip.setDestFile(zipFile);
        FileSet fileSet = new FileSet();
        fileSet.setProject(project);
        fileSet.setDir(dir);
        zip.addFileset(fileSet);
        zip.execute();
    }

    /**
     * 只打包文件，不打包目录
     * @param file
     * @param out
     * @throws IOException
     */
    public static void zip(File file, ZipOutputStream out) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath() + "不存在！");
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                zip(f, out);
            }
        } else {
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                ZipEntry entry = new ZipEntry(file.getName());
                out.putNextEntry(entry);
                int count = 0;
                byte[] data = new byte[LENGTH];
                while ((count = bis.read(data, 0, LENGTH)) != -1) {
                    out.write(data, 0, count);
                }
                bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
