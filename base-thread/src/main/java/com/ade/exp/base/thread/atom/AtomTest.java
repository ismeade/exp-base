package com.ade.exp.base.thread.atom;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 原子效率测试
 * Created by liyang on 2017/3/31.
 */
public class AtomTest {

    private final static int MAX = 4000000;

    private int count1 = 0;
    private AtomicInteger aCount2 = new AtomicInteger(0);
    private int count3 = 0;

    private synchronized void add1() {
        count1++;
    }

    private Lock lock = new ReentrantLock();
    private void add3() {
        try {
            lock.lock();
            count3++;
        } finally {
            lock.unlock();
        }
    }

    private void test1() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            add1();
        }
        System.out.println(count1);
        System.out.println(System.currentTimeMillis() - start);
    }

    private void test2() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            aCount2.incrementAndGet();
//            aCount2.getAndAdd(1); // ++i 返回后再加
//            aCount2.addAndGet(1); // i++ 先加在返回
        }
        System.out.println(aCount2);
        System.out.println(System.currentTimeMillis() - start);
    }

    private void test3() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            add3();
        }
        System.out.println(count3);
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void main(String[] args) {
        AtomTest atomTest = new AtomTest();
        atomTest.test1();
        atomTest.test2();
        atomTest.test3();
    }

}
