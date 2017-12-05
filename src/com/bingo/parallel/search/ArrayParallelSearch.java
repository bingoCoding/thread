package com.bingo.parallel.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ArrayParallelSearch {

    static Integer[] arr=new Integer[100];
    static AtomicInteger result=new AtomicInteger(-1);
    static int ThreadNum=2;
    static ExecutorService service= Executors.newCachedThreadPool();

    /**
     * 查询任务
     */
    public static class SearchTask implements Callable<Integer>{
        private int start;
        private int end;
        private int searchValue;

        public SearchTask(int start,int end,int searchValue){
            this.start=start;
            this.end=end;
            this.searchValue=searchValue;
        }
        @Override
        public Integer call() throws Exception {
            return search(searchValue,start,end);
        }
    }

    /**
     * 具体查询方法
     * @param searchValue
     * @param start
     * @param end
     * @return
     */
    public static Integer search(Integer searchValue,int start,int end){
        for (int i=start;i<end;i++){
            if (result.get()>0){
                return result.get();
            }
            if (arr[i]==searchValue){
                if (!result.compareAndSet(-1,i)){
                    return result.get();
                }
                return i;
            }
        }
        return -1;
    }

    /**
     * 并行查询方法
     */
    public static int parallelSearch(int searchValue) throws ExecutionException, InterruptedException {
        int subArraySize=arr.length/ThreadNum+1;
        List<Future<Integer>> list=new ArrayList<>();
        for (int i = 0; i < arr.length; i+=subArraySize) {
            int end = i+subArraySize;
            if (end>arr.length) {
                end=arr.length;
            }
            list.add(service.submit(new SearchTask(searchValue,i,end)));
        }

        for (Future<Integer> future:list) {
            if(future.get()>0){
                return future.get();
            }
        }
        return -1;
    }

}
