package org.ipc.test;

import org.msgpack.BeanMessage;

/**
 * Created by stereo on 16-8-11.
 */
public class Bean2 implements BeanMessage {
    public String string = "bean2";
    public String[] strings = {"A","B","C","D","C"};
}
