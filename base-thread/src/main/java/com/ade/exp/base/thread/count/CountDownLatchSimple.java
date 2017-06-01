package com.ade.exp.base.thread.count;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by liyang on 2017/3/31.
 */
public class CountDownLatchSimple {

    private final static int COUNT = 10;

    public static void main(String[] args) {

        // 计数器
        CountDownLatch countDownLatch = new CountDownLatch(COUNT);
        ExecutorService executorService = Executors.newFixedThreadPool(COUNT);

        for (int i = 0; i < COUNT; i++) {
            executorService.execute(() -> {
                try {
                    int time = new Random().nextInt(5) + 3;
                    TimeUnit.SECONDS.sleep(time);
                    System.out.println("线程:" + Thread.currentThread().getName() + "经过 " + time + "秒 完成任务.");
                    countDownLatch.countDown(); // 计数器 -1
                } catch (InterruptedException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            });
        }

        try {
            countDownLatch.await(); // 等待计数器归0
        } catch (InterruptedException e) {
            System.out.println(e.getLocalizedMessage());
        }

        System.out.println("全部完成.");
        for (int i = 0; i < COUNT; i++) {
            executorService.execute(() -> {
                try {
                    int time = new Random().nextInt(5) + 3;
                    TimeUnit.SECONDS.sleep(time);
                    System.out.println("线程:" + Thread.currentThread().getName() + "经过 " + time + "秒 完成任务.");
                    countDownLatch.countDown(); // 计数器 -1
                } catch (InterruptedException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            });
        }
        try {
            // 计数器归0以后 再次await 会直接唤醒，相当于没有wait，所以CountDownLatch不能循环使用，需要循环使用考虑CyclicBarrier
            countDownLatch.await();
        } catch (InterruptedException e) {
            System.out.println(e.getLocalizedMessage());
        }
        System.out.println("全部完成.");

        executorService.shutdown();

    }

}
