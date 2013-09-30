package me.marcon.qookie;

import java.util.List;

//TODO: Find a better name
public interface QookieAction<M> {
    public void freeze(List<M> queue);
}
