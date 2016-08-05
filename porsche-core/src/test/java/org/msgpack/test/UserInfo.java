package org.msgpack.test;

import org.msgpack.annotation.Message;

/**
 * Created by stereo on 16-8-5.
 */
@Message
public class UserInfo {
    public long s = 100;

    public King king = new King();
}
