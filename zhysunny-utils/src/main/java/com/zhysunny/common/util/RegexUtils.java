package com.zhysunny.common.util;

import java.util.regex.Pattern;

/**
 * 正则库
 * @author 章云
 * @date 2019/9/18 18:09
 */
public class RegexUtils {

    private RegexUtils() {}

    /********************************* 数字类 ********************************/

    /**
     * 身份证号码(15位和18位)
     */
    public static final String IDCARD_REGEX = "([1-9]\\d{7}((0\\d)|(1[0-2]))(([0-2]\\d)|(3[01]))\\d{3})|([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0-2]\\d)|(3[01]))\\d{3}[0-9xX])";

    /**
     * 手机号码
     */
    public static final String MOBILE_PHONE_REGEX = "1((3[0-9])|(4[57])|([5789][0-9]))\\d{8}";

    /**
     * ip地址
     */
    public static final String IP_ADDRESS_REGEX = "\\d{1,3}(\\.\\d{1,3}){3}";

    /**
     * 金额(正数位必须有值)
     */
    public static final String MONEY_REGEX = "(-)?[\\d]+[\\d,]*(\\.\\d+)?";

    /********************************* 车辆 ********************************/

    /**
     * 车牌
     */
    public static final String CARNUM_REGEX = "[\\u4EAC|\\u6D25|\\u6CAA|\\u6E1D|\\u5180|\\u8C6B|\\u4E91|\\u8FBD|\\u9ED1|\\u6E58|\\u7696|\\u95FD|\\u9C81|\\u65B0|\\u82CF|\\u6D59|\\u8D63|\\u9102|\\u6842|\\u7518|\\u664B|\\u8499|\\u9655|\\u5409|\\u8D35|\\u7CA4|\\u9752|\\u85CF|\\u5DDD|\\u5B81|\\u743C][A-Z][A-Z0-9]{5}";

    /**
     * Email正则表达式
     */
    public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 字符串完全匹配
     * @param value 需要匹配的值
     * @param regex 正则
     * @return
     */
    public static boolean matches(String value, String regex) {
        return value != null && regex != null && value.matches(regex);
    }

    /**
     * 字符串是否包含
     * @param value 需要匹配的值
     * @param regex 正则
     * @return
     */
    public static boolean contains(String value, String regex) {
        return value != null && regex != null && Pattern.compile(regex).matcher(value).find();
    }

}
