package com.zhysunny.io.xml.bean;

import com.zhysunny.io.xml.XmlBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Topics")
@XmlAccessorType(XmlAccessType.FIELD)
public class Topics implements XmlBean {

    @XmlElement(name = "Topic")
    private List<Topic> topics;

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return "Topics [topics=" + topics + "]";
    }
}
