package org.sprintdragon.ipc.acton;

import org.sprintdragon.ipc.api.INotification;

/**
 * 通知者
 * 
 * @author stereo
 */
public class Notifier {

	protected ActionFacade actionFacade = ActionFacade.getInstance();

	public INotification sendNotification(String notificationName, Object body,
										  String type) {
		return actionFacade.sendNotification(notificationName, body, type);
	}

	public INotification sendNotification(String notificationName, Object body) {
		return actionFacade.sendNotification(notificationName, body);
	}

	public INotification sendNotification(String notificationName) {
		return actionFacade.sendNotification(notificationName);
	}
}
