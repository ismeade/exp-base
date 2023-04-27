package com.ade.exp.base.test.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KeyLock {

    private static Map<String, ReentrantLock> map = new ConcurrentHashMap<>();

    public static void lock(String key) {
        if (null == key || "".equals(key.trim())) {
            throw new IllegalArgumentException("key not is null");
        }
        Lock lock = map.computeIfAbsent(key, s -> new ReentrantLock());
        lock.lock();
    }

    public static void main(String[] args) throws InterruptedException {
        Map<String, Object> map = new ConcurrentHashMap<>();
//        System.out.println(map.putIfAbsent("abc", "1"));
//        System.out.println(map.putIfAbsent("abc", "1"));
//        System.out.println(map.putIfAbsent("abc", "1"));
//        System.out.println(map.putIfAbsent("abc", "1"));


        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> System.out.println(map.putIfAbsent("abc", "lock")));
        }
        TimeUnit.SECONDS.sleep(10);
        executorService.shutdown();
    }

}
