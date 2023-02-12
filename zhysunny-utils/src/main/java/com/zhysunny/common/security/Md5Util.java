package com.zhysunny.common.security;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    /**
     * 指定编码加密
     * @param data
     * @return
     */
    public static String md5Hex(byte[] data) throws NoSuchAlgorithmException {
        return HexUtil.toHexString(HexUtil.md5(data));
    }

    /**
     * 指定编码加密
     * @param data
     * @return
     */
    public static String md5Hex(String data) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return HexUtil.toHexString(HexUtil.md5(data, null));
    }

    /**
     * 指定编码加密
     * @param data
     * @param charset
     * @return
     */
    public static String md5Hex(String data, String charset) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return HexUtil.toHexString(HexUtil.md5(data, charset));
    }

    /**
     * 解密登录密码
     * @param str
     * @return
     */
    public static String getDecryptLoginPassword(String str) {
        byte[] ptext = HexUtil.toByteArray(str);
        BigInteger encryC = new BigInteger(ptext);
        BigInteger variable = encryC.modPow(HexUtil.PRIVATE_D, HexUtil.N);
        // 计算明文对应的字符串
        byte[] mt = variable.toByteArray();
        StringBuffer buffer = new StringBuffer();
        for (int i = mt.length - 1; i > -1; i--) {
            buffer.append((char) mt[i]);
        }
        return buffer.substring(0, buffer.length() - 10);
    }

    /**
     * 对一个文件获取md5值
     * @return md5串
     */
    public static String getFileMD5(File file) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
            return null;
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(md5.digest()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成二进制文件查询的md5值
     * @return md5串
     */
    public static String setFileMD5(File file) {
        MessageDigest md5;
        int filelimit = 2 * 1024;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
            return null;
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            long filelength = file.length();
            if (filelength > filelimit) {
                length = fileInputStream.read(buffer);
                md5.update(buffer);
                length = fileInputStream.read(buffer);
                md5.update(buffer);
            } else {
                while ((length = fileInputStream.read(buffer)) != -1) {
                    md5.update(buffer, 0, length);
                }
            }
            byte[] b = String.valueOf(filelength).getBytes("UTF-8");
            md5.update(b);
            return new String(Hex.encodeHex(md5.digest()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成二进制文件查询的md5值
     * @return md5串
     */
    public static String setFileMD5(InputStream fis) {
        MessageDigest MD5;
        int filelimit = 2 * 1024;
        try {
            MD5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
            return null;
        }
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            long filelength = fis.available();
            if (filelength > filelimit) {
                length = fis.read(buffer);
                baos.write(buffer);
                length = fis.read(buffer);
                System.out.println("length=" + length);
                baos.write(buffer);
                byte[] b = String.valueOf(filelength).getBytes("UTF-8");
                baos.write(b);
            } else {
                while ((length = fis.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }
            }
            MD5.update(baos.toByteArray());
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 求一个字符串的md5值
     * @param target 字符串
     * @return md5 value
     */
    public static String getStringMD5(String target) {
        return DigestUtils.md5Hex(target);
    }
}
