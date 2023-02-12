package com.zhysunny.common.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 公共线程池 多例模式
 * @author 章云
 * @date 2019/6/25 10:12
 */
public enum ThreadPoolUtils {

    /**
     * 一个线程池实例，生产环境只能怪，参数可以使用配置
     */
    INSTANCE1("pool-1-", 20, 20),
    /**
     * 线程池实例2
     */
    INSTANCE2("pool-2-", 20, 20);

    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE_DEFAULT = 20;
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE_DEFAULT = CORE_POOL_SIZE_DEFAULT;
    /**
     * 空闲线程存活时间
     */
    private static final long KEEP_ALIVE_TIME = 60;

    /**
     * 阻塞队列
     */
    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    /**
     * 线程池对象
     */
    private ThreadPoolExecutor threadPools;

    ThreadPoolUtils(String name, int coreSize, int maxSize) {
        // 优先使用核心线程  CORE_POOL_SIZE
        // 当核心线程数用满时，线程会添加至阻塞队列等待  queue
        // 当阻塞队列满时启动最大线程数  MAX_POOL_SIZE-CORE_POOL_SIZE
        // 如果超过最大线程抛出异常，可实例化RejectedExecutionHandler进行线程数超出处理
        threadPools = new ThreadPoolExecutor(coreSize, maxSize, KEEP_ALIVE_TIME, TimeUnit.SECONDS, queue, new ThreadFactory() {
            private AtomicInteger count = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                int c = count.incrementAndGet();
                Thread t = new Thread(r);
                t.setName(name + c);
                return t;
            }
        });
    }

    /**
     * 添加任务线程
     * @param runnable
     */
    public void addThread(Runnable runnable) {
        threadPools.execute(runnable);
    }

    /**
     * 返回当前线程池状态信息
     * @return
     */
    public String getMessage() {
        StringBuilder message = new StringBuilder(256);
        message.append("线程数:").append(getActiveCount()).append(',').append("已添加线程数:").append(getTaskCount()).append(',')
        .append("已完成线程数:").append(getCompletedTaskCount()).append(',').append("阻塞队列缓存线程数:").append(getQueueSize()).append(',');
        return message.toString();
    }

    public ThreadPoolExecutor getThreadPools() {
        return this.threadPools;
    }

    /**
     * 线程池当前工作线程数
     * @return
     */
    public int getActiveCount() {
        return threadPools.getActiveCount();
    }

    /**
     * 线程池已完成任务数(或线程数)
     * @return
     */
    public long getCompletedTaskCount() {
        return threadPools.getCompletedTaskCount();
    }

    /**
     * 线程池已添加任务数(或线程数)
     * @return
     */
    public long getTaskCount() {
        return threadPools.getTaskCount();
    }

    /**
     * 阻塞队列缓存任务数(或线程数)
     * @return
     */
    public int getQueueSize() {
        return threadPools.getQueue().size();
    }

    /**
     * 等待
     * @param timeout
     * @param unit
     */
    public void await(long timeout, TimeUnit unit) throws InterruptedException {
        threadPools.awaitTermination(timeout, unit);
    }

    /**
     * 等待
     */
    public void join() throws InterruptedException {
        while (getActiveCount() > 0) {
            await(1, TimeUnit.SECONDS);
        }
    }

    /**
     * 释放线程池资源
     */
    public void shutdown() {
        if (threadPools != null) {
            threadPools.shutdown();
        }
    }
}
