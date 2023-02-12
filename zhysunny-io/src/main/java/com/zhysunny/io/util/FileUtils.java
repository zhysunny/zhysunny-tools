package com.zhysunny.io.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 文件类操作工具
 * @author 章云
 * @date 2019/7/27 20:52
 */
public class FileUtils {

    private static final int LENGTH = 8192;

    /**
     * 递归删除文件
     * @param file
     * @throws IOException
     */
    public static void deleteAllFile(File file) {
        if (file.isFile()) {
            try {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.exists()) {
                        deleteAllFile(f);
                    }
                }
                try {
                    Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 复制文件或目录(可以重命名)
     * @param file
     * @param toPath
     * @param fileName
     */
    public static void copyFile(File file, String toPath, String fileName) {
        if (file.isDirectory()) {
            File toPathDir = new File(toPath, fileName);
            if (!toPathDir.exists()) {
                toPathDir.mkdirs();
            }
            File[] sonFiles = file.listFiles();
            for (File sonFile : sonFiles) {
                copyFile(sonFile, toPathDir.getAbsolutePath(), sonFile.getName());
            }
        } else {
            File toPathDir = new File(toPath);
            if (!toPathDir.exists()) {
                toPathDir.mkdirs();
            }
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                fis = new FileInputStream(file);
                byte[] b = new byte[LENGTH];
                int read = 0;
                fos = new FileOutputStream(new File(toPath, fileName));
                while ((read = fis.read(b)) > 0) {
                    fos.write(b, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(fis, fos);
            }
        }
    }

    /**
     * 复制文件或目录
     * @param file
     * @param toPath
     */
    public static void copyFile(File file, String toPath) {
        copyFile(file, toPath, file.getName());
    }


    /**
     * 移动文件或目录(重命名)
     * @param file
     * @param toPath
     * @param fileName
     */
    public static void moveFile(File file, String toPath, String fileName) {
        copyFile(file, toPath, fileName);
        deleteAllFile(file);
    }

    /**
     * 移动文件或目录
     * @param file
     * @param toPath
     */
    public static void moveFile(File file, String toPath) {
        copyFile(file, toPath);
        deleteAllFile(file);
    }

    /**
     * 获得目录下文件数组并按修改时间升序排序
     * @param directory
     * @return
     */
    public static File[] listFilesSortTime(File directory) {
        File[] files = directory.listFiles();
        sort(files, 1);
        return files;
    }

    /**
     * 获得目录下文件数组并按修改时间降序排序
     * @param directory
     * @return
     */
    public static File[] listFilesReverseTime(File directory) {
        File[] files = directory.listFiles();
        sort(files, -1);
        return files;
    }

    /**
     * 获得目录下文件名数组并按修改时间升序排序
     * @param directory
     * @return
     */
    public static String[] listSortTime(File directory) {
        String[] fileNames = directory.list();
        sort(fileNames, directory, 1);
        return fileNames;
    }

    /**
     * 获得目录下文件名数组并按修改时间降序排序
     * @param directory
     * @return
     */
    public static String[] listReverseTime(File directory) {
        String[] fileNames = directory.list();
        sort(fileNames, directory, -1);
        return fileNames;
    }


    /**
     * 按文件时间排序
     * @param files 文件数组
     * @param sort  大于0升序，小于0降序，等于0无序
     */
    private static void sort(File[] files, final int sort) {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                int result = 0;
                if (file1.exists() && file2.exists()) {
                    Long fileLastTime1 = file1.lastModified();
                    Long fileLastTime2 = file2.lastModified();
                    result = fileLastTime1.compareTo(fileLastTime2);
                    result = sort * result;
                }
                return result;
            }
        });
    }

    /**
     * 按文件时间排序
     * @param fileNames 文件名数组
     * @param directory 文件名对应文件的父目录
     * @param sort      大于0升序，小于0降序，等于0无序
     */
    private static void sort(String[] fileNames, File directory, final int sort) {
        Arrays.sort(fileNames, new Comparator<String>() {
            @Override
            public int compare(String fileName1, String fileName2) {
                File file1 = new File(directory, fileName1);
                File file2 = new File(directory, fileName2);
                int result = 0;
                if (file1.exists() && file2.exists()) {
                    Long fileLastTime1 = file1.lastModified();
                    Long fileLastTime2 = file2.lastModified();
                    result = fileLastTime1.compareTo(fileLastTime2);
                    result = sort * result;
                }
                return result;
            }
        });
    }

    /* ################流操作#################### */

    /**
     * 关闭流
     * @param streams
     */
    public static void close(Closeable... streams) {
        for (Closeable stream : streams) {
            try {
                if (null != stream) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
