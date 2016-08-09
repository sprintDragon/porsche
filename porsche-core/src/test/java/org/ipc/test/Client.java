package org.ipc.test;

import org.sprintdragon.ipc.Config;
import org.sprintdragon.ipc.api.IFunction;
import org.sprintdragon.ipc.client.ClientProxy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by stereo on 16-8-9.
 */
public class Client {

    public static void main(String[] args) throws Exception {
        final ClientProxy clientProxy = new ClientProxy(new Config( "127.0.0.1" , 10092 ));
        clientProxy.init();
        clientProxy.start();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        ITestAction testAction = clientProxy.create(ITestAction.class);
                        while (true)
                        {
                            int rs = testAction.aaa(new Bean());
                            System.out.println("结果=" + rs);
                            Thread.sleep(500);
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
        }
        // clientProxy.close();
    }
}
