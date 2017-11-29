package com.bingo.simple;

public class GoodSuspend {
    final static Object obj =new Object();

    public static class ChangeObjThread extends Thread{

        volatile boolean suspend=false;

        public void suspendMe(){
            suspend=true;
        }

        public void resumeMe(){
            suspend=false;
            synchronized (this){
                notify();
            }
        }
        @Override
        public void run() {
            while (true){
                synchronized (this){
                    while (suspend){
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                synchronized (obj){
                    System.out.println("in change");
                }
                Thread.yield();//让出CPU，重新竞争资源
            }
        }
    }
    public static class ReadObjThread extends Thread{
        @Override
        public void run() {
            while (true){
                synchronized (obj){
                    System.out.println("in read");
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChangeObjThread t1 = new ChangeObjThread();
        ReadObjThread t2 = new ReadObjThread();
        t1.start();
        t2.start();
        Thread.sleep(2000);
        t1.suspendMe();
        System.out.println("t1 suspend 2s =========");
        Thread.sleep(2000);
        System.out.println("t1 resume ============");
        t1.resumeMe();
    }
}
