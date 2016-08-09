package org.sprintdragon.ipc.acton;


import org.sprintdragon.ipc.api.IAction;

/**
 * Action虚类(可相互广播)
 * 
 * @author stereo
 */
public abstract class Action<T> extends Notifier implements IAction {

	protected String actionName = "actionName";

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

	public Action(Class<?> cls) {
		this.actionName = cls.getName();
	}

	@Override
	public IAction resolveAction(String actionName) {
		if (this.actionName.equals(actionName))
			return this;
		return null;
	}
}