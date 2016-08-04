package org.sprintdragon.ipc.client;

public interface AsyncListener<T> {
	void asyncReturn(T returnValue);
}
