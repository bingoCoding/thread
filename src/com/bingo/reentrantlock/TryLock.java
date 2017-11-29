package com.bingo.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁限时等待 可以解决死锁问题
 * tryLock 线程尝试获得锁，在一定的时间内，如果获得锁，返回true，
 *  在规定时间内如果没有获得锁，会一直等待，规定时间内得不到锁，否则false
 *  如果没有参数，会直接返回结果，不会等待
 */
public class TryLock implements Runnable {

    ReentrantLock lock1 = new ReentrantLock();
    ReentrantLock lock2 = new ReentrantLock();

    int lock;
    public TryLock(int lock){
        this.lock=lock;
    }

    @Override
    public void run() {
        if (lock==1){
            while (true){
                if (lock1.tryLock()){
                    try {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (lock2.tryLock()){
                            try {
                                System.out.println(Thread.currentThread().getName()+ " work");
                            }finally {
                                lock2.unlock();
                            }
                        }
                    }finally {
                        lock1.unlock();
                    }

                }
            }
        }else {
            while (true){
                if (lock2.tryLock()){
                    try {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (lock1.tryLock()){
                            try{
                                System.out.println(Thread.currentThread().getName() +":work");
                            }finally {
                                lock1.unlock();
                            }
                        }
                    } finally {
                        lock2.unlock();
                    }

                }
            }
        }

    }

    public static void main(String[] args) {
        TryLock l1=new TryLock(1);
        TryLock l2=new TryLock(2);
        Thread t1 =new Thread(l1);
        Thread t2 =new Thread(l2);
        t1.setName("t1");
        t2.setName("t2");
        t1.start();
        t2.start();
    }
}
