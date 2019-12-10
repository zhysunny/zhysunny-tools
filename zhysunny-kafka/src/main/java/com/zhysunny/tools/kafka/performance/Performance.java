package com.zhysunny.tools.kafka.performance;

import com.zhysunny.io.conf.Configuration;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * 性能测试接口
 * @author 章云
 * @date 2019/12/10 14:26
 */
public interface Performance {

    /**
     * 准备
     * @param conf
     */
    void prepare(Configuration conf);

    /**
     * 生产数据
     * @param producer
     * @param record
     * @param data
     * @param stats
     */
    void producer(KafkaProducer<byte[], byte[]> producer, ProducerRecord<byte[], byte[]> record, byte[] data, Stats stats);

    /**
     * 消费数据
     */
    int consumer(KafkaConsumer<byte[], byte[]> consumer);

}
