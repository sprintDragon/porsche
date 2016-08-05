package org.sprintdragon.ipc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by stereo on 16-8-4.
 */
public class MsgPackEncoder extends MessageToByteEncoder<Serializable> {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Serializable serializable, ByteBuf byteBuf) throws Exception {
    }

    public static void main(String[] args) throws IOException {

    }
}
