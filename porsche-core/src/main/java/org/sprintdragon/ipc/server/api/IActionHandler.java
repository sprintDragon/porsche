package org.sprintdragon.ipc.server.api;

import io.netty.channel.ChannelHandlerContext;
import org.sprintdragon.ipc.Heartbeat;
import org.sprintdragon.ipc.Packet;
import org.sprintdragon.ipc.server.event.HeartbeatEvent;
import org.sprintdragon.ipc.server.event.RequestEvent;
import org.sprintdragon.ipc.server.event.ResponseEvent;

/**
 * Created by stereo on 16-8-18.
 */
public interface IActionHandler {

    public void handleHeartbeat(HeartbeatEvent heartbeat) throws Exception;

    public void handleRequest(RequestEvent request) throws Exception;

    public void replyResponse(ResponseEvent response) throws Exception;

    public IActionInvoker getActionInvoker();
}
