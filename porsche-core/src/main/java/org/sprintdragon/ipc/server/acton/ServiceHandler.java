package org.sprintdragon.ipc.server.acton;

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
import org.sprintdragon.ipc.server.event.HeartbeatEvent;
import org.sprintdragon.ipc.server.event.RequestEvent;
import org.sprintdragon.ipc.server.event.ResponseEvent;
import org.sprintdragon.ipc.util.Daemon;
import org.sprintdragon.ipc.util.ThreadPoolUtils;
import org.sprintdragon.service.AbstractService;

import java.util.concurrent.*;

/**
 * Created by stereo on 16-8-18.
 */
public class ServiceHandler extends AbstractService implements IServiceHandler,EventHandler<Event<Constants.ActionEnum>> {

    private static Logger LOG = LoggerFactory.getLogger(ServiceHandler.class);

    private ExecutorService handlerPool;
    private final IServiceInvoker actionInvoker;
    final int poolSize;
    final int queueSize;
    final String poolType;
    final String queueType;

    public ServiceHandler(IServiceContext servicer, Config config)
    {
        super("ServiceHandler");
        actionInvoker = new ServiceInvoker(servicer);
        poolSize = config.getBusinessPoolSize();
        queueSize = config.getBusinessPoolQueueSize();
        poolType = config.getBusinessPoolType();
        queueType = config.getBusinessPoolQueueType();
    }

    void initHandlerPool() {
        int minPoolSize;
        int aliveTime;
        int maxPoolSize = poolSize;
        if (Constants.THREADPOOL_TYPE_FIXED.equals(poolType)) {
            minPoolSize = maxPoolSize;
            aliveTime = 0;
        } else if (Constants.THREADPOOL_TYPE_CACHED.equals(poolType)) {
            minPoolSize = 20;
            maxPoolSize = Math.max(minPoolSize, maxPoolSize);
            aliveTime = 60000;
        } else {
            throw new IpcRuntimeException("HandlerPool-"+ poolType);
        }
        boolean isPriority = Constants.QUEUE_TYPE_PRIORITY.equals(queueType);
        BlockingQueue<Runnable> configQueue = ThreadPoolUtils.buildQueue(queueSize, isPriority);
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
    public void handleHeartbeat(HeartbeatEvent heartbeat) throws Exception {
    }

    @Override
    public void handleRequest(RequestEvent request) throws Exception {
        boolean succeed = actionInvoker.invoke(new ServiceCall(request.getTarget()));
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
    public IServiceInvoker getActionInvoker() {
        return actionInvoker;
    }

    @Override
    public void handle(final Event<Constants.ActionEnum> event) {
        Constants.ActionEnum type = event.getType();
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
            case HEARTBEAT:
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
