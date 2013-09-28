package me.marcon.qookie;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import me.marcon.qookie.io.Channel;
import me.marcon.qookie.io.Serializer;

public class Qookie<M extends Serializable, T> {

    //TODO: generalize with the concept of policies
    public static final int NUMBER_LIMIT = 50;
    public static final int BYTE_LIMIT = 462500;
    
    // 1- enqueues messages of type M
    // 2- when ready to send, serializes list of Ms to a payload of type T
    // 3- sends payload T through a channel that can handle T 

    private List<M> queue;
    private Channel<T> channel;
    private Serializer<M, T> serializer;
    private int numberLimit;
    private int byteLimit;

    public Qookie(Channel<T> channel, Serializer<M, T> serializer) {
        this.queue = new LinkedList<M>();
        this.channel = channel;
        this.serializer = serializer;
        this.numberLimit = NUMBER_LIMIT;
        this.byteLimit = BYTE_LIMIT;
    }

    public synchronized Qookie<M, T> enqueue(M message) {
        queue.add(message);
        if(queue.size() >= numberLimit || getQueueSize() >= byteLimit) {
            flush();
        }
        return this;
    }
    
    public synchronized int flush() {
        T payload = serializer.serialize(queue);
        int result = channel.send(payload);
        if(result == 0) {
            //Successful
            queue.clear();
        }
        return result;
    }

    public int getNumberLimit() {
        return numberLimit;
    }

    public void setNumberLimit(int numberLimit) {
        this.numberLimit = numberLimit;
    }

    public int getByteLimit() {
        return byteLimit;
    }

    public void setByteLimit(int byteLimit) {
        this.byteLimit = byteLimit;
    }

    private int getQueueSize() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytes = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(queue);
            bytes = bos.toByteArray();
            out.close();
            bos.close();
            return bytes.length;
        } catch (IOException e) {
            return bytes != null ? bytes.length : -1;
        }
    }
}
