package com.ade.exp.base.thread.pool;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by liyang on 2017/5/31.
 */
public class ThreadPoolExp {

    private void usePoll (int count) {
        long startTime = System.currentTimeMillis();
        final List<Integer> l = new LinkedList<>();
        ThreadPoolExecutor tp = new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(count));
        final Random random = new Random();
        for (int i = 0; i < count; i++) {
            tp.execute(() -> l.add(random.nextInt()));
        }
        tp.shutdown();
        try {
            tp.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            System.out.println(e.getLocalizedMessage());
        }
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(l.size());
    }

    private void noUsePoll (int count) {
        long startTime = System.currentTimeMillis();
        final List<Integer> l = new LinkedList<>();
        final Random random = new Random();
        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> l.add(random.nextInt()));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(l.size());
    }

    public static void main(String[] args) {
        new ThreadPoolExp().usePoll(200000);
        new ThreadPoolExp().noUsePoll(200000);
    }

}
