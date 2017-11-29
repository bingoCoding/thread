package com.bingo.reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionAndReentrantLock implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition=lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println("waiting... ");
            condition.await();
            System.out.println("starting...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionAndReentrantLock cr=new ConditionAndReentrantLock();
        Thread t1=new Thread(cr);
        t1.start();
        Thread.sleep(2000);
        lock.lock();
        condition.signal();//唤醒等待线程
        lock.unlock();
    }
}
