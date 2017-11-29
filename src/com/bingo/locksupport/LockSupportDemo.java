package com.bingo.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport 是一个非常方便的线程阻塞工具。它可以让线程在任意位置让线程阻塞。
 * 与suspend比，弥补了由于resume发生在前，导致线程不能执行的情况。
 * 与wait比，不用先获得锁，也不会抛出中断异常。
 *
 * LockSupport 机制与信号量类似，为每个线程只准备一个许可。
 *
 *
 */
public class LockSupportDemo {
    static Object obj=new Object();
    public static class T extends Thread{
        public T(String name){
            super.setName(name);
        }
        @Override
        public void run() {
            synchronized (obj){
                System.out.println("in "+getName());
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) {
        T t1=new T("t1");
        T t2=new T("t2");
        t1.start();
        t2.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
    }
}
