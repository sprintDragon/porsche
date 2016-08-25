package org.sprintdragon.ipc.server.event;

import io.netty.channel.ChannelHandlerContext;
import org.sprintdragon.ipc.Packet;
import org.sprintdragon.ipc.server.event.enums.ServiceEnum;

/**
 * Created by stereo on 16-8-18.
 */
public class RequestEvent extends ServiceEvent<Packet> {
    public RequestEvent(Packet target,ChannelHandlerContext channelHandlerContext) {
        super(target, ServiceEnum.REQUEST, channelHandlerContext);
    }
}
