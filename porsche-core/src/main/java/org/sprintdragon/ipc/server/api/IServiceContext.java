package org.sprintdragon.ipc.server.api;


import org.sprintdragon.event.Dispatcher;

/**
 * 
 * Action控制器上下文
 * 
 * @author stereo
 */
public interface IServiceContext {

	/**
	 * 
	 * 注册Action观察者
	 * 
	 * @param noteName
	 * @param observer
	 */
	public void registerObserver(String noteName, IObserver observer);

	/**
	 * 注销Action观察者
	 * 
	 * @param noteName
	 * @param notifyContext
	 */
	public void removeObserver(String noteName, Object notifyContext);

	/**
	 * 广播事件
	 * 
	 * @param note
	 */
	public void notifyObservers(INotification note);

	/**
	 * 注册Action
	 * 
	 * @param action
	 */
	public void registerAction(IService action);

	/**
	 * 检索Action
	 * 
	 * @param actionName
	 * @return
	 */
	public IService retrieveAction(String actionName);

	/**
	 * 执行Action
	 * 
	 * @param notification
	 */
	public void executeAction(INotification notification);

	/**
	 * 注销Action
	 * 
	 * @param actionName
	 * @return
	 */
	public IService removeAction(String actionName);

	/**
	 * 是否有actionName的Action
	 * 
	 * @param actionName
	 * @return
	 */
	public boolean hasAction(String actionName);

	public IServiceHandler getServiceHandler();

	public Dispatcher getDispatcher();
}