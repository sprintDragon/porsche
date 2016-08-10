package org.msgpack.test;

import org.msgpack.BeanMessage;

/**
 * Created by stereo on 16-8-5.
 */
public class UserInfo implements BeanMessage {

    public long s = 100;

    public King king = new King();

    public Object object = new String("hellow");
}
