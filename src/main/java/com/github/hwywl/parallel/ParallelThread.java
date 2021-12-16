package com.github.hwywl.parallel;

import cn.hutool.core.collection.CollUtil;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 并发执行线程
 *
 * @author YI create in 2021/12/16 15:26
 */
public class ParallelThread {
    /**
     * 并发执行任务
     *
     * @param tasks 任务
     * @return 任务消耗的时间（毫秒）
     * @throws InterruptedException
     */
    public static int parallelThreadTask(List<Runnable> tasks) throws InterruptedException {
        int consumptionOfTime = 0;
        if (CollUtil.isNotEmpty(tasks)) {
            long startTime = System.currentTimeMillis();
            CountDownLatch countDownLatch = new CountDownLatch(tasks.size());
            ExecutorService executorService = Executors.newFixedThreadPool(tasks.size());

            tasks.forEach(runnable -> {
                countDownLatch.countDown();
                executorService.execute(runnable);
            });
            countDownLatch.await();
            consumptionOfTime = Math.toIntExact((System.currentTimeMillis() - startTime));
            executorService.shutdown();
        }

        return consumptionOfTime;
    }

    /**
     * 并发执行任务
     *
     * @param tasks   任务
     * @param timeout 超时时间
     * @param unit    超时时间粒度
     * @return 任务消耗的时间（毫秒）
     * @throws InterruptedException
     */
    public static int parallelThreadTask(List<Runnable> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        int consumptionOfTime = 0;
        if (CollUtil.isNotEmpty(tasks)) {
            long startTime = System.currentTimeMillis();
            CountDownLatch countDownLatch = new CountDownLatch(tasks.size());
            ExecutorService executorService = Executors.newFixedThreadPool(tasks.size());

            tasks.forEach(runnable -> {
                countDownLatch.countDown();
                executorService.execute(runnable);
            });
            countDownLatch.await(timeout, unit);
            consumptionOfTime = Math.toIntExact((System.currentTimeMillis() - startTime) / 1000);
            executorService.shutdown();
        }

        return consumptionOfTime;
    }
}
