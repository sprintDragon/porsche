package org.sprintdragon.ipc.api;

/**
 * 
 * @author stereo
 * 
 * @param <E>
 */
public interface EngineHandler<E>{

	/**
	 * 请求处理
	 * 
	 * @param e
	 * @return
	 * @throws Exception
	 */
	E handle(E e);

}
