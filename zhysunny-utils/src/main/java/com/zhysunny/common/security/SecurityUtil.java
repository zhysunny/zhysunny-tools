package com.zhysunny.common.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

/**
 * @author 章云
 * @date 2019/9/18 19:03
 */
public final class SecurityUtil {

    private SecurityUtil() {}

    private static Random random = new Random();

    private static long mhiTag;
    private static long mloTag;

    private static String toString(byte[] bytes) {
        if (16 != bytes.length) {
            return "** Bad UUID Format/Value **";
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            buf.append(String.format("%02X", (byte)(bytes[i] >> 4)));
            buf.append(String.format("%02X", (byte)(bytes[i] & 0xf)));
        }
        return buf.toString();
    }

    private static byte[] toByteArray() {
        byte[] bytes = new byte[16];
        int idx = 15;
        long val = mloTag;
        for (int i = 0; i < 8; i++) {
            bytes[idx--] = (byte)(int)(val & (long)255);
            val >>= 8;
        }
        val = mhiTag;
        for (int i = 0; i < 8; i++) {
            bytes[idx--] = (byte)(int)(val & (long)255);
            val >>= 8;
        }
        return bytes;
    }

    private static long getMachineID() {
        long i = 0;
        try {
            InetAddress inetaddress = InetAddress.getLocalHost();
            byte[] abyte0 = inetaddress.getAddress();
            i = ((abyte0[0] << 24) & 0xff000000) | ((abyte0[1] << 16) & 0xff0000) | ((abyte0[2] << 8) & 0xff00) | (abyte0[3] & 0xff);
        } catch (UnknownHostException e) {
        }
        return i;
    }

    /**
     * 获取uuid字符
     * @return
     */
    private static String getUUID() {
        long machineid = getMachineID();
        Random random = new Random(System.currentTimeMillis());
        mhiTag = (System.currentTimeMillis() + (Integer.MIN_VALUE * 4294967296L)) ^ machineid;
        mloTag = System.currentTimeMillis() + random.nextLong();
        return toString(toByteArray());
    }

    /**
     * 解析前台传送的加密字符
     * @param str
     * @return
     */
    public static String getDecryptLoginPassword(String str) {
        byte[] ptext = HexUtil.toByteArray(str);
        BigInteger encry = new BigInteger(ptext);
        BigInteger variable = encry.modPow(HexUtil.PRIVATE_D, HexUtil.N);
        // 计算明文对应的字符串
        byte[] mt = variable.toByteArray();
        StringBuilder buffer = new StringBuilder();
        for (int i = mt.length - 1; i > -1; i--) {
            buffer.append((char)mt[i]);
        }
        return buffer.substring(0, buffer.length() - 10);
    }

    /**
     * 生成密码安全码
     * @return
     * @see
     */
    public static String getNewPsw() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String s1 = Md5Util.md5Hex(String.valueOf(System.currentTimeMillis()));
        String s2 = getUUID();
        return s1 + s2;
    }

    /**
     * 生成加密后的密码
     * @param usercode
     * @param logpwd
     * @param psw
     * @return
     */
    public static String getStoreLogpwd(String usercode, String logpwd, String psw)
    throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return Md5Util.md5Hex(usercode + Md5Util.md5Hex(logpwd) + psw);
    }

    /**
     * 生成随机的MD5密文
     * @return
     */
    public static String getNewToken() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return Md5Util.md5Hex(UUID.randomUUID().toString());
    }

    /**
     * 生成签名
     * @param srcStr
     * @return
     */
    public static String getDigest(String srcStr) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return Md5Util.md5Hex(Md5Util.md5Hex(srcStr) + (srcStr.hashCode() + srcStr.length()) + "19880322");
    }

    /**
     * 生成指定长度的验证码
     * @param len
     * @return
     */
    public static String getVerifyCode(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

}
