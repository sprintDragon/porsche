package org.sprintdragon.ipc.server.api;

import org.sprintdragon.ipc.Packet;

/**
 * 
 * @author stereo
 * 
 */
public interface IpcEngine{

	public void handleRequest(Packet packet) throws Exception;

	public void replyResponse(Packet packet) throws Exception;
}
