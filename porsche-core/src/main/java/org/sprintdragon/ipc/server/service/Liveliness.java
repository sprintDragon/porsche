package org.sprintdragon.ipc.server.service;

import org.sprintdragon.event.Dispatcher;
import org.sprintdragon.event.EventHandler;
import org.sprintdragon.ipc.Heartbeat;
import org.sprintdragon.ipc.server.event.HeartbeatEvent;
import org.sprintdragon.ipc.server.event.enums.HeartbeatEnum;
import org.sprintdragon.ipc.util.AbstractLivelinessMonitor;

/**
 * Created by stereo on 16-8-24.
 */
public class Liveliness extends AbstractLivelinessMonitor<Heartbeat> implements EventHandler<HeartbeatEvent>{

    private int expireIntvl;
    private EventHandler dispatcher;

    public Liveliness(int expireIntvl, Dispatcher dispatcher) {
        super("Liveliness");
        this.expireIntvl = expireIntvl;
        this.dispatcher = dispatcher.getEventHandler();
    }

    @Override
    protected void expire(Heartbeat heartbeat) {
    }

    @Override
    protected void serviceInit() throws Exception {
        setExpireInterval(expireIntvl);
        setMonitorInterval(expireIntvl/3);
    }

    @Override
    public void handle(HeartbeatEvent event) {
        HeartbeatEnum type = event.getType();
        switch (type)
        {
            case REGISTER:
                break;
            case UNREGISTER:
                break;
            case PING:
                break;
            case TOPIC_PUSH:
                break;
        }
    }
}
