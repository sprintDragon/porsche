package org.sprintdragon.ipc.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sprintdragon.ipc.Constants;
import org.sprintdragon.ipc.Packet;

/**
 * Created by stereo on 16-8-4.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static Logger LOG = LoggerFactory.getLogger(ClientHandler.class);

    private ClientProxy clientProxy;

    public ClientHandler(ClientProxy clientProxy)
    {
        this.clientProxy = clientProxy;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        LOG.debug("ClientHandler.channelRead msg is " + msg);
        Channel channel = ctx.channel();
        if(msg instanceof Packet)
        {
            Packet packet = (Packet) msg;
            switch (packet.getType())
            {
                case Constants.TYPE_REQUEST:
                    break;
                case Constants.TYPE_RESPONSE:
                    Callback callback = clientProxy.removeCallBack(packet.getId());
                    callback.call(packet.getResult());
                    break;
                case Constants.TYPE_HEARTBEAT_REQUEST:
                    break;
                case Constants.TYPE_HEARTBEAT_RESPONSE:
                    break;
                default:
                    break;
            }
        }
        else
            LOG.error("ClientHandler.channelRead error msg is " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
