package com.zhysunny.io.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Topic")
@XmlAccessorType(XmlAccessType.FIELD)
public class Topic {
    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "Filter")
    private Filter filter;
    @XmlElement(name = "Regex")
    private Regex regex;
    @XmlElement(name = "Translate")
    private Translate translate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Regex getRegex() {
        return regex;
    }

    public void setRegex(Regex regex) {
        this.regex = regex;
    }

    public Translate getTranslate() {
        return translate;
    }

    public void setTranslate(Translate translate) {
        this.translate = translate;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "name='" + name + '\'' +
                ", filter=" + filter +
                ", regex=" + regex +
                ", translate=" + translate +
                '}';
    }
}
