package org.sprintdragon.ipc.server;

import io.netty.channel.ChannelHandlerContext;
import org.sprintdragon.event.Dispatcher;
import org.sprintdragon.event.EventHandler;
import org.sprintdragon.ipc.Config;
import org.sprintdragon.ipc.Heartbeat;
import org.sprintdragon.ipc.util.AbstractLivelinessMonitor;

/**
 * Created by stereo on 16-8-24.
 */
public class Liveliness extends AbstractLivelinessMonitor<Heartbeat> {

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
        ChannelHandlerContext ctx = IpcContext.getChannelHandlerContext();


        super.register(ob);
    }

    @Override
    public synchronized void unregister(Heartbeat ob) {
    }
}
