package org.msgpack.test;

import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * Created by stereo on 16-8-5.
 */
@Message
public class UserInfo implements Serializable{

    public long s = 100;

    public King king = new King();

    public Object object = new String("hellow");
}
