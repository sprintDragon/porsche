package org.sprintdragon.ipc.server;

import org.sprintdragon.event.Dispatcher;
import org.sprintdragon.event.EventHandler;
import org.sprintdragon.ipc.Config;
import org.sprintdragon.ipc.Heartbeat;
import org.sprintdragon.ipc.util.AbstractLivelinessMonitor;

/**
 * Created by stereo on 16-8-24.
 */
public class LivelinessMonitor extends AbstractLivelinessMonitor<Heartbeat> {

    private Config config;
    private EventHandler dispatcher;

    public LivelinessMonitor(Config config, Dispatcher dispatcher) {
        super("LivelinessMonitor");
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
}
