package org.sprintdragon.ipc.client;

public interface Callback<T> {

	void call(T value) throws Exception;

	Class<?> getAcceptValueType();
}
