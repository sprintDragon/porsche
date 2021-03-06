package org.sprintdragon.ipc.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sprintdragon.event.Dispatcher;
import org.sprintdragon.ipc.Constants;
import org.sprintdragon.ipc.Heartbeat;
import org.sprintdragon.ipc.Packet;
import org.sprintdragon.ipc.server.api.*;
import org.sprintdragon.ipc.server.event.RequestEvent;
import org.sprintdragon.ipc.server.event.ResponseEvent;
import org.sprintdragon.ipc.server.service.RequestContext;

/**
 * Created by stereo on 16-8-9.
 */
public class IpcHandler extends ChannelInboundHandlerAdapter implements IpcEngine{

    private static Logger LOG = LoggerFactory.getLogger(IpcHandler.class);

    private Dispatcher dispatcher;
    public IpcHandler(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try
        {
            if(msg instanceof Packet)
            {
                final Packet packet = (Packet) msg;
                if (packet.getType() == Constants.TYPE_REQUEST)
                    dispatcher.getEventHandler().handle(new RequestEvent(packet,ctx));
                else if (packet.getType() == Constants.TYPE_RESPONSE)
                    dispatcher.getEventHandler().handle(new ResponseEvent(packet,ctx));
                else
                    LOG.error("IpcHandler.channelRead error msg is " + msg);
            }else if (msg instanceof Heartbeat)
            {
                Heartbeat heartbeat = (Heartbeat) msg;
            }
            else
                LOG.error("IpcHandler.channelRead error msg is " + msg);
        }
        catch (Exception e)
        {
            LOG.error("IpcHandler.handle packet is " + msg + " error",e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.error("IpcHandler.exceptionCaught",cause);
        ctx.close();
    }
}
