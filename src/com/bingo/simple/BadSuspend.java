package com.bingo.simple;

/**
 * suspend 线程挂起，不会释放锁资源，因此会浪费掉大量的性能
 * 另外，
 */
public class BadSuspend {
    final static Object obj= new Object();

    static T t1 = new T("t1");
    static T t2 = new T("t2");

    public static class T extends Thread{
        public T(String name){
            super.setName(name);
        }
        @Override
        public void run() {
            synchronized (obj){
                System.out.println("Thread name : "+getName());
                Thread.currentThread().suspend();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        //Thread.sleep(2000);
        t2.start();
        t1.resume();
        t2.resume();
        t1.join();
        t2.join();
    }
}
