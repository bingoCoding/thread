package com.bingo.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * lockSupport 支持中断响应
 */
public class LockSupportInteruptDemo {
    static Object obj=new Object();
    public static class T extends Thread{
        public T(String name){
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (obj){
                System.out.println("in "+getName());
                LockSupport.park(this);
                if(Thread.interrupted()){
                    System.out.println(getName()+" 中断");
                }
                System.out.println(getName()+" 结束");
            }
        }
    }

    public static void main(String[] args) {
        T t1=new T("t1");
        T t2=new T("t2");
        t1.start();
        t2.start();
        t1.interrupt();
        LockSupport.unpark(t2);
    }
}
