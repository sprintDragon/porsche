package org.sprintdragon.ipc.server;

import org.sprintdragon.ipc.server.acton.ActionContext;
import org.sprintdragon.ipc.server.api.IAction;
import org.sprintdragon.ipc.server.api.IActionContext;

/**
 * Created by stereo on 16-8-11.
 */
public abstract class IpcRegistry {

    private IActionContext actionContext = ActionContext.getInstance();

    public void registerAction(IAction action) {
        actionContext.registerAction(action);
    }

    public IAction retrieveAction(String actionName) {
        return actionContext.retrieveAction(actionName);
    }

    public IAction removeAction(String actionName) {
        return actionContext.removeAction(actionName);
    }

    public boolean hasAction(String actionName) {
        return actionContext.hasAction(actionName);
    }

    private static IpcRegistry registry = new IpcRegistry() {};
    static IpcRegistry get(){
        return registry;
    }
}
