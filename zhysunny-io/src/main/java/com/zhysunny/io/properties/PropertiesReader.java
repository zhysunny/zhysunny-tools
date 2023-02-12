package com.zhysunny.io.properties;

import com.zhysunny.io.BaseReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Properties文件读取类
 * @author 章云
 * @date 2019/7/27 11:02
 */
public class PropertiesReader extends BaseReader {

    private Properties props;

    public PropertiesReader(Object... resources) {
        super(resources);
    }

    public PropertiesReader(List<Object> resources) {
        super(resources);
    }

    public PropertiesReader(Properties props) {
        this.props = props;
    }

    @Override
    public PropertiesReader builder() throws Exception {
        try {
            props = new Properties();
            for (Object resource : resources) {
                if (resource instanceof URL) {
                    URL url = (URL)resource;
                    props.load(url.openStream());
                } else if (resource instanceof File) {
                    File file = (File)resource;
                    props.load(new FileInputStream(file));
                } else if (resource instanceof String) {
                    File file = new File((String)resource);
                    URL url = Thread.currentThread().getContextClassLoader().getResource((String)resource);
                    if (url != null) {
                        props.load(url.openStream());
                    } else {
                        props.load(new FileInputStream(file));
                    }
                } else if (resource instanceof InputStream) {
                    InputStream is = (InputStream)resource;
                    props.load(is);
                } else {
                    throw new RuntimeException("不支持的资源配置类型：" + resource.getClass());
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        translate();
        return this;
    }

    private static final Pattern PATTERN = Pattern.compile("\\$\\{([^\\}]+)\\}");

    public PropertiesReader translate() {
        Matcher matcher = null;
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            String value = entry.getValue().toString();
            matcher = PATTERN.matcher(value);
            StringBuffer buffer = new StringBuffer();
            while (matcher.find()) {
                String matcherKey = matcher.group();
                matcherKey = matcherKey.substring(2, matcherKey.length() - 1);
                String matchervalue = props.getProperty(matcherKey);
                if (matchervalue != null) {
                    matcher.appendReplacement(buffer, matchervalue);
                }
            }
            matcher.appendTail(buffer);
            props.setProperty(entry.getKey().toString(), buffer.toString());
        }
        return this;
    }

    public Properties getProps() {
        return props;
    }

    public <T> T toConstant(Class<T> clz) throws Exception {
        if (props == null) {
            builder();
        }
        if (!PropertiesConstant.class.isAssignableFrom(clz)) {
            throw new Exception(clz.getSimpleName() + " 没有实现 " + PropertiesConstant.class);
        }
        T t = clz.newInstance();
        Field[] fields = clz.getFields();
        for (Field field : fields) {
            field.setAccessible(true);
            PropKey propKey = field.getAnnotation(PropKey.class);
            if (propKey != null) {
                String key = propKey.key();
                String value = props.getProperty(key);
                if (value == null) {
                    // 使用默认值
                    value = propKey.defaultValue();
                    if (value == null || "".equals(value)) {
                        continue;
                    }
                }
                if (propKey.classpath().length() > 0) {
                    AbstractTypeConversion typeConversion = (AbstractTypeConversion)Class.forName(propKey.classpath())
                    .getConstructor(String.class).newInstance(propKey.param());
                    field.set(t, typeConversion.conversion(value));
                } else if (field.getType() == String.class) {
                    field.set(t, value);
                } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
                    field.setBoolean(t, Boolean.parseBoolean(value));
                } else if (field.getType() == Byte.class || field.getType() == byte.class) {
                    field.setByte(t, Byte.parseByte(value));
                } else if (field.getType() == Short.class || field.getType() == short.class) {
                    field.setShort(t, Short.parseShort(value));
                } else if (field.getType() == Integer.class || field.getType() == int.class) {
                    field.setInt(t, Integer.parseInt(value));
                } else if (field.getType() == Long.class || field.getType() == long.class) {
                    field.setLong(t, Long.parseLong(value));
                } else if (field.getType() == Float.class || field.getType() == float.class) {
                    field.setFloat(t, Float.parseFloat(value));
                } else if (field.getType() == Double.class || field.getType() == double.class) {
                    field.setDouble(t, Double.parseDouble(value));
                } else if (field.getType() == Character.class || field.getType() == char.class) {
                    field.setChar(t, value.charAt(0));
                } else {
                    throw new Exception("支持八大基本数据类型和String类型的字段映射,或者继承TypeConversion并设置classpath参数");
                }
            }
        }
        return t;
    }

}
