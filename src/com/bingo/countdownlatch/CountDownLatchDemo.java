package com.bingo.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒计时器
 * 等待规定的线程都执行完毕，再继续执行
 */
public class CountDownLatchDemo implements Runnable {

    static CountDownLatch cdl=new CountDownLatch(10);
    static CountDownLatchDemo demo=new CountDownLatchDemo();

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10)*1000);
            System.out.println(Thread.currentThread().getId()+" 结束");
            cdl.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec= Executors.newFixedThreadPool(10);
        for (int i=0;i<10;i++){
            exec.execute(demo);
        }
        cdl.await();
        System.out.println("over!");
        exec.shutdown();
    }
}
