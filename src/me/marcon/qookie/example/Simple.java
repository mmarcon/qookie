/*
 * Copyright (c) 2013 Massimiliano Marcon, http://marcon.me
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
