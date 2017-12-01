package com.bingo.disruptor;


import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class Productor {
    private final RingBuffer<PCData> ringBuffer;
    public Productor(RingBuffer<PCData> ringBuffer){
        this.ringBuffer=ringBuffer;
    }
    public void pushData(ByteBuffer bb){
        long sequence = ringBuffer.next();
        try{
            PCData pcData = ringBuffer.get(sequence);
            pcData.setValue(bb.getLong(0));
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
