package me.marcon.qookie.io;

import java.util.List;

public interface Serializer<T, E> {
    public E serialize(List<T> objects);
}
