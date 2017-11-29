package com.bingo.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 经过ReentrantLock lockInterruptibly方法上锁的程序，可以中断响应
 *
 * 用锁中断响应可以解决死锁，让程序继续执行
 * 但是，锁中断会放弃已获得的锁，线程任务不会执行.所以，本例只有t2 work
 */
public class IntLock implements Runnable {

    ReentrantLock lock1=new ReentrantLock();
    ReentrantLock lock2=new ReentrantLock();

    int lock;
    /**
     * 为了控制加锁顺序，方便造成死锁
     * @param lock
     */
    public IntLock(int lock){
        this.lock=lock;
    }

    @Override
    public void run() {
        try {
            //产生死锁
            if(lock==1){
                lock1.lockInterruptibly();
                try{
                    Thread.sleep(2000);
                    System.out.println("t1 work");
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }

                lock2.lockInterruptibly();
            }else {
                lock2.lockInterruptibly();
                try{
                    Thread.sleep(2000);
                    System.out.println("t2 work");
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (lock1.isHeldByCurrentThread()){
                lock1.unlock();
                System.out.println("lock1");
            }
            if (lock2.isHeldByCurrentThread()){
                lock2.unlock();
                System.out.println("lock2");
            }
            System.out.println("id: "+Thread.currentThread().getName()+" 线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        IntLock l1=new IntLock(1);
        IntLock l2=new IntLock(2);
        Thread t1 = new Thread(l1);
        Thread t2 = new Thread(l2);
        t1.setName("t1");
        t2.setName("t2");
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t1.interrupt();//中断响应
    }
}
