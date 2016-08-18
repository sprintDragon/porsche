package org.sprintdragon.ipc.server.service;


import org.sprintdragon.ipc.server.api.IService;
import org.sprintdragon.ipc.server.api.IServiceContext;
import org.sprintdragon.ipc.server.api.INotification;

/**
 * Action虚类(可相互广播)
 * 
 * @author stereo
 */
public abstract class Service implements IService {

	protected String actionName = "actionName";
	protected IServiceContext actionContext;

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

	public Service(Class<?> cls) {
		this.actionName = cls.getName();
	}

	@Override
	public IService resolveAction(String actionName) {
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

	public void setActionContext(IServiceContext actionContext) {
		this.actionContext = actionContext;
	}
}