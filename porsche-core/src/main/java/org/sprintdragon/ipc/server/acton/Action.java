package org.sprintdragon.ipc.server.acton;


import org.sprintdragon.ipc.server.api.IAction;
import org.sprintdragon.ipc.server.api.IActionContext;
import org.sprintdragon.ipc.server.api.INotification;

/**
 * Action虚类(可相互广播)
 * 
 * @author stereo
 */
public abstract class Action<T> implements IAction {

	protected String actionName = "actionName";

	protected IActionContext actionContext;

	@Override
	public void onRegister() {
	}

	@Override
	public void onRemove() {
	}

	@Override
	public String getActionName() {
		return actionName;
	}

	public Action(Class<?> cls) {
		this.actionName = cls.getName();
	}

	@Override
	public IAction resolveAction(String actionName) {
		if (this.actionName.equals(actionName))
			return this;
		return null;
	}

	@Override
	public INotification sendNotification(String notificationName, Object body,
										  String type) {
		INotification notification = new Notification(notificationName, body,
				type);
		actionContext.notifyObservers(notification);
		return notification;
	}

	@Override
	public INotification sendNotification(String notificationName, Object body) {
		return sendNotification(notificationName, body,null);
	}

	@Override
	public INotification sendNotification(String notificationName) {
		return sendNotification(notificationName,null,null);
	}

	public void setActionContext(IActionContext actionContext) {
		this.actionContext = actionContext;
	}
}