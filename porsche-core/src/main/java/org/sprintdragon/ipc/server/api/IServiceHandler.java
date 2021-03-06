package org.sprintdragon.ipc.server.api;

import org.sprintdragon.ipc.server.event.RequestEvent;
import org.sprintdragon.ipc.server.event.ResponseEvent;

/**
 * Created by stereo on 16-8-18.
 */
public interface IServiceHandler {

    //public void handleHeartbeat(HeartbeatEvent heartbeat) throws Exception;

    public void handleRequest(RequestEvent request) throws Exception;

    public void replyResponse(ResponseEvent response) throws Exception;

    public IServiceInvoker getServiceInvoker();
}
