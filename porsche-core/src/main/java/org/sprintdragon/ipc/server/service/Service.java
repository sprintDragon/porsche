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

	protected String serviceName = "serviceName";
	protected IServiceContext actionContext;

	@Override
	public void onRegister() {
	}

	@Override
	public void onRemove() {
	}

	@Override
	public String getServiceName() {
		return serviceName;
	}

	public Service(Class<?> cls) {
		this.serviceName = cls.getName();
	}

	@Override
	public IService resolveService(String actionName) {
		if (this.serviceName.equals(actionName))
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

	public void setServiceContext(IServiceContext actionContext) {
		this.actionContext = actionContext;
	}
}