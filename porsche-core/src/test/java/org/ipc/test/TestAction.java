package org.ipc.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sprintdragon.ipc.acton.Action;
import org.sprintdragon.ipc.api.INotification;
import org.sprintdragon.ipc.client.RemoteProxy;

/**
 * Created by stereo on 16-8-9.
 */
public class TestAction extends Action implements ITestAction{
    private static Logger LOG = LoggerFactory.getLogger(TestAction.class);

    public TestAction(Class<?> cls) {
        super(cls);
    }

    @Override
    public void handleNotification(INotification notification) {
    }

    public int aaa(Bean bean){
        LOG.info("TestAction.aaa " + bean);
        return 0;
    }
}
