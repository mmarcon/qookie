package me.marcon.qookie.example;

import java.util.List;

import me.marcon.qookie.Qookie;
import me.marcon.qookie.io.Channel;
import me.marcon.qookie.io.Serializer;
import android.util.Log;

public class Simple {
    static class LogChannel implements Channel<String> {
        @Override
        public int send(String payload) {
            Log.i("LogChannel", payload);
            return 0;
        }
    }

    static class DummySerializer implements Serializer<String, String> {
        @Override
        public String serialize(List<String> objects) {
            StringBuilder builder = new StringBuilder(':');
            for (String o : objects) {
                builder.append(o);
                builder.append(':');
            }
            return builder.toString();
        }
    }
    
    private Qookie<String, String> qookie; 
    
    public Simple(){
        qookie = new Qookie<String, String>(new LogChannel(), new DummySerializer());
    }
    
    public void test(){
        qookie.enqueue("Monkey")
            .enqueue("Monkey")
            .enqueue("Monkey")
            .enqueue("Monkey")
            .flush(); //Should log :Monkey:Monkey:Monkey:Monkey:
    }
}
