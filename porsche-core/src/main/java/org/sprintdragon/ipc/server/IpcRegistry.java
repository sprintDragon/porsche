package org.sprintdragon.ipc.server;

import org.sprintdragon.ipc.acton.ActionContext;
import org.sprintdragon.ipc.api.IAction;
import org.sprintdragon.ipc.api.IActionContext;

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

    public static IpcRegistry get(){
        return new IpcRegistry() {};
    }
}
