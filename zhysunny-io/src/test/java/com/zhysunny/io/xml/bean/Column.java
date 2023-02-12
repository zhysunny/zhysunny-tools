package com.zhysunny.io.xml.bean;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Column")
@XmlAccessorType(XmlAccessType.FIELD)
public class Column {
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "key")
    private String key;
    @XmlAttribute(name = "value")
    private String value;
    @XmlAttribute(name = "notNull")
    private Boolean notNull = false;
    @XmlAttribute(name = "regexName")
    private String regexName;
    @XmlAttribute(name = "groupName")
    private String groupName;
    @XmlAttribute(name = "ref")
    private String ref;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getNotNull() {
        return notNull;
    }

    public void setNotNull(String notNull) {
        this.notNull = Boolean.parseBoolean(notNull);
    }

    public String getRegexName() {
        return regexName;
    }

    public void setRegexName(String regexName) {
        this.regexName = regexName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setNotNull(Boolean notNull) {
        this.notNull = notNull;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", notNull=" + notNull +
                ", regexName='" + regexName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", ref='" + ref + '\'' +
                '}';
    }
}
