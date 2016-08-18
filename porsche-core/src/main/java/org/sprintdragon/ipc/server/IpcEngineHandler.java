package org.sprintdragon.ipc.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sprintdragon.event.Dispatcher;
import org.sprintdragon.ipc.Constants;
import org.sprintdragon.ipc.Heartbeat;
import org.sprintdragon.ipc.Packet;
import org.sprintdragon.ipc.server.acton.ActionCall;
import org.sprintdragon.ipc.server.api.*;
import org.sprintdragon.ipc.server.event.HeartbeatEvent;
import org.sprintdragon.ipc.server.event.RequestEvent;
import org.sprintdragon.ipc.server.event.ResponseEvent;

/**
 * Created by stereo on 16-8-9.
 */
public class IpcEngineHandler extends ChannelInboundHandlerAdapter implements IpcEngine{

    private static Logger LOG = LoggerFactory.getLogger(IpcEngineHandler.class);

    private Dispatcher dispatcher;
    public IpcEngineHandler(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        IpcContext.begin(msg,ctx);
        try {
            if(msg instanceof Packet)
            {
                final Packet packet = (Packet) msg;
                if (packet.getType() == Constants.TYPE_REQUEST)
                    dispatcher.getEventHandler().handle(new RequestEvent(packet,ctx));
                else if (packet.getType() == Constants.TYPE_RESPONSE)
                    dispatcher.getEventHandler().handle(new ResponseEvent(packet,ctx));
                else
                    LOG.error("IpcEngineHandler.channelRead error msg is " + msg);
            }else if (msg instanceof Heartbeat)
            {
                Heartbeat heartbeat = (Heartbeat) msg;
                dispatcher.getEventHandler().handle(new HeartbeatEvent(heartbeat,ctx));
            }
            else
                LOG.error("IpcEngineHandler.channelRead error msg is " + msg);
        } catch (Exception e)
        {
            LOG.error("IpcEngineHandler.handle packet is " + msg + " error",e);
        }
        IpcContext.end();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.error("IpcEngineHandler.exceptionCaught",cause);
        ctx.close();
    }
}
