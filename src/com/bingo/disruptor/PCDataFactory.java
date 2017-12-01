package com.bingo.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 *  工厂类
 *  在disruptor系统初始化时，构造缓冲区的对象实例。（disruptor会预先分配空间）
 */
public class PCDataFactory implements EventFactory<PCData> {
    @Override
    public PCData newInstance() {
        return new PCData();
    }
}
