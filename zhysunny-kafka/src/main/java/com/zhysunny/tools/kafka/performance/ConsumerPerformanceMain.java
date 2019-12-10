/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.zhysunny.tools.kafka.performance;

import com.zhysunny.io.conf.Configuration;
import com.zhysunny.tools.kafka.constant.Constants;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 消费者性能测试
 * @author 章云
 * @date 2019/12/10 16:13
 */
public class ConsumerPerformanceMain {

    public static void main(String[] args) throws Exception {
        try {
            String filepath = "conf/consumer-performance.properties";
            Configuration conf = Configuration.getInstance().addDefaultResource(filepath).builder();
            Performance performance = new ConsumerPerformanceImpl();
            // 准备
            performance.prepare(conf);
            // 消费者
            KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<>(conf.getProps());
            // 绑定topic
            List<String> topics = new ArrayList<>();
            topics.add(conf.getString(Constants.TOPIC_NAME));
            consumer.subscribe(topics);
            // 消费数据
            int total = 0;
            long start = System.currentTimeMillis();
            while (true) {
                int count = performance.consumer(consumer);
                System.out.println(count);
                total += count;
                if (total >= conf.getInt(Constants.NUM_RECORDS)) {
                    break;
                }
            }
            long end = System.currentTimeMillis();
            double avgRecord = total / (double)(end - start) * 1000.0;
            System.out.printf("%d records receive, %.1f records/sec\n", total, avgRecord);
            consumer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
