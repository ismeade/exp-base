package com.ade.exp.base.thread.wait;

import java.util.concurrent.TimeUnit;

/**
 *
 * Created by liyang on 2017/3/30.
 */
public class WaitNotifyAll {

    static int count = 0;


    static final Object obj = new Object();

    public static void main(String[] args) {
        new Thread(new Test("a")).start();
        new Thread(new Test("b")).start();
        new Thread(new Test("c")).start();
        new Thread(new NotifyAll()).start();
    }

}

class Test implements Runnable {

    private String name;

    public Test(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        synchronized (WaitNotifyAll.obj) {
            while (true) {
                try {
                    System.out.println("开始等待 " + WaitNotifyAll.obj);
                    WaitNotifyAll.obj.wait(); // 在这等待
                    System.out.println(name);
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                    return;
                }
            }
        }
    }
}

class NotifyAll implements Runnable {

    @Override
    public synchronized void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
                synchronized (WaitNotifyAll.obj) {
                    System.out.println("唤醒其他线程 " + WaitNotifyAll.obj);
                    WaitNotifyAll.obj.notifyAll();
                }
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                return;
            }
        }
    }

}