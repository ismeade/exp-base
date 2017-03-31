package com.ade.exp.base.thread.count;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 和countDownLatch类似
 * 但是不需要调用countDown，await时自动-1
 * Created by liyang on 2017/3/31.
 */
public class CyclicBarrierExp {

    public static void main(String[] args) throws InterruptedException {
        // 同步计数器的技术周期为3, 每当凑够3个线程cyclicBarrier.await()后，放行该3个线程，最后不足3个时会一直wait
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        // 启动子线程，处理“其他”业务
        for (int index = 0; index < 10; index++) {
            TimeUnit.SECONDS.sleep(1);
            new Thread(() -> {
                try {
                    System.out.println("线程准备就绪.");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
                System.out.println(Thread.currentThread().getName() + "开始执行");
            }).start();
        }
    }

}
