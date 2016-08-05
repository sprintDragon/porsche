package org.msgpack.test;

import org.junit.Test;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sprintdragon.ipc.Packet;

import java.io.IOException;
import java.util.*;

/**
 * Created by stereo on 16-8-5.
 */
public class MsgPackTest {

    private static final Logger logger = LoggerFactory.getLogger(MsgPackTest.class);

    @Test
    public void msgpacktest() throws IOException {
        Map<String,Object> map = new HashMap<String,Object>();
        List<Object> objectList = new ArrayList<Object>();
        map.put("1",new UserInfo());
        Packet demo = new Packet();
        demo.setResult(map);
        demo.setReturnType(UserInfo.class);
        HashSet<Object> hashSet = new HashSet<Object>();
        hashSet.add("1");
        Object[] objects = {new UserInfo(),map,hashSet};
        demo.setParams(objects);
        MessagePack messagePack = new MessagePack();
        byte[] data = messagePack.write(demo);
        demo = messagePack.read(data,Packet.class);
        logger.info(demo.toString());
    }

}
