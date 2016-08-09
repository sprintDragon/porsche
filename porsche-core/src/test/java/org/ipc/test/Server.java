package org.ipc.test;

import org.sprintdragon.ipc.Config;
import org.sprintdragon.ipc.acton.ActionFacade;
import org.sprintdragon.ipc.api.IActionFacade;
import org.sprintdragon.ipc.server.IpcServer;

/**
 * Created by stereo on 16-8-9.
 */
public class Server {


    public static void main(String[] args) throws Exception {
        IpcServer ipcServer = new IpcServer(new Config("127.0.0.1",10092));
        ipcServer.init();
        ipcServer.start();
        IActionFacade facade = ActionFacade.getInstance();
        facade.registerAction(new TestAction(ITestAction.class));
        //Thread.sleep(30000);
        //ipcServer.close();
    }
}
