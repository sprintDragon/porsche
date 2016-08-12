package org.sprintdragon.ipc.acton;


import org.sprintdragon.ipc.api.IAction;
import org.sprintdragon.ipc.api.IActionFacade;
import org.sprintdragon.ipc.api.INotification;
import java.util.Set;

/**
 * ActionFacade 降低Action耦合
 * 
 * @author stereo
 */
public class ActionFacade extends AttributeStore implements IActionFacade {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9157702014060683354L;

	private static class ActionFacadeHolder{
		private static ActionFacade instance = new ActionFacade();
	}

	protected ActionContext actionContext = null;

	private ActionFacade() {
		initializeFacade();
	}

	protected void initializeFacade() {
		if (actionContext != null)
			return;
		actionContext = ActionContext.getInstance();
	}

	public static ActionFacade getInstance() {
		return ActionFacadeHolder.instance;
	}

	@Override
	public void registerAction(IAction action) {
		actionContext.registerAction(action);
	}

	@Override
	public void registerAction(Set<IAction> actions) {
		for (IAction action : actions)
			registerAction(action);
	}

	@Override
	public void removeAction(String actionName) {
		this.actionContext.removeAction(actionName);
	}

	@Override
	public boolean hasAction(String notificationName) {
		return actionContext.hasAction(notificationName);
	}

	public INotification sendNotification(String notificationName, Object body,
										  String type) {
		INotification notification = new Notification(notificationName, body,
				type);
		notifyObservers(notification);
		return notification;
	}

	public INotification sendNotification(String notificationName, Object body) {
		return sendNotification(notificationName, body, null);
	}

	public INotification sendNotification(String notificationName) {
		return sendNotification(notificationName, null, null);
	}

	@Override
	public void notifyObservers(INotification note) {
		if (actionContext != null)
			actionContext.notifyObservers(note);
	}

	@Override
	public IAction retrieveAction(String actionName) {
		return actionContext.retrieveAction(actionName);
	}
}