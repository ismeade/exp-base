package com.ade.exp.base.thread.count;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量锁，Semaphore保存一定数量的信号量，每个线程获取一个信号量，完成业务后释放信号量，信号量发完将不能获取信号量，线程将阻塞
 * Created by liyang on 2017/6/1.
 */
public class SemaphoreSimple {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3); // 信号量的数量
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire(); // 获取一个信号量，注意归还信号量的数量，避免死锁
//                    semaphore.tryAcquire();
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("acquire");
                } catch (InterruptedException e) {
                    System.out.println(e.getLocalizedMessage());
                } finally {
                    semaphore.release();
                }
            });
        }
        executorService.shutdown();

    }

}
