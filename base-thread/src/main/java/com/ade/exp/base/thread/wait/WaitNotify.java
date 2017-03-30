package com.ade.exp.base.thread.wait;

import java.util.concurrent.TimeUnit;

/**
 *
 * Created by liyang on 2017/3/30.
 */
public class WaitNotify {

    public static void main(String args[]) {
        new Thread(new NumberPrint("a")).start();
        new Thread(new NumberPrint("b")).start();
    }

}

class NumberPrint implements Runnable {

    private String name;

    public NumberPrint(String name) {
        this.name = name;
    }

    public void run() {
        synchronized ("") {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    "".notify(); //唤醒等待res资源的线程，把锁交给线程（该同步锁执行完毕自动释放锁）
                    "".wait(); //释放CPU控制权，释放res的锁，本线程阻塞，等待被唤醒。
                    System.out.println(name);
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }

    }
}
