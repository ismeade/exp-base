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
                    WaitNotifyAll.count++;
                    System.out.println("开始等待 " + WaitNotifyAll.count);
                    if (WaitNotifyAll.count == 3) {
                        WaitNotifyAll.count = 0;
                        TimeUnit.MILLISECONDS.sleep(1000);
                        WaitNotifyAll.obj.notifyAll();
                    } else {
                        WaitNotifyAll.obj.wait(); // 在这等待
                    }
                    System.out.println(name);
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                    return;
                }
            }
        }
    }
}
