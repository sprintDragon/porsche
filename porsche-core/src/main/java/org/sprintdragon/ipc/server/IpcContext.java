package org.sprintdragon.ipc.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.sprintdragon.ipc.Packet;

/**
 * Created by stereo on 16-8-17.
 */
public class IpcContext {

    private Object _msg;
    private ChannelHandlerContext _channelHandlerContext;
    private static final ThreadLocal<IpcContext> _localContext = new ThreadLocal<IpcContext>();

    public static void begin(Object msg, ChannelHandlerContext channelHandlerContext){
        IpcContext context = (IpcContext) _localContext.get();
        if (context == null)
        {
            context = new IpcContext();
            _localContext.set(context);
        }
        context._msg = msg;
        context._channelHandlerContext = channelHandlerContext;
    }

    public static void end() {
        IpcContext context = (IpcContext) _localContext.get();
        if (context != null)
        {
            context._msg = null;
            context._channelHandlerContext = null;
            _localContext.set(null);
        }
    }

    public static Object getMsg()
    {
        IpcContext context = (IpcContext) _localContext.get();

        if (context != null)
            return context._msg;
        else
            return null;
    }

    public static ChannelHandlerContext getChannelHandlerContext()
    {
        IpcContext context = (IpcContext) _localContext.get();

        if (context != null)
            return context._channelHandlerContext;
        else
            return null;
    }

    public static Channel getChannel() {
        IpcContext context = (IpcContext) _localContext.get();

        if (context != null)
            return context._channelHandlerContext.channel();
        else
            return null;
    }
}
