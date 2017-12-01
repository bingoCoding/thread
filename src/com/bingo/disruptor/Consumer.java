package com.bingo.disruptor;

import com.lmax.disruptor.WorkHandler;

public class Consumer implements WorkHandler<PCData>{

    @Override
    public void onEvent(PCData pcData) throws Exception {
        System.out.println(Thread.currentThread().getId()+" Evnet: "+pcData.getValue()*pcData.getValue());
    }
}
