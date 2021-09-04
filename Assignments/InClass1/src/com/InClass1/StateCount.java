package com.InClass1;

public class StateCount {
    String state;
    int count;

    public StateCount(String state, int count) {
        this.state = state;
        this.count = count;
    }

    public void increment() {
        this.count++;
    }

    @Override
    public String toString() {
        return "StateCount{" +
                "state='" + state + '\'' +
                ", count=" + count +
                '}';
    }
}
