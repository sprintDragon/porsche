package org.sprintdragon.ipc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by stereo on 16-8-4.
 */
public class MsgPackDecoder extends LengthFieldBasedFrameDecoder {
    public MsgPackDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(10*1024*1024, lengthFieldOffset, 4,0,4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        byte[] array;
        int offset;
        int length = in.readableBytes();
        if (in.hasArray()) {
            array = in.array();
            offset = in.arrayOffset() + in.readerIndex();
        } else {
            array = new byte[length];
            in.getBytes(in.readerIndex(), array, 0, length);
            offset = 0;
        }
        return null;
    }
}
