package org.sprintdragon.ipc.server;

import org.sprintdragon.ipc.server.api.IService;
import org.sprintdragon.ipc.server.api.IServiceContext;

/**
 * Created by stereo on 16-8-11.
 */
public class IpcRegistry {

    private IServiceContext actionContext;

    public IpcRegistry(IServiceContext actionContext)
    {
        this.actionContext = actionContext;
    }

    public void registerService(IService service) {
        actionContext.registerService(service);
    }

    public IService retrieveService(String serviceName) {
        return actionContext.retrieveService(serviceName);
    }

    public IService removeService(String serviceName) {
        return actionContext.removeService(serviceName);
    }

    public boolean hasService(String serviceName) {
        return actionContext.hasService(serviceName);
    }

}
