package com.zhysunny.driver.bean;

import java.util.Locale;

/**
 * Description: 表字段类
 * @author 章云
 * @date 2018年6月23日 上午11:07:02
 */
public class JdbcDataBaseColumn {
    // 字段名
    private String name;
    // 字段类型
    private String type;
    // 字段长度
    private String length;
    // 字段是否必填
    private Boolean notNull = false;
    // 字段是否是主键
    private Boolean primaryKey = false;
    // 字段修改从必填到非必填时为true
    private Boolean modifyNull = false;

    public JdbcDataBaseColumn(String name, String type) {
        super();
        this.name = name;
        this.type = type;
    }

    public JdbcDataBaseColumn(String name, String type, String length, Boolean notNull) {
        super();
        this.name = name;
        this.type = type;
        this.length = length;
        this.notNull = notNull;
    }

    /**
     * 设置浮点数(默认两位小数)
     * @param isFloat
     * @return
     */
    public JdbcDataBaseColumn setIsFloat(boolean isFloat) {
        if (!"0".equals(length)) {
            this.length = length + ",2";
        }
        return this;
    }

    /**
     * 设置浮点数
     * @param isFloat
     * @param accuracy 小数位数
     * @return
     */
    public JdbcDataBaseColumn setIsFloat(boolean isFloat, int accuracy) {
        if (!"0".equals(length)) {
            this.length = length + "," + accuracy;
        }
        return this;
    }

    public String getName() {
        return name.toUpperCase(Locale.ENGLISH);
    }

    public void setName(String name) {
        this.name = name.toUpperCase(Locale.ENGLISH);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Boolean getNotNull() {
        return notNull;
    }

    public void setNotNull(Boolean notNull) {
        this.notNull = notNull;
    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public String toString() {
        return "JdbcDataBaseColumn [name=" + name + ", type=" + type + ", length=" + length + ", notNull=" + notNull
                + ", primaryKey=" + primaryKey + "]";
    }

    public Boolean getModifyNull() {
        return modifyNull;
    }

    public void setModifyNull(Boolean modifyNull) {
        this.modifyNull = modifyNull;
    }

}
