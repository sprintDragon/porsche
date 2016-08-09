package org.sprintdragon.ipc.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sprintdragon.ipc.Constants;
import org.sprintdragon.ipc.Packet;
import org.sprintdragon.ipc.acton.ActionContext;
import org.sprintdragon.ipc.acton.ActionFacade;
import org.sprintdragon.ipc.api.EngineHandler;
import org.sprintdragon.ipc.api.IActionContext;
import org.sprintdragon.ipc.api.IActionFacade;
import org.sprintdragon.ipc.client.Callback;
import org.sprintdragon.ipc.client.ClientHandler;

/**
 * Created by stereo on 16-8-9.
 */
public class IpcEngine extends ChannelInboundHandlerAdapter implements EngineHandler<Packet> {

    private static Logger LOG = LoggerFactory.getLogger(IpcEngine.class);

    protected IActionFacade actionFacade;

    protected IActionContext actionContext;

    public IpcEngine(){
        actionFacade = ActionFacade.getInstance();
        actionContext = ActionContext.getInstance();
    }

    @Override
    public Packet handle(Packet packet){
        actionContext.invoke(packet);
        return packet;
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
                        packet = handle(packet);
                        channel.writeAndFlush(packet).sync();
                        break;
                    case Constants.TYPE_RESPONSE:
                        break;
                    case Constants.TYPE_HEARTBEAT_REQUEST:
                        break;
                    case Constants.TYPE_HEARTBEAT_RESPONSE:
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                LOG.error("IpcEngine.handle packet is " + msg + " error");
                e.printStackTrace();
            }
        }
        else
            LOG.error("IpcEngine.channelRead error msg is " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.error("IpcEngine.exceptionCaught",cause);
        ctx.close();
    }
}
