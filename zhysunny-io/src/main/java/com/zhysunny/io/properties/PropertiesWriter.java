package com.zhysunny.io.properties;

import com.zhysunny.io.BaseWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Properties文件输出类
 * @author 章云
 * @date 2019/7/27 14:08
 */
public class PropertiesWriter extends BaseWriter {

    public PropertiesWriter(String path) {
        super(path);
    }

    public PropertiesWriter(File file) {
        super(file);
    }

    /**
     * 默认不追加写入
     * @param props
     * @param comment
     * @throws Exception
     */
    public void write(Properties props, String comment) throws Exception {
        write(props, comment, false);
    }

    /**
     * 写入Properties文件
     * @param props
     * @param comment
     * @param append
     * @throws Exception
     */
    public void write(Properties props, String comment, boolean append) throws Exception {
        FileOutputStream fos = new FileOutputStream(file, append);
        props.store(fos, comment);
        fos.close();
    }

    /**
     * 默认不追加写入
     * @param clz
     * @param comment
     * @throws Exception
     */
    public void write(Class<? extends PropertiesConstant> clz, String comment) throws Exception {
        write(clz, comment, false);
    }

    /**
     * 将常量类写入文件
     * @param clz
     * @param comment
     * @param append
     * @throws Exception
     */
    public void write(Class<? extends PropertiesConstant> clz, String comment, boolean append) throws Exception {
        Field[] fields = clz.getFields();
        Properties prop = new Properties();
        for (Field field : fields) {
            field.setAccessible(true);
            PropKey propKey = field.getAnnotation(PropKey.class);
            if (propKey != null) {
                String key = propKey.key();
                String value;
                if (propKey.classpath().length() > 0) {
                    AbstractTypeConversion typeConversion = (AbstractTypeConversion)Class.forName(propKey.classpath())
                    .getConstructor(String.class).newInstance(propKey.param());
                    value = typeConversion.toString(field.get(clz));
                } else if (field.getType() == String.class) {
                    value = (String)field.get(clz);
                } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
                    value = String.valueOf(field.getBoolean(clz));
                } else if (field.getType() == Byte.class || field.getType() == byte.class) {
                    value = String.valueOf(field.getByte(clz));
                } else if (field.getType() == Short.class || field.getType() == short.class) {
                    value = String.valueOf(field.getShort(clz));
                } else if (field.getType() == Integer.class || field.getType() == int.class) {
                    value = String.valueOf(field.getInt(clz));
                } else if (field.getType() == Long.class || field.getType() == long.class) {
                    value = String.valueOf(field.getLong(clz));
                } else if (field.getType() == Float.class || field.getType() == float.class) {
                    value = String.valueOf(field.getFloat(clz));
                } else if (field.getType() == Double.class || field.getType() == double.class) {
                    value = String.valueOf(field.getDouble(clz));
                } else if (field.getType() == Character.class || field.getType() == char.class) {
                    value = String.valueOf(field.getChar(clz));
                } else {
                    throw new Exception("只支持八大基本数据类型和String类型的字段映射");
                }
                if (value == null) {
                    // 使用默认值
                    value = propKey.defaultValue();
                }
                prop.put(key, value);
            }
        }
        FileOutputStream fos = new FileOutputStream(file, append);
        prop.store(fos, comment);
        fos.close();
    }

}
