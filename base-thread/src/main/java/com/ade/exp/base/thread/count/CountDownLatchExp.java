package com.ade.exp.base.thread.count;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 使线程进入wait，当CountDownLatch值count==0时，唤醒所有await的线程
 * 需要调用countDown方法改变count值
 *
 * Created by liyang on 2017/3/31.
 */
public class CountDownLatchExp {

    private static final int COUNT = 20;

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(COUNT);

        for (int i = 0; i < COUNT; i++) {
            new Thread(new Client(countDownLatch)).start();
        }

        TimeUnit.SECONDS.sleep(5);

        while (countDownLatch.getCount() > 0) {
            countDownLatch.countDown();
        }
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
            countDownLatch.await();
            System.out.println("wake up");
        } catch (InterruptedException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
