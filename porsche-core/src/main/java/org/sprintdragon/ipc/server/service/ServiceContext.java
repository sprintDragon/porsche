package org.sprintdragon.ipc.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;
import org.sprintdragon.event.AsyncDispatcher;
import org.sprintdragon.event.Dispatcher;
import org.sprintdragon.event.EventHandler;
import org.sprintdragon.ipc.Config;
import org.sprintdragon.ipc.Constants;
import org.sprintdragon.ipc.server.api.*;
import org.sprintdragon.service.AbstractService;
import org.sprintdragon.service.Service;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 控制层上下文
 * 
 * @author stereo
 */
public class ServiceContext extends AbstractService implements IServiceContext,
		Iterable<IService> {
	private Config config;
	private Dispatcher dispatcher;
	private IServiceHandler serviceHandler;
	protected Map<String, IService> actionMap;
	protected Map<String, List<IObserver>> observerMap;
	public static Logger logger = LoggerFactory.getLogger(ServiceContext.class);
	private static ThreadLocal<WeakReference<Object>> threadLocal = new ThreadLocal<WeakReference<Object>>();

	public ServiceContext(Config config) {
		super("ServiceContext");
		this.config = config;
	}

	@Override
	protected void serviceInit() throws Exception {
		actionMap = new ConcurrentHashMap<String, IService>();
		observerMap = new ConcurrentHashMap<String, List<IObserver>>();

		//事件处理器
		dispatcher = new AsyncDispatcher();
		((Service)dispatcher).init();

		//业务处理器
		serviceHandler = new ServiceHandler(this,config);
		((Service)serviceHandler).init();

		dispatcher.register(Constants.ServiceEnum.class, (EventHandler) serviceHandler);
	}

	@Override
	protected void serviceStart() throws Exception {
		if (dispatcher!=null)
			((Service)dispatcher).start();

		if (serviceHandler!=null)
			((Service)serviceHandler).start();
	}

	@Override
	protected void serviceStop() throws Exception {
		if (dispatcher!=null)
			((Service)dispatcher).stop();

		if (serviceHandler!=null)
			((Service)serviceHandler).stop();

	}

	public static Object getObjectLocal() {
		WeakReference<Object> ref = threadLocal.get();
		if (ref != null) {
			return ref.get();
		} else {
			return null;
		}
	}

	public static void setObjectLocal(Object object) {
		if (object != null) {
			threadLocal.set(new WeakReference<Object>(object));
		} else {
			threadLocal.remove();
		}
	}

	@Override
	public void executeAction(INotification note) {
		IService actionInstance = this.actionMap.get(note.getName());
		if (actionInstance != null) {
			actionInstance.handleNotification(note);
		}
	}

	@Override
	public void registerAction(final IService action) {
		if (this.actionMap.containsKey(action.getActionName()))
			return;
		this.actionMap.put(action.getActionName(), action);

		registerObserver(action.getActionName(), new Observer(new IFunction() {
			public void onNotification(INotification notification) {
				executeAction(notification);
			}
		}, this));
		action.setActionContext(this);
		action.onRegister();
	}

	@Override
	public IService retrieveAction(String actionName) {
		if (null != actionMap.get(actionName)) {
			return this.actionMap.get(actionName);
		}
		for (IService action : this)
			return action.resolveAction(actionName);
		return null;
	}

	@Override
	public IService removeAction(String actionName) {
		if (hasAction(actionName)) {
			IService action = actionMap.get(actionName);
			removeObserver(actionName, this);
			actionMap.remove(actionName);
			action.onRemove();
			return action;
		}
		return null;
	}

	@Override
	public boolean hasAction(String actionName) {
		return actionMap.containsKey(actionName);
	}

	@Override
	public void registerObserver(String notificationName, IObserver observer) {
		if (this.observerMap.get(notificationName) == null)
			this.observerMap.put(notificationName, new ArrayList<IObserver>());

		List<IObserver> observers = this.observerMap.get(notificationName);
		observers.add(observer);
	}

	@Override
	public void removeObserver(String notificationName, Object notifyContext) {
		List<IObserver> observers = observerMap.get(notificationName);
		if (observers != null) {
			for (int i = 0; i < observers.size(); i++) {
				Observer observer = (Observer) observers.get(i);
				if (observer.compareNotifyContext(notifyContext) == true)
					observers.remove(observer);
			}
			if (observers.size() == 0)
				observerMap.remove(notificationName);
		}
	}

	@Override
	public void notifyObservers(INotification note) {
		List<IObserver> observers_ref = observerMap.get(note.getName());
		if (observers_ref != null) {
			Object[] observers = (Object[]) observers_ref.toArray();
			for (int i = 0; i < observers.length; i++) {
				IObserver observer = (IObserver) observers[i];
				observer.notifyObserver(note);
			}
		}
	}

	@Override
	public Iterator<IService> iterator() {
		return actionMap.values().iterator();
	}

	@Override
	public IServiceHandler getServiceHandler() {
		return serviceHandler;
	}

	@Override
	public Dispatcher getDispatcher() {
		return dispatcher;
	}
}