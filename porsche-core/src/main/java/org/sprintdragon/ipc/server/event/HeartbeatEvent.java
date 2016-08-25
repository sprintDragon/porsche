package org.sprintdragon.ipc.server.event;

import io.netty.channel.ChannelHandlerContext;
import org.sprintdragon.event.Event;
import org.sprintdragon.ipc.Heartbeat;
import org.sprintdragon.ipc.server.event.enums.HeartbeatEnum;
import org.sprintdragon.ipc.util.Time;

/**
 * Created by stereo on 16-8-25.
 */
public class HeartbeatEvent implements Event<HeartbeatEnum> {
    private long timestamp;
    private HeartbeatEnum type;
    private String topic;
    private Object topicData;
    private Heartbeat heartbeat;
    private ChannelHandlerContext channelHandlerContext;

    public HeartbeatEvent(HeartbeatEnum type, String topic, Object topicData)
    {
        this(type,null);
        this.topic = topic;
        this.topicData = topicData;
    }

    public HeartbeatEvent(HeartbeatEnum type, ChannelHandlerContext channelHandlerContext, Heartbeat heartbeat)
    {
        this(type,channelHandlerContext);
        this.heartbeat = heartbeat;
        this.heartbeat.setServer_time(timestamp);
    }

    protected HeartbeatEvent(HeartbeatEnum type,ChannelHandlerContext channelHandlerContext)
    {
        this.type = type;
        this.channelHandlerContext = channelHandlerContext;
        this.timestamp = Time.now();
    }

    @Override
    public HeartbeatEnum getType() {
        return type;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public Heartbeat getHeartbeat() {
        return heartbeat;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public String getTopic() {
        return topic;
    }

    public Object getTopicData() {
        return topicData;
    }
}
