package com.zhysunny.tools.kafka;

import kafka.admin.AdminUtils;
import kafka.admin.TopicCommand;
import kafka.utils.ZkUtils;
import scala.collection.JavaConversions;
import scala.collection.Seq;
import java.util.List;
import java.util.Properties;

/**
 * kafka-topics.sh脚本，针对topic的增删改查
 * @author 章云
 * @date 2019/10/18 10:03
 */
public class KafkaTopic {

    private ZkUtils zkUtils;

    public KafkaTopic(ZkUtils zkUtils) {
        this.zkUtils = zkUtils;
    }

    /**
     * 获取所有topic
     * @return
     */
    public List<String> listTopic() {
        Seq<String> allTopics = zkUtils.getAllTopics();
        return JavaConversions.seqAsJavaList(allTopics);
    }

    /**
     * 判断topic是否存在
     * @param topicName
     * @return
     */
    public boolean existTopic(String topicName) {
        return AdminUtils.topicExists(zkUtils, topicName);
    }

    /**
     * 创建topic
     * @param topicName  topic名
     * @param partitions 分区数
     * @param replicas   副本数
     */
    public void createTopic(String topicName, int partitions, int replicas) {
        Properties configs = new Properties();
        TopicCommand.warnOnMaxMessagesChange(configs, replicas);
        AdminUtils.createTopic(zkUtils, topicName, partitions, replicas, configs);
    }

    /**
     * 修改topic分区数，分区数只能增加不能减少
     * @param topicName
     * @param partitions
     */
    public void alertTopic(String topicName, int partitions) {
        AdminUtils.addPartitions(zkUtils, topicName, partitions, "", true);
    }

    /**
     * 获取topic信息
     * @param topicName
     */
    public void describeTopic(String topicName) {
        String[] args = new String[]{ "describe", "--topic", topicName };
        TopicCommand.TopicCommandOptions opts = new TopicCommand.TopicCommandOptions(args);
        TopicCommand.describeTopic(zkUtils, opts);
    }

    /**
     * 删除topic
     * @param topicName
     */
    public void deleteTopic(String topicName) {
        String[] args = new String[]{ "delete", "--topic", topicName };
        TopicCommand.TopicCommandOptions opts = new TopicCommand.TopicCommandOptions(args);
        TopicCommand.deleteTopic(zkUtils, opts);
    }

}
