package com.zhysunny.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * 参数校验工具类
 * @author 章云
 * @date 2019/7/27 15:11
 */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * 判断传入参数是否是byte
     * @param str
     * @return
     */
    public static boolean isByte(String str) {
        try {
            byte i = Byte.parseByte(str);
            return i >= Byte.MIN_VALUE && i <= Byte.MAX_VALUE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断传入参数是否是短整型
     * @param str
     * @return
     */
    public static boolean isShort(String str) {
        try {
            short i = Short.parseShort(str);
            return i >= Short.MIN_VALUE && i <= Short.MAX_VALUE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断传入参数是否是整型
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        try {
            int i = Integer.parseInt(str);
            return i >= Integer.MIN_VALUE && i <= Integer.MAX_VALUE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断传入参数是否是长整型
     * @param str
     * @return
     */
    public static boolean isLong(String str) {
        try {
            long i = Long.parseLong(str);
            return i >= Long.MIN_VALUE && i <= Long.MAX_VALUE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断传入参数是否是单精度浮点
     * @param str
     * @return
     */
    public static boolean isFloat(String str) {
        try {
            float i = Float.parseFloat(str);
            return i >= Float.MIN_VALUE && i <= Float.MAX_VALUE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断传入参数是否是双精度浮点
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        try {
            double i = Double.parseDouble(str);
            return i >= Double.MIN_VALUE && i <= Double.MAX_VALUE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断传入参数是否是布尔值(忽略大小写)
     * @param str
     * @return
     */
    public static boolean isBoolean(String str) {
        return "true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str);
    }

    /**
     * 判断传入参数是否是身份证号码
     * @param str
     * @return
     */
    public static boolean isIdcard(String str) {
        return RegexUtils.matches(str, RegexUtils.IDCARD_REGEX);
    }

    /**
     * 判断传入参数是否是手机号码
     * @param str
     * @return
     */
    public static boolean isMobilePhone(String str) {
        return RegexUtils.matches(str, RegexUtils.MOBILE_PHONE_REGEX);
    }

    /**
     * 判断传入参数是否是IP地址
     * @param str
     * @return
     */
    public static boolean isIpAddress(String str) {
        return RegexUtils.matches(str, RegexUtils.IP_ADDRESS_REGEX);
    }

    /**
     * 判断传入参数是否是金额
     * @param str
     * @return
     */
    public static boolean isMoney(String str) {
        return RegexUtils.matches(str, RegexUtils.MONEY_REGEX);
    }

    /**
     * 判断传入参数是否是车牌号
     * @param str
     * @return
     */
    public static boolean isCarNum(String str) {
        return RegexUtils.matches(str, RegexUtils.CARNUM_REGEX);
    }

    /**
     * 判断传入参数是否是邮箱
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        return RegexUtils.matches(str, RegexUtils.EMAIL);
    }

    /**
     * 判断传入参数是否包含身份证号码
     * @param str
     * @return
     */
    public static boolean containsIdcard(String str) {
        return RegexUtils.contains(str, RegexUtils.IDCARD_REGEX);
    }

    /**
     * 判断传入参数是否包含手机号码
     * @param str
     * @return
     */
    public static boolean containsMobilePhone(String str) {
        return RegexUtils.contains(str, RegexUtils.MOBILE_PHONE_REGEX);
    }

    /**
     * 判断传入参数是否包含IP地址
     * @param str
     * @return
     */
    public static boolean containsIpAddress(String str) {
        return RegexUtils.contains(str, RegexUtils.IP_ADDRESS_REGEX);
    }

    /**
     * 判断传入参数是否包含金额
     * @param str
     * @return
     */
    public static boolean containsMoney(String str) {
        return RegexUtils.contains(str, RegexUtils.MONEY_REGEX);
    }

    /**
     * 判断传入参数是否包含车牌号
     * @param str
     * @return
     */
    public static boolean containsCarNum(String str) {
        return RegexUtils.contains(str, RegexUtils.CARNUM_REGEX);
    }

    /**
     * 判断传入参数是否包含邮箱
     * @param str
     * @return
     */
    public static boolean containsEmail(String str) {
        return RegexUtils.contains(str, RegexUtils.EMAIL);
    }

    /**
     * 有一个参数为空返回true
     * 都不为空返回false
     * @param params
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmptyParamOne(final Object... params) {
        if (null == params || 0 == params.length) {
            return true;
        }
        for (Object param : params) {
            if (param == null) {
                return true;
            } else {
                if (param instanceof String && "".equals(param)) {
                    return true;
                } else if (param instanceof Object[] && (((Object[])param).length == 0)) {
                    return true;
                } else if (param instanceof Collection && ((Collection)param).isEmpty()) {
                    return true;
                } else if (param instanceof Map && ((Map)param).isEmpty()) {
                    return true;
                } else if (param instanceof byte[] && (((byte[])param).length == 0)) {
                    return true;
                } else {
                    throw new RuntimeException("无法判空的对象" + param.getClass());
                }
            }
        }
        return false;
    }

    /**
     * 参数全部为空返回true
     * 有一个不为空返回false
     * @param params
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmptyParamAll(final Object... params) {
        if (null == params || 0 == params.length) {
            return true;
        }
        for (Object param : params) {
            if (param != null) {
                if (param instanceof String && !"".equals(param)) {
                    return false;
                } else if (param instanceof Object[] && (((Object[])param).length != 0)) {
                    return false;
                } else if (param instanceof Collection && !((Collection)param).isEmpty()) {
                    return false;
                } else if (param instanceof Map && !((Map)param).isEmpty()) {
                    return false;
                } else if (param instanceof byte[] && (((byte[])param).length != 0)) {
                    return false;
                } else {
                    throw new RuntimeException("无法判空的对象" + param.getClass());
                }
            }
        }
        return true;
    }

}