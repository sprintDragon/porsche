package org.sprintdragon.ipc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;
import org.sprintdragon.ipc.Packet;

import java.util.List;

/**
 * Created by stereo on 16-8-4.
 */
public class MsgPackDecoder extends LengthFieldBasedFrameDecoder {

    private final MessagePack messagePack = new MessagePack();

    public MsgPackDecoder() {
        super(64 * 1024 * 1024, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null)
        {
            return null;
        }
        ByteBufInputStream bin = new ByteBufInputStream(in);
        int len = bin.available();
        if(len > 0)
        {
            byte[] data = new byte[len];
            bin.read(data,0,len);
            Packet packet = messagePack.read(data, Packet.class);
            bin.close();
            return packet;
        }else
            return null;
    }

    @Override
    protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
        return buffer.slice(index, length);
    }
}
