package me.marcon.qookie.io;

public interface Channel<T> {
    public int send(T payload);
}
