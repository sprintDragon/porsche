package org.sprintdragon.ipc.server.api;

import org.sprintdragon.ipc.Packet;

/**
 * 
 * @author stereo
 * 
 */
public interface IpcEngine{

	public Packet handleRequest(Packet packet);

	public void replyResponse(Packet packet);
}
