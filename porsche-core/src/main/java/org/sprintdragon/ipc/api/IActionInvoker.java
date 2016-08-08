package org.sprintdragon.ipc.api;

/**
 * 
 * IServiceInvoker
 * 
 * @author stereo
 * @version 2013.12.19
 * 
 */
public interface IActionInvoker {

	public static final String BEAN_NAME = "serviceInvoker";

	Object getService(String serviceName);

	boolean invoke(IActionCall call);

	boolean invoke(IActionCall call, Object service);
}
