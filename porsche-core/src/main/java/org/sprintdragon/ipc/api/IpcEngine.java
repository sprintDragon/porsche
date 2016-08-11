package org.sprintdragon.ipc.api;

import org.sprintdragon.ipc.Packet;

/**
 * 
 * @author stereo
 * 
 */
public interface IpcEngine{

	public Packet handleAction(Packet e);

	public void registerAction(IAction action);

	public IAction retrieveAction(String actionName);

	public IAction removeAction(String actionName);

	public boolean hasAction(String actionName);
}
