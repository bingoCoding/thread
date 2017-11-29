package com.bingo.poolthread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceDemo {
    public static void main(String[] args) {
        ScheduledExecutorService service= Executors.newScheduledThreadPool(5);
        service.scheduleAtFixedRate(new Thread(()->{
            try {
                Thread.sleep(1000);
                System.out.println(System.currentTimeMillis()/1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }),0,2, TimeUnit.SECONDS);
    }
}
