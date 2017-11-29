package com.bingo.forkjointask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Long> {

    private static final int THRESHOLD=10000;
    private long start;
    private long end;

    public CountTask(long start,long end) {
        this.start=start;
        this.end=end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        boolean canCompute=end-start<THRESHOLD;
        if (canCompute){
            for (long i=start;i<end;i++){
                sum+=i;
            }
        }else{
            long step=(end-start)/100;
            ArrayList<CountTask> taskList= new ArrayList<>();
            long pos=start;
            for (int i=0;i<100;i++){
                long lastOne=pos+step;
                if (lastOne>end){
                    lastOne=end;
                }
                CountTask task=new CountTask(pos,lastOne);
                pos+=step;
                taskList.add(task);
                task.fork();
            }
            for (CountTask task:taskList){
                sum+=task.join();
            }
        }
        return sum;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        CountTask t=new CountTask(0,20000);
        ForkJoinTask<Long> result=forkJoinPool.submit(t);
        long res=result.get();
        System.out.println("sum="+res);
    }
}
