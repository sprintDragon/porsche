package org.sprintdragon.ipc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;
import org.sprintdragon.ipc.Packet;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by stereo on 16-8-4.
 */
public class MsgPackEncoder extends MessageToByteEncoder<Serializable> {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Serializable serializable, ByteBuf byteBuf) throws Exception {
//        MessagePack messagePack = new MessagePack();
//        byte[] array = messagePack.write(serializable);

    }

    public static void main(String[] args) throws IOException {
        Packet demo = new Packet();
        demo.setResult(1);
        MessagePack messagePack = new MessagePack();
        byte[] data = messagePack.write(demo);
        demo = messagePack.read(data,Packet.class);
        System.out.println(demo);
    }
}
