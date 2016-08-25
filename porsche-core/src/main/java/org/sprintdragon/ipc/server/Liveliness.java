package org.sprintdragon.ipc.server;

import io.netty.channel.ChannelHandlerContext;
import org.sprintdragon.event.Dispatcher;
import org.sprintdragon.event.Event;
import org.sprintdragon.event.EventHandler;
import org.sprintdragon.ipc.Config;
import org.sprintdragon.ipc.Heartbeat;
import org.sprintdragon.ipc.server.event.enums.HeartbeatEnum;
import org.sprintdragon.ipc.util.AbstractLivelinessMonitor;

/**
 * Created by stereo on 16-8-24.
 */
public class Liveliness extends AbstractLivelinessMonitor<Heartbeat> implements EventHandler<Event<HeartbeatEnum>>{

    private Config config;
    private EventHandler dispatcher;

    public Liveliness(Config config, Dispatcher dispatcher) {
        super("Liveliness");
        this.config = config;
        this.dispatcher = dispatcher.getEventHandler();
    }

    @Override
    protected void expire(Heartbeat heartbeat) {
    }

    @Override
    protected void serviceInit() throws Exception {
        int expireIntvl = config.getHeartBeatExpireInterval();
        setExpireInterval(expireIntvl);
        setMonitorInterval(expireIntvl/3);
    }

    @Override
    public synchronized void register(Heartbeat ob) {
        super.register(ob);
    }

    @Override
    public synchronized void unregister(Heartbeat ob) {
        super.unregister(ob);
    }

    @Override
    public void handle(Event<HeartbeatEnum> event) {
        HeartbeatEnum type = event.getType();
        switch (type)
        {
            case REGISTER:
                break;
            case UNREGISTER:
                break;
            case PING:
                break;
        }
    }
}
