package org.sprintdragon.ipc.api;

import java.util.Set;

/**
 * Facades松耦合
 * 
 * @author stereo
 * 
 */
public interface IFacade extends INotifier {

	/**
	 * 广播事件
	 * 
	 * @param note
	 */
	public void notifyObservers(INotification note);

	/**
	 * 检索业务
	 * 
	 * @param actionName
	 * @return
	 */
	public IAction retrieveAction(String actionName);

	/**
	 * 注册Action
	 * 
	 * @param action
	 */
	public void registerAction(IAction action);

	/**
	 * 注册Actions
	 * 
	 * @param actions
	 */
	public void registerAction(Set<IAction> actions);

	/**
	 * 注销Action
	 * 
	 * @param notificationName
	 */
	public void removeAction(String notificationName);

	/**
	 * 是否有Action
	 * 
	 * @param notificationName
	 * @return
	 */
	public boolean hasAction(String notificationName);
}
