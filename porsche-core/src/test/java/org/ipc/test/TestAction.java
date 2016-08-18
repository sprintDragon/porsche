package org.ipc.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sprintdragon.ipc.server.service.Service;
import org.sprintdragon.ipc.server.api.INotification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stereo on 16-8-9.
 */
public class TestAction extends Service implements ITestService {
    private static Logger LOG = LoggerFactory.getLogger(TestAction.class);

    public TestAction(Class<?> cls) {
        super(cls);
    }

    @Override
    public void handleNotification(INotification notification) {
    }

    public Bean test1(Bean bean){
        LOG.info("TestAction.test1 " + bean);
        return bean;
    }

    @Override
    public int test2(Bean bean) {
        LOG.info("TestAction.test2 " + bean);
        return 1;
    }

    @Override
    public Integer test3() {
        LOG.info("TestAction.test3");
        return 1;
    }

    @Override
    public void test4() {
        LOG.info("TestAction.test4");
    }

    @Override
    public Map<String, Bean> test5(List<Bean> beens) {
        LOG.info("TestAction.test5 " + beens);
        Map<String,Bean> map = new HashMap<String,Bean>();
        for (int i = 0; i < beens.size(); i++) {
            map.put(""+i,beens.get(i));
        }
        return map;
    }

    @Override
    public Bean2 test6(Bean bean) {
        LOG.info("TestAction.test6 " + bean);
        return new Bean2();
    }
}
