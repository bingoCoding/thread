package com.bingo.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁限时等待 可以解决思索问题
 * tryLock 线程尝试获得锁，在一定的时间内，如果获得锁，返回true，
 *  在规定时间内如果没有获得锁，会一直等待，规定时间内得不到锁，否则false
 *  如果没有参数，会直接返回结果，不会等待
 */
public class TimeLock implements Runnable{

    public static ReentrantLock lock =new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)){
                System.out.println(Thread.currentThread().getName()+"获取锁成功");
                Thread.sleep(6000);
            }else{
                System.out.println(Thread.currentThread().getName()+"获取锁失败");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(lock.isHeldByCurrentThread()) lock.unlock();
         }
    }

    public static void main(String[] args) {
        TimeLock tl=new TimeLock();
        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);
        t1.start();
        t2.start();


    }
}
