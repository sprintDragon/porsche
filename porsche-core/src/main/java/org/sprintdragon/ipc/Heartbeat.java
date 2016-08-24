package org.sprintdragon.ipc;

import io.netty.channel.ChannelHandlerContext;
import org.msgpack.BeanMessage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stereo on 16-8-15.
 */
public class Heartbeat implements BeanMessage {
    private String id;
    private byte type;
    private long clientTime;
    private long serverTime;
    private Map<String,Object> attributes = new HashMap<String,Object>();

    public String getId() {
        return id;
    }

    public byte getType() {
        return type;
    }

    public long getClientTime() {
        return clientTime;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public void setClientTime(long clientTime) {
        this.clientTime = clientTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public void setAttribute(String key,Object value){
        attributes.put(key,value);
    }
    public Object getAttribute(String key){
        return attributes.get(key);
    }

    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Heartbeat heartbeat = (Heartbeat) o;
        return id != null ? id.equals(heartbeat.id) : heartbeat.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
