package org.sprintdragon.ipc.server.api;

/**
 * 控制接口
 * 
 * @author stereo
 */
public interface IService extends INotifier {

	public void onRemove();

	public void onRegister();

	public String getActionName();

	public IService resolveAction(String actionName);

	public void handleNotification(INotification notification);

	public void setActionContext(IServiceContext actionContext);
}
