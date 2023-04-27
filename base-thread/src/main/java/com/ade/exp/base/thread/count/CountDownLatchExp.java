package com.ade.exp.base.thread.count;

import java.util.concurrent.*;

/**
 * 使线程进入wait，当CountDownLatch值count==0时，唤醒所有await的线程
 * 需要调用countDown方法改变count值
 *
 * Created by liyang on 2017/3/31.
 */
public class CountDownLatchExp {

    private static final int COUNT = 20;

    private final static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(COUNT);

        for (int i = 0; i < COUNT; i++) {
           executorService.execute(new Client(countDownLatch));
        }

        countDownLatch.await(); // 等待，直到线程发出信号量达到COUNT时唤醒

        System.out.println("wake up");
        executorService.shutdown();
    }

}

class Client implements Runnable {

    private CountDownLatch countDownLatch;

    Client (CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            // do something
            TimeUnit.SECONDS.sleep(1);
            countDownLatch.countDown(); // 线程完成工作后发出一个信号量
            System.out.println(countDownLatch.getCount());
        } catch (InterruptedException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
