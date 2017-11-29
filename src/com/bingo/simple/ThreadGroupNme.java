package com.bingo.simple;

/**
 * 线程组
 */
public class ThreadGroupNme implements Runnable {
    public static void main(String[] args) {
        ThreadGroup tg = new ThreadGroup("group");
        new Thread(tg,new ThreadGroupNme(),"t1").start();
        new Thread(tg,new ThreadGroupNme(),"t2").start();
        System.out.println(tg.activeCount());
        tg.list();//打印线程组所有信息
    }

    @Override
    public void run() {
        String name=Thread.currentThread().getThreadGroup().getName()+"-"+Thread.currentThread().getName();
        while (true){
            System.out.println("thead: "+name);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
