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

    public void registerAction(IService action) {
        actionContext.registerService(action);
    }

    public IService retrieveAction(String actionName) {
        return actionContext.retrieveService(actionName);
    }

    public IService removeAction(String actionName) {
        return actionContext.removeService(actionName);
    }

    public boolean hasAction(String actionName) {
        return actionContext.hasService(actionName);
    }

}
