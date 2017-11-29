package com.bingo.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁
 * ReentrantLock允许传一个boolean类型的参数，默认为false，标识锁是不公平竞争
 * 当改为true时，为公平竞争。即按时间的先后顺序获得锁，只要等待的时间够长，就一定会执行到。
 * 公平锁的实现成本比较高，性能较低下。因为必然会维持一个有序队列，以保证线程顺序执行。
 */
public class FairLock implements Runnable {

    ReentrantLock rt =new ReentrantLock(true);
    @Override
    public void run() {
        while (true){
            try {
                rt.lock();
                System.out.println(Thread.currentThread().getName()+"-获得锁");
            }finally {
                rt.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock fl = new FairLock();
        Thread t1 =new Thread(fl);
        Thread t2 =new Thread(fl);
        Thread t3 =new Thread(fl);
        t1.setName("t1");
        t2.setName("t2");
        t3.setName("t3");
        t1.start();
        t2.start();
        t3.start();
    }
}
