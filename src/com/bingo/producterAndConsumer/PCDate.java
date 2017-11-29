package com.bingo.producterAndConsumer;

public final class PCDate {

    private final int intData;

    public PCDate(int intData){
        this.intData=intData;
    }

    public int getIntData() {
        return intData;
    }

    @Override
    public String toString() {
        return "PCDate{" +
                "intData=" + intData +
                '}';
    }
}
