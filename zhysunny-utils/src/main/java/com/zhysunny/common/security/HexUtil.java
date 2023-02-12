package com.zhysunny.common.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 章云
 * @date 2019/9/18 19:00
 */
public class HexUtil {

    private HexUtil(){}

    private static final String HEX_CHARS = "0123456789abcdef";

    protected static final BigInteger PRIVATE_D = new BigInteger("3206586642942415709865087389521403230384599658161226562177807849299468150139");
    protected static final BigInteger N = new BigInteger("7318321375709168120463791861978437703461807315898125152257493378072925281977");

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_CHARS.charAt(b[i] >>> 4 & 0x0F));
            sb.append(HEX_CHARS.charAt(b[i] & 0x0F));
        }
        return sb.toString();
    }

    public static byte[] toByteArray(String s) {
        byte[] buf = new byte[s.length() / 2];
        int j = 0;
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte)((Character.digit(s.charAt(j++), 16) << 4) | Character.digit(s.charAt(j++), 16));
        }
        return buf;
    }

    public static byte[] md5(byte[] data) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("MD5").digest(data);
    }

    public static byte[] md5(String data, String charset) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (charset == null) {
            return md5(data.getBytes());
        } else {
            return md5(data.getBytes(charset));
        }
    }
}
