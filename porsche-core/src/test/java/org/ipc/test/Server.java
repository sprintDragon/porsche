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

        //创建服务
        IpcServer ipcServer = new IpcServer(new Config("127.0.0.1",10092));
        //初始化
        ipcServer.init();
        //启动
        ipcServer.start();
        //注册接口
        IActionFacade facade = ActionFacade.getInstance();
        facade.registerAction(new TestAction(ITestAction.class));
        //关闭服务
        //ipcServer.close();
    }
}
