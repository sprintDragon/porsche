package org.sprintdragon.ipc.server.event;

import io.netty.channel.ChannelHandlerContext;
import org.sprintdragon.event.Event;
import org.sprintdragon.ipc.Constants;
import org.sprintdragon.ipc.util.Time;

/**
 * Created by stereo on 16-8-18.
 */
public class ActionEvent<T> implements Event<Constants.ActionEnum>{
    private T target;
    private long timestamp;
    private Constants.ActionEnum type;
    private ChannelHandlerContext channelHandlerContext;

    public ActionEvent(T target, Constants.ActionEnum type, ChannelHandlerContext channelHandlerContext){
        this.timestamp = Time.now();
        this.target = target;
        this.type = type;
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public Constants.ActionEnum getType() {
        return type;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public T getTarget() {
        return target;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }
}
