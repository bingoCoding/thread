package com.bingo.producterAndConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<PCDate> queue=new LinkedBlockingDeque<>();
        Producter p1 = new Producter(queue);
        Producter p2 = new Producter(queue);
        Producter p3 = new Producter(queue);
        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
        Consumer c3 = new Consumer(queue);
        ExecutorService exec= Executors.newCachedThreadPool();
        exec.execute(p1);
        exec.execute(p2);
        exec.execute(p3);
        exec.execute(c1);
        exec.execute(c2);
        exec.execute(c3);
        Thread.sleep(10*1000);
        p1.stop();
        p2.stop();
        p3.stop();
        Thread.sleep(3000);
        exec.shutdown();
    }
}
