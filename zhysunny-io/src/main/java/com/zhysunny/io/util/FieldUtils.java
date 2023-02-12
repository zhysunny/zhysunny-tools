package com.zhysunny.io.util;

import java.lang.reflect.Field;

/**
 * 针对反射Field类的操作
 * @author 章云
 * @date 2019/8/27 9:13
 */
public class FieldUtils {

    public static String getValue(Field field, Object obj) throws Exception {
        String value = null;
        if (field.getType() == String.class) {
            value = (String) field.get(obj);
        } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
            value = String.valueOf(field.getBoolean(obj));
        } else if (field.getType() == Byte.class || field.getType() == byte.class) {
            value = String.valueOf(field.getByte(obj));
        } else if (field.getType() == Short.class || field.getType() == short.class) {
            value = String.valueOf(field.getShort(obj));
        } else if (field.getType() == Integer.class || field.getType() == int.class) {
            value = String.valueOf(field.getInt(obj));
        } else if (field.getType() == Long.class || field.getType() == long.class) {
            value = String.valueOf(field.getLong(obj));
        } else if (field.getType() == Float.class || field.getType() == float.class) {
            value = String.valueOf(field.getFloat(obj));
        } else if (field.getType() == Double.class || field.getType() == double.class) {
            value = String.valueOf(field.getDouble(obj));
        } else if (field.getType() == Character.class || field.getType() == char.class) {
            value = String.valueOf(field.getChar(obj));
        } else {
            throw new Exception("只支持八大基本数据类型和String类型的字段映射");
        }
        return value;
    }

    public static void setValue(Field field, String value, Object obj) throws Exception {
        if (field.getType() == String.class) {
            field.set(obj, value);
        } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
            field.setBoolean(obj, Boolean.parseBoolean(value));
        } else if (field.getType() == Byte.class || field.getType() == byte.class) {
            field.setByte(obj, Byte.parseByte(value));
        } else if (field.getType() == Short.class || field.getType() == short.class) {
            field.setShort(obj, Short.parseShort(value));
        } else if (field.getType() == Integer.class || field.getType() == int.class) {
            field.setInt(obj, Integer.parseInt(value));
        } else if (field.getType() == Long.class || field.getType() == long.class) {
            field.setLong(obj, Long.parseLong(value));
        } else if (field.getType() == Float.class || field.getType() == float.class) {
            field.setFloat(obj, Float.parseFloat(value));
        } else if (field.getType() == Double.class || field.getType() == double.class) {
            field.setDouble(obj, Double.parseDouble(value));
        } else if (field.getType() == Character.class || field.getType() == char.class) {
            field.setChar(obj, value.charAt(0));
        } else {
            throw new Exception("只支持八大基本数据类型和String类型的字段映射");
        }
    }

}
