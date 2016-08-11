package org.sprintdragon.ipc.api;

import org.sprintdragon.ipc.Packet;

/**
 * 
 * @author stereo
 * 
 */
public interface IpcEngine{

	public Packet handleAction(Packet e);
}
