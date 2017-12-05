package com.bingo.future;

import java.util.concurrent.Callable;

/**
 * 操作实际数据的类
 */
public class RealDate implements Callable<String>{

    private String data;
    public RealDate(String data){
        this.data=data;
    }

    @Override
    public String call() throws Exception {
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i <10 ; i++) {
            sb.append(data).append(",");
            Thread.sleep(1000);
        }
        return sb.toString();
    }
}
