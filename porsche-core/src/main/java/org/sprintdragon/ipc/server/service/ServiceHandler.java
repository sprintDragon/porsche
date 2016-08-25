package org.sprintdragon.ipc.server.service;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sprintdragon.event.Event;
import org.sprintdragon.event.EventHandler;
import org.sprintdragon.ipc.Config;
import org.sprintdragon.ipc.Constants;
import org.sprintdragon.ipc.exc.IpcRuntimeException;
import org.sprintdragon.ipc.server.api.IServiceContext;
import org.sprintdragon.ipc.server.api.IServiceHandler;
import org.sprintdragon.ipc.server.api.IServiceInvoker;
import org.sprintdragon.ipc.server.event.RequestEvent;
import org.sprintdragon.ipc.server.event.ResponseEvent;
import org.sprintdragon.ipc.server.event.enums.ServiceEnum;
import org.sprintdragon.ipc.util.Daemon;
import org.sprintdragon.ipc.util.ThreadPoolUtils;
import org.sprintdragon.service.AbstractService;

import java.util.concurrent.*;

/**
 * Created by stereo on 16-8-18.
 */
public class ServiceHandler extends AbstractService implements IServiceHandler,EventHandler<Event<ServiceEnum>> {

    private static Logger LOG = LoggerFactory.getLogger(ServiceHandler.class);

    private Config config;
    private ExecutorService handlerPool;
    private final IServiceInvoker serviceInvoker;

    public ServiceHandler(IServiceContext servicer, Config config)
    {
        super("ServiceHandler");
        serviceInvoker = new ServiceInvoker(servicer);
        this.config = config;
    }

    void initHandlerPool() {
        int minPoolSize;
        int aliveTime;
        int maxPoolSize = config.getBusinessPoolSize();
        if (Constants.THREADPOOL_TYPE_FIXED.equals(config.getBusinessPoolType())) {
            minPoolSize = maxPoolSize;
            aliveTime = 0;
        } else if (Constants.THREADPOOL_TYPE_CACHED.equals(config.getBusinessPoolType())) {
            minPoolSize = 20;
            maxPoolSize = Math.max(minPoolSize, maxPoolSize);
            aliveTime = 60000;
        } else {
            throw new IpcRuntimeException("HandlerPool-"+ config.getBusinessPoolType());
        }
        boolean isPriority = Constants.QUEUE_TYPE_PRIORITY.equals(config.getBusinessPoolQueueType());
        BlockingQueue<Runnable> configQueue = ThreadPoolUtils.buildQueue(config.getBusinessPoolQueueSize(), isPriority);
        Daemon.DaemonFactory threadFactory = new Daemon.DaemonFactory();
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            private int i = 1;
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
                if (i++ % 7 == 0)
                {
                    i = 1;
                    LOG.warn("Task:{} has been reject for InvokerPool exhausted!" +
                                    " pool:{}, active:{}, queue:{}, taskcnt: {}",
                            new Object[]{
                                    runnable,
                                    executor.getPoolSize(),
                                    executor.getActiveCount(),
                                    executor.getQueue().size(),
                                    executor.getTaskCount()
                            });
                }
                throw new RejectedExecutionException("Biz thread pool of provider has bean exhausted");
            }
        };
        handlerPool = new ThreadPoolExecutor(minPoolSize, maxPoolSize,
                aliveTime, TimeUnit.MILLISECONDS,
                configQueue, threadFactory, handler);
    }

    void shutdown() {
        if(handlerPool!=null && !handlerPool.isShutdown())
            handlerPool.shutdown();
    }

    @Override
    public void handleRequest(RequestEvent request) throws Exception {
        boolean succeed = serviceInvoker.invoke(new ServiceCall(request.getTarget()));
        if (succeed)
            replyResponse(new ResponseEvent(request.getTarget(),request.getChannelHandlerContext()));
        else
            LOG.error("handleRequest failed request : " + request.getTarget());
    }

    @Override
    public void replyResponse(ResponseEvent response) throws Exception {
        Channel channel = response.getChannelHandlerContext().channel();
        channel.writeAndFlush(response.getTarget()).sync();
    }

    @Override
    public IServiceInvoker getServiceInvoker() {
        return serviceInvoker;
    }

    @Override
    public void handle(final Event<ServiceEnum> event) {
        ServiceEnum type = event.getType();
        switch (type)
        {
            case REQUEST:
                handlerPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handleRequest((RequestEvent) event);
                        } catch (Exception ex) {
                            LOG.error("HandleRequest error",ex);
                        }
                    }
                });
                break;
            case RESPONSE:
                break;
            default:
                break;
        }
    }

    @Override
    protected void serviceInit() throws Exception {
        initHandlerPool();
    }

    @Override
    protected void serviceStart() throws Exception {
    }

    @Override
    protected void serviceStop() throws Exception {
        shutdown();
    }
}