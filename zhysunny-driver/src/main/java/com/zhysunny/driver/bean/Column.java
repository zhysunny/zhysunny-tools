package com.zhysunny.driver.bean;

import com.zhysunny.driver.FieldType;
import com.zhysunny.driver.JdbcConnection;
import com.zhysunny.driver.mysql.FieldTypeMysql;
import com.zhysunny.driver.oracle.FieldTypeOracle;
import com.zhysunny.driver.util.Constants;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Locale;

/**
 * 字段
 * @author 章云
 * @date 2019/8/16 21:50
 */
@XmlRootElement(name = "Column")
@XmlAccessorType(XmlAccessType.FIELD)
public class Column {

    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "type")
    private String type;
    @XmlAttribute(name = "length")
    private String length;
    @XmlAttribute(name = "notNull")
    private String notNull;
    @XmlAttribute(name = "primaryKey")
    private String primaryKey;
    @XmlAttribute(name = "comment")
    private String comment;

    private Field field;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return field.getName();
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLength() {
        if (this.length == null || this.length.trim().length() == 0) {
            return field.getLength();
        }
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public boolean getNotNull() {
        if ("1".equals(notNull) || "true".equalsIgnoreCase(notNull) || getPrimaryKey()) {
            return true;
        }
        return false;
    }

    public void setNotNull(String notNull) {
        this.notNull = notNull;
    }

    public boolean getPrimaryKey() {
        if ("1".equals(primaryKey) || "true".equalsIgnoreCase(primaryKey)) {
            return true;
        }
        return false;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setField(JdbcConnection conn) throws Exception {
        if (this.type != null) {
            FieldType fieldType = null;
            if (Constants.Driver.ORACLE.getName().equalsIgnoreCase(conn.getThisDriver())) {
                fieldType = new FieldTypeOracle();
            } else if (Constants.Driver.MYSQL.getName().equalsIgnoreCase(conn.getThisDriver())) {
                fieldType = new FieldTypeMysql();
            } else {
                throw new Exception("未知的驱动：" + conn.getThisDriver());
            }
            if (this.type.toUpperCase(Locale.ENGLISH).startsWith(Constants.VARCHAR) || this.type.equalsIgnoreCase(Constants.STRING)) {
                this.field = fieldType.getStringField();
            } else if (this.type.equalsIgnoreCase(Constants.BYTE) || this.type.equalsIgnoreCase(Constants.TINYINT)) {
                this.field = fieldType.getByteField();
            } else if (this.type.equalsIgnoreCase(Constants.SHORT) || this.type.equalsIgnoreCase(Constants.SMALLINT)) {
                this.field = fieldType.getShortField();
            } else if (this.type.equalsIgnoreCase(Constants.INTEGER) || this.type.equalsIgnoreCase(Constants.INT)) {
                this.field = fieldType.getIntegerField();
            } else if (this.type.equalsIgnoreCase(Constants.LONG) || this.type.equalsIgnoreCase(Constants.BIGINT)) {
                this.field = fieldType.getLongField();
            } else if (this.type.equalsIgnoreCase(Constants.FLOAT)) {
                this.field = fieldType.getFloatField();
            } else if (this.type.equalsIgnoreCase(Constants.DOUBLE)) {
                this.field = fieldType.getDoubleField();
            } else if (this.type.equalsIgnoreCase(Constants.NUMBER)) {
                this.field = fieldType.getNumberField();
            } else if (this.type.equalsIgnoreCase(Constants.CHAR)) {
                this.field = fieldType.getCharField();
            } else if (this.type.equalsIgnoreCase(Constants.DATE)) {
                this.field = fieldType.getDateField();
            } else if (this.type.equalsIgnoreCase(Constants.TIME)) {
                this.field = fieldType.getTimeField();
            } else if (this.type.equalsIgnoreCase(Constants.DATETIME)) {
                this.field = fieldType.getDatetimeField();
            } else {
                // 字符串是默认类型
                this.field = fieldType.getStringField();
            }
        }
    }

    /**
     * Column复制给JdbcDataBaseColumn
     * @return
     */
    public JdbcDataBaseColumn copy() {
        JdbcDataBaseColumn dbColumn = new JdbcDataBaseColumn(this.getName(), this.getType());
        dbColumn.setLength(this.getLength());
        dbColumn.setNotNull(this.getNotNull());
        dbColumn.setPrimaryKey(this.getPrimaryKey());
        return dbColumn;
    }

}
