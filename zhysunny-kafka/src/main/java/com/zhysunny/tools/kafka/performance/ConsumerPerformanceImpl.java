package com.zhysunny.tools.kafka.performance;

import com.zhysunny.common.util.UnitUtils;
import com.zhysunny.io.conf.Configuration;
import com.zhysunny.tools.kafka.constant.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Arrays;
import static java.util.stream.Collectors.*;

/**
 * 生产者性能测试接口
 * @author 章云
 * @date 2019/12/10 14:33
 */
public class ConsumerPerformanceImpl implements Performance {

    @Override
    public void prepare(Configuration conf) {
        String[] hosts = conf.getStrings(Constants.SERVERS_HOSTS);
        // 拼接 bootstrap.servers
        final String kafkaPort = conf.getString(Constants.KAFKA_SERVER_PORT, "9092");
        conf.setString(Constants.BOOTSTRAP_SERVERS,
        StringUtils.join(
        Arrays.stream(hosts).map(host -> new StringBuilder(host).append(':').append(kafkaPort)).collect(toList()),
        ','));
        // 其他参数
        long size = (long)UnitUtils.getSize(conf.getString(Constants.MAX_PARTITION_FETCH_BYTES, "1M"));
        conf.setLong(Constants.MAX_PARTITION_FETCH_BYTES, size);
        conf.setString(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        conf.setString(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
    }

    @Override
    public void producer(KafkaProducer<byte[], byte[]> producer, ProducerRecord<byte[], byte[]> record, byte[] data, Stats stats) {
    }

    @Override
    public int consumer(KafkaConsumer<byte[], byte[]> consumer) {
        ConsumerRecords<byte[], byte[]> records = consumer.poll(600000);
        return records.count();
    }

}
