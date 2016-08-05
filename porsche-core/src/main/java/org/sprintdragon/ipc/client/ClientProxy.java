package org.sprintdragon.ipc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sprintdragon.ipc.Config;
import org.sprintdragon.service.AbstractService;

/**
 * Created by stereo on 16-8-4.
 */
public class ClientProxy extends AbstractService{

    private static Logger log = LoggerFactory.getLogger(ClientProxy.class);

    private Config config;

    public ClientProxy(Config config)
    {
        super("ClientProxy"+":"+config.getRemoteAddress().toString());
        this.config = config;
    }

    @Override
    public void serviceInit() throws Exception {
    }

    @Override
    public void serviceStart() throws Exception {
    }

    @Override
    public void serviceStop() throws Exception {
    }

    public static void main(String[] args) {
    }
}
