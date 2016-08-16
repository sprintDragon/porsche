package org.sprintdragon.ipc.server.api;

/**
 * 控制接口
 * 
 * @author stereo
 */
public interface IAction extends INotifier {

	public void onRemove();

	public void onRegister();

	public String getActionName();

	public IAction resolveAction(String actionName);

	public void handleNotification(INotification notification);

	public void setActionContext(IActionContext actionContext);
}
