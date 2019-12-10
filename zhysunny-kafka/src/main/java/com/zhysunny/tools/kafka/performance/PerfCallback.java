package com.zhysunny.tools.kafka.performance;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * 生产者回调
 * @author 章云
 * @date 2019/12/10 15:20
 */
public class PerfCallback implements Callback {

    private final long start;
    private final int iteration;
    private final int bytes;
    private final Stats stats;

    public PerfCallback(int iter, long start, int bytes, Stats stats) {
        this.start = start;
        this.stats = stats;
        this.iteration = iter;
        this.bytes = bytes;
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception e) {
        long now = System.currentTimeMillis();
        int latency = (int)(now - start);
        this.stats.record(iteration, latency, bytes, now);
        if (e != null) {
            e.printStackTrace();
        }
    }

}
