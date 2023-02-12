package com.zhysunny.io.properties.constant;

import com.zhysunny.io.properties.PropKey;
import com.zhysunny.io.properties.PropertiesConstant;

public class Constant implements PropertiesConstant {

    @PropKey(key = "value")
    public static String value;

    @PropKey(key = "value1")
    public static boolean value1;

    @PropKey(key = "value2")
    public static int value2;

    @PropKey(key = "array", classpath = "com.zhysunny.io.properties.impl.StringArrayTypeConversion")
    public static String[] array;

    @PropKey(key = "address")
    public static String address;

}
