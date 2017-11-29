package com.bingo.poolthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolDemo {

    public static class T extends Thread{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getId()+" run");
        }
    }

    public static void main(String[] args) {
        ExecutorService exec= Executors.newFixedThreadPool(5);
        T t=new T();
        for (int i=0;i<10;i++){
            exec.execute(t);
        }
        exec.shutdown();
    }

}
