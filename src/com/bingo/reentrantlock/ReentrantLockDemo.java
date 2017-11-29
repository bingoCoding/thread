package com.bingo.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;


/**
 * 重入锁 ReentrantLock
 * synchronized wait notify 的加强，使用更灵活
 * 需要手动指定枷锁位置，手动释放锁
 */
public class ReentrantLockDemo implements Runnable {

    static int i=0;
    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        for (int n=0;n<10000;n++){
            lock.lock();
            try {
                i++;
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo rd=new ReentrantLockDemo();
        Thread t1 = new Thread(rd);
        Thread t2 = new Thread(rd);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("i="+i);
    }
}
