package org.sprintdragon.ipc.api;

/**
 * 控制接口
 * 
 * @author stereo
 */
public interface IAction extends INotifier {

	/**
	 * 注册触发
	 */
	public void onRegister();

	/**
	 * 销毁触发
	 */
	public void onRemove();

	/**
	 * 获取ActionName
	 * 
	 * @return
	 */
	public String getActionName();

	/**
	 * 适配Action
	 * 
	 * @param actionName
	 * @return
	 */
	public IAction resolveAction(String actionName);

	/**
	 * Action之间广播
	 * 
	 * @param notification
	 */
	public void handleNotification(INotification notification);
}
