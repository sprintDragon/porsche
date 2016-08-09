package org.ipc.test;

import org.msgpack.annotation.Message;
import java.util.Date;

/**
 * Created by stereo on 16-8-9.
 */
@Message
public class Bean {
    public int a = 1;
    public long b = 2l;
    public String c = "bean";
    public Date d = new Date();

    @Override
    public String toString() {
        return "Bean{" +
                "a=" + a +
                ", b=" + b +
                ", c='" + c + '\'' +
                ", d=" + d +
                '}';
    }
}
