package org.sprintdragon.ipc;

/**
 * Created by stereo on 16-8-9.
 */
public final class Constants {

    /**
     * packet state
     */
    public static final byte STATUS_PENDING = 0x01; // 未处理
    public static final byte STATUS_SUCCESS_RESULT = 0x02; // 调用成功并返回结果
    public static final byte STATUS_SUCCESS_NULL = 0x03; // 调用成功并返回NULL
    public static final byte STATUS_SUCCESS_VOID = 0x04; // 调用成功无结果
    public static final byte STATUS_SERVICE_NOT_FOUND = 0x10;// 业务未找到
    public static final byte STATUS_METHOD_NOT_FOUND = 0x11; // 方法未找到
    public static final byte STATUS_ACCESS_DENIED = 0x12; // 拒绝访问
    public static final byte STATUS_INVOCATION_EXCEPTION = 0x13;// 调用时异常
    public static final byte STATUS_GENERAL_EXCEPTION = 0x14; // 一般异常
    public static final byte STATUS_APP_SHUTTING_DOWN = 0x15; // 应用程序关闭
    public static final byte STATUS_NOT_CONNECTED = 0x20; // 未连接

    /**
     * packet type
     */
    public static final byte TYPE_REQUEST = 0x01;	//普通请求类型
    public static final byte TYPE_RESPONSE = 0x02;	//普通响应类型
    public static final byte TYPE_HEARTBEAT_REQUEST = 0x03; //心跳请求类型
    public static final byte TYPE_HEARTBEAT_RESPONSE = 0x04;//心跳响应类型
}
