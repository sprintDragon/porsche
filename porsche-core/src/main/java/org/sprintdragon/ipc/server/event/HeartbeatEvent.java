package org.sprintdragon.ipc.server.event;

import io.netty.channel.ChannelHandlerContext;
import org.sprintdragon.ipc.Constants;
import org.sprintdragon.ipc.Heartbeat;

/**
 * Created by stereo on 16-8-18.
 */
public class HeartbeatEvent extends ActionEvent<Heartbeat>{
    public HeartbeatEvent(Heartbeat target, ChannelHandlerContext channelHandlerContext) {
        super(target, Constants.ActionEnum.HEARTBEAT, channelHandlerContext);
    }
}
