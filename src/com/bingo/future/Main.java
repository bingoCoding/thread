package com.bingo.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        FutureTask task=new FutureTask(new RealDate("a"));
        ExecutorService service= Executors.newFixedThreadPool(1);
        service.execute(task);
        System.out.println("请求完毕");
        System.out.println("继续工作。。。");
        Thread.sleep(1000);
        try {
            System.out.println(task.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
