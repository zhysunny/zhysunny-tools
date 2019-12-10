package com.zhysunny.tools.kafka.performance;

import com.zhysunny.common.util.UnitUtils;
import com.zhysunny.io.conf.Configuration;
import com.zhysunny.tools.kafka.KafkaTopic;
import com.zhysunny.tools.kafka.constant.Constants;
import kafka.utils.ZkUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.security.JaasUtils;
import java.util.Arrays;
import static java.util.stream.Collectors.*;

/**
 * 生产者性能测试接口
 * @author 章云
 * @date 2019/12/10 14:33
 */
public class ProducerPerformanceImpl implements Performance {

    @Override
    public void prepare(Configuration conf) {
        String[] hosts = conf.getStrings(Constants.SERVERS_HOSTS);
        // 拼接 bootstrap.servers
        final String kafkaPort = conf.getString(Constants.KAFKA_SERVER_PORT, "9092");
        conf.setString(Constants.BOOTSTRAP_SERVERS,
        StringUtils.join(
        Arrays.stream(hosts).map(host -> new StringBuilder(host).append(':').append(kafkaPort)).collect(toList()),
        ','));
        // 消息大小
        long size = (long)UnitUtils.getSize(conf.getString(Constants.RECORD_SIZE, "1KB"));
        conf.setLong(Constants.RECORD_SIZE, size);
        // 创建或修改topic分区副本
        final String zkPort = conf.getString(Constants.ZOOKEEPER_SERVER_PORT, "2181");
        String zookeeperConnect = StringUtils.join(
        Arrays.stream(hosts).map(host -> new StringBuilder(host).append(':').append(zkPort)).collect(toList()),
        ',');
        ZkUtils zkUtils = ZkUtils.apply(zookeeperConnect, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        KafkaTopic kafkaTopic = new KafkaTopic(zkUtils);
        String topicName = conf.getString(Constants.TOPIC_NAME);
        int partition = conf.getInt(Constants.TOPIC_PARTITIONS, 3);
        int factory = conf.getInt(Constants.TOPIC_FACTORY, 1);
        if (!kafkaTopic.existTopic(topicName)) {
            kafkaTopic.createTopic(topicName, partition, factory);
        }
        // 其他参数
        conf.setString(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        conf.setString(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
    }

    @Override
    public void producer(KafkaProducer<byte[], byte[]> producer, ProducerRecord<byte[], byte[]> record, byte[] data, Stats stats) {
        long sendStartMs = System.currentTimeMillis();
        Callback cb = stats.nextCompletion(sendStartMs, data.length, stats);
        producer.send(record, cb);
    }

    @Override
    public int consumer(KafkaConsumer<byte[], byte[]> consumer) {
        return 0;
    }

}
