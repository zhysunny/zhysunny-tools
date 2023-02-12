package com.zhysunny.io.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * 字符串工具
 * @author 章云
 * @date 2019/7/27 11:16
 */
public class StringUtils {

    /**
     * 数组转String
     * @param array
     * @param separator
     * @return
     */
    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        } else {
            StringBuffer buf = new StringBuffer(64);
            for (int i = 0; i < array.length; i++) {
                if (i > 0) {
                    buf.append(separator);
                }
                if (array[i] == null) {
                    array[i] = "";
                }
                buf.append(array[i]);
            }
            return buf.toString();
        }
    }

    /**
     * 集合转String
     * @param collection
     * @param separator
     * @return
     */
    public static String join(Collection collection, String separator) {
        if (collection == null) {
            return null;
        } else {
            StringBuffer buf = new StringBuffer(64);
            Iterator iterator = collection.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                if (i > 0) {
                    buf.append(separator);
                }
                Object next = iterator.next();
                if (next == null) {
                    next = "";
                }
                buf.append(next);
                i++;
            }
            return buf.toString();
        }
    }

}
