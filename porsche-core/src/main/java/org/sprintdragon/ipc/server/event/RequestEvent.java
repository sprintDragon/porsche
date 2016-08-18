package org.sprintdragon.ipc.server.event;

import io.netty.channel.ChannelHandlerContext;
import org.sprintdragon.ipc.Constants;
import org.sprintdragon.ipc.Packet;

/**
 * Created by stereo on 16-8-18.
 */
public class RequestEvent extends ServiceEvent<Packet> {
    public RequestEvent(Packet target,ChannelHandlerContext channelHandlerContext) {
        super(target, Constants.ServiceEnum.REQUEST, channelHandlerContext);
    }
}
