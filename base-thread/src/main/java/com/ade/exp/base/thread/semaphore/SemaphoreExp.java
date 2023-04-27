package com.ade.exp.base.thread.semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量，当信号量有余量(>0)时，可以进入acquire之后的代码，否则一直等待，
 * 代码完成后要归还信号量release
 * 可以理解成有数量的lock
 *
 * 手动编写线程池时使用
 *
 * Created by liyang on 2017/3/31.
 */
public class SemaphoreExp {

    public static void main(String[] args) {
        // 5 信号的数量 false 是否公平 true时根据调用acquire的顺序获得信号量，false时随机获取信号量
        Semaphore semaphore = new Semaphore(5, false);
        for (int i = 0; i < 10; i++) {
            new Thread(new Test(semaphore)).start();
        }
    }

}

class Test implements Runnable {

    private Semaphore semaphore;

    Test(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("get");
            TimeUnit.SECONDS.sleep(new Random().nextInt(4) + 1);
        } catch (InterruptedException e) {
            System.out.println(e.getLocalizedMessage());
        } finally {
            semaphore.release();
        }
    }

}