package org.sprintdragon.ipc.server.event.enums;

/**
 * Created by stereo on 16-8-25.
 */
public enum HeartbeatEnum {
    REGISTER,
    UNREGISTER,

    PING,

    EXPIRE,

    PUSH, //server ---> client 推送数据

    PULL  //server ---> client 拉取数据
}
