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
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Arrays;

/**
 * 生产者性能测试
 * @author 章云
 * @date 2019/12/10 16:13
 */
public class ProducerPerformanceMain {

    public static void main(String[] args) throws Exception {
        try {
            String filepath = "conf/producer-performance.properties";
            Configuration conf = Configuration.getInstance().addDefaultResource(filepath).builder();
            Performance performance = new ProducerPerformanceImpl();
            // 准备
            performance.prepare(conf);
            // 生产者
            KafkaProducer<byte[], byte[]> producer = new KafkaProducer<>(conf.getProps());
            // 模拟数据
            byte[] data = new byte[conf.getInt(Constants.RECORD_SIZE, 1024)];
            Arrays.fill(data, (byte)1);
            ProducerRecord<byte[], byte[]> record = new ProducerRecord<>(conf.getString(Constants.TOPIC_NAME), data);
            long numRecords = conf.getLong(Constants.NUM_RECORDS);
            Stats stats = new Stats(conf.getLong(Constants.NUM_RECORDS), 5000);
            for (int i = 0; i < numRecords; i++) {
                performance.producer(producer, record, data, stats);
            }
            stats.printTotal();
            producer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
