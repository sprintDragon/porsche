package org.sprintdragon.ipc.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sprintdragon.ipc.Constants;
import org.sprintdragon.ipc.Packet;
import org.sprintdragon.ipc.api.IActionCall;
import org.sprintdragon.ipc.exc.IpcRuntimeException;
import org.sprintdragon.ipc.util.UUID;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by stereo on 16-8-8.
 */
public class RemoteProxy implements InvocationHandler {

    private static Logger LOG = LoggerFactory.getLogger(RemoteProxy.class);

    private ClientProxy clientProxy;
    private Class<?> _type;
    private WeakHashMap<Method, String> _mangleMap = new WeakHashMap<Method, String>();

    public RemoteProxy(ClientProxy proxy, Class<?> type){
        this.clientProxy = proxy;
        this._type = type;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try
        {
            String mangleName;
            synchronized (_mangleMap) {
                mangleName = _mangleMap.get(method);
            }
            if (mangleName == null) {
                String methodName = method.getName();
                Class<?>[] params = method.getParameterTypes();

                if (methodName.equals("equals") && params.length == 1
                        && params[0].equals(Object.class)) {
                    Object value = args[0];
                    if (value == null || !Proxy.isProxyClass(value.getClass()))
                        return Boolean.FALSE;
                    Object proxyHandler = Proxy.getInvocationHandler(value);
                    if (!(proxyHandler instanceof RemoteProxy))
                        return Boolean.FALSE;
                    RemoteProxy handler = (RemoteProxy) proxyHandler;
                    return new Boolean(clientProxy.equals(handler.getClientProxy()));
                } else if (methodName.equals("hashCode") && params.length == 0)
                    return new Integer(clientProxy.hashCode());
                else if (methodName.equals("getType"))
                    return proxy.getClass().getInterfaces()[0].getName();
                else if (methodName.equals("toString") && params.length == 0)
                    return "Proxy[" + clientProxy.toString() + "]";
                mangleName = method.getName();
                synchronized (_mangleMap) {
                    _mangleMap.put(method, mangleName);
                }
            }
            int sequence = -1;
            //build packet
            final Packet packet = packet(_type.getName(), method.getName(), method.getReturnType(), args);
            LOG.debug("RemoteProxy invoke packet is " + packet);
            if (packet !=null && clientProxy.removeCallBack(packet.getId()) == null)
            {
                final AsyncFuture<Object> future = new AsyncFuture<Object>();
                Callback<Object> callback = new Callback<Object>() {

                    @Override
                    public Class<?> getAcceptValueType() {
                        return packet.getReturnType();
                    }

                    @Override
                    public void call(Object value){
                        future.done(value);
                    }
                };
                clientProxy.setCallback(packet.getId(), callback);
                Channel channel = clientProxy.getChannel();
                ChannelFuture channelFuture = channel.writeAndFlush(packet).sync();
                if(channelFuture.isSuccess())
                {
                    try
                    {
                        Object result = future.get(getClientProxy().getConfig().getReadTimeout(), TimeUnit.MILLISECONDS);
                        LOG.debug("RemoteProxy invoke result is " + result);
                        return result;
                    }catch (InterruptedException ex)
                    {
                        throw new IpcRuntimeException("RemoteProxy invoke packet "+ packet + " read timeout");
                    }
                }
                else
                    throw new IpcRuntimeException("RemoteProxy invoke packet "+ packet + " send error");
            }else
                throw new IpcRuntimeException("RemoteProxy invoke packet error");
        }
        catch (Exception ex)
        {
            LOG.error("RemoteProxy invoke",ex);
            throw new IpcRuntimeException(ex);
        }
    }

    private Packet packet(String serviceName, String method,
                          Class<?> returnType, Object[] params) {
        UUID uuid = new UUID();
        uuid.setS_id(serviceName + "-" + method);
        return new Packet(uuid.toString(),Constants.TYPE_REQUEST,Constants.STATUS_PENDING,serviceName,method,params,returnType);
    }

    private ClientProxy getClientProxy() {
        return clientProxy;
    }
}
