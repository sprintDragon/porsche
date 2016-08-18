package org.sprintdragon.ipc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sprintdragon.event.Dispatcher;
import org.sprintdragon.ipc.Config;
import org.sprintdragon.ipc.codec.MsgPackDecoder;
import org.sprintdragon.ipc.codec.MsgPackEncoder;
import org.sprintdragon.ipc.exc.IpcRuntimeException;
import org.sprintdragon.ipc.server.acton.ServiceContext;
import org.sprintdragon.ipc.server.api.IServiceContext;
import org.sprintdragon.service.AbstractService;
import org.sprintdragon.service.Service;

/**
 * Created by stereo on 16-8-4.
 */
public class IpcServer extends AbstractService {

    private static Logger log = LoggerFactory.getLogger(IpcServer.class);

    private Config config;
    private Channel channel;
    private ServerBootstrap bootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private IServiceContext actionContext;
    private IpcRegistry registry;

    public IpcServer(){
        this(new Config());
    }

    public IpcServer(Config config) {
        super("IpcServer"+":"+config.getRemoteAddress().toString());
        this.config = config;
    }

    @Override
    protected void serviceInit() throws Exception {
        actionContext = new ServiceContext(config);
        ((Service)actionContext).init();

        registry = new IpcRegistry(actionContext);

        final SslContext sslCtx;
        if (config.isSsl()) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }
        Class clazz;
        if(config.isUseEpoll())
        {
            bossGroup = new EpollEventLoopGroup(1);
            workerGroup = new EpollEventLoopGroup(config.getChildNioEventThreads());
            clazz = EpollServerSocketChannel.class;
        }

        else {
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup(config.getChildNioEventThreads());
            clazz = NioServerSocketChannel.class;
        }
        bootstrap = new ServerBootstrap();
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.SO_LINGER ,config.getSoLinger());
        bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT);
        bootstrap.group(bossGroup, workerGroup)
                .channel(clazz)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>()
                {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception
                    {
                        ChannelPipeline p = ch.pipeline();
                        if (sslCtx != null) {
                            p.addLast(sslCtx.newHandler(ch.alloc()));
                        }
                        p.addLast(
                                new MsgPackEncoder(),
                                new MsgPackDecoder(config.getPayload()),
                                new IpcEngineHandler(actionContext.getDispatcher())
                        );
                    }
                });
    }

    @Override
    protected void serviceStart() throws Exception {

        if (bootstrap!=null && actionContext!=null)
        {
            ((Service)actionContext).start();
            channel = bootstrap.bind(config.getHost(),config.getPort()).sync().channel();
        }
        else
            throw new IpcRuntimeException("IpcServer is not inited");
    }

    @Override
    protected void serviceStop() throws Exception {
        if(actionContext!=null && bootstrap!=null && channel!=null && bossGroup!=null && workerGroup!=null)
        {
            ((Service)actionContext).stop();
            channel.close().sync();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            bootstrap = null;
            channel = null;
            bossGroup = null;
            workerGroup = null;

        }else
            throw new IpcRuntimeException("IpcServer is not started");
    }

    public IpcRegistry getIpcRegistry(){
        return registry;
    }

    public Config getConfig() {
        return config;
    }

    public IServiceContext getActionContext() {
        return actionContext;
    }

    public Dispatcher getDispatcher() {
        return actionContext!=null?actionContext.getDispatcher() : null;
    }
}
