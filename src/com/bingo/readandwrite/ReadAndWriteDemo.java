package com.bingo.readandwrite;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadAndWriteDemo {

    static Lock lock=new ReentrantLock();
    static ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    static Lock readlLock=readWriteLock.readLock();
    static Lock writeLock=readWriteLock.writeLock();
    private volatile int value;

    public Object handleRead(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(5000);//模拟读操作，读时间越长，读写锁优势越明显
            System.out.println(Thread.currentThread().getId()+" 读: "+value);
            return value;
        }finally {
            lock.unlock();
        }
    }

    public Object handleWrite(Lock lock,int index) throws InterruptedException {
        try{
            lock.lock();
            this.value=index;
            System.out.println(Thread.currentThread().getId()+" 写: "+value);
            return this.value;
        }finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        ReadAndWriteDemo demo=new ReadAndWriteDemo();



        for (int i=18;i<20;i++){
            new Thread(()->{
                try {
                    int a=new Random(100).nextInt();
                    System.out.println("index= "+a);
                    demo.handleWrite(writeLock,a);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (int i=0;i<18;i++){
            new Thread(()->{
                try {
                    demo.handleRead(readlLock);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
