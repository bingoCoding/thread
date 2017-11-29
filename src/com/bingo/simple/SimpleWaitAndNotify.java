package com.bingo.simple;

/**
 * wait 等待并释放锁
 * notify 唤醒等待中的锁
 */
public class SimpleWaitAndNotify {
    final static Object obj = new Object();

    public static class T1 extends Thread{
        @Override
        public void run() {
            synchronized(obj){
                System.out.println(System.currentTimeMillis()+":T1 start");
                try {
                    obj.wait();
                    System.out.println(System.currentTimeMillis()+":T1 wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+":T1 end");
            }
        }
    }

    public static class T2 extends Thread{
        @Override
        public void run() {
            synchronized (obj){
                System.out.println(System.currentTimeMillis()+":T2 start");
                try {
                    obj.notify();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+":T2 end");
            }
        }
    }

    public static void main(String[] args) {
        new T1().start();
        new T2().start();
    }

}
