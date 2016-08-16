package org.sprintdragon.ipc.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sprintdragon.ipc.Constants;
import org.sprintdragon.ipc.Packet;
import org.sprintdragon.ipc.server.acton.ActionCall;
import org.sprintdragon.ipc.server.api.*;

/**
 * Created by stereo on 16-8-9.
 */
public class IpcEngineHandler extends ChannelInboundHandlerAdapter implements IpcEngine{

    private static Logger LOG = LoggerFactory.getLogger(IpcEngineHandler.class);

    private IActionInvoker invoker;
    public IpcEngineHandler(IActionInvoker invoker){
        this.invoker = invoker;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();
        if(msg instanceof Packet)
        {
            try {
                Packet packet = (Packet) msg;
                switch (packet.getType())
                {
                    case Constants.TYPE_REQUEST:
                        packet = handleRequest(packet);
                        channel.writeAndFlush(packet).sync();
                        break;
                    case Constants.TYPE_RESPONSE:
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                LOG.error("IpcEngineHandler.handle packet is " + msg + " error");
                e.printStackTrace();
            }
        }
        else
            LOG.error("IpcEngineHandler.channelRead error msg is " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.error("IpcEngineHandler.exceptionCaught",cause);
        ctx.close();
    }

    @Override
    public Packet handleRequest(Packet packet) {
        boolean isSuccess = invoker.invoke(new ActionCall(packet));
        if (!isSuccess)
        {
            LOG.debug("IpcEngine handlePacket is failed packet:" + packet);
        }
        return packet;
    }

    @Override
    public void replyResponse(Packet packet) {
    }
}
