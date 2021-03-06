package org.msgpack.test;

import org.msgpack.BeanMessage;
import org.sprintdragon.ipc.exc.IpcRuntimeException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by stereo on 16-8-5.
 */
public class UserInfo implements BeanMessage {

    public class A{}

    public long s = 100;

    public King king = new King();

    public Object object = new String("hellow");

    public IpcRuntimeException ipcRuntimeException = new IpcRuntimeException("test",new RuntimeException("不对"));

    public static void main(String[] args) {
        Method cls = UserInfo.class.getMethods()[0];
        System.out.println(cls.getDeclaringClass());
    }

    public class InnerClass1 {
        public InnerClass1() {
            System.out.println("Inner Class1");
        }
    }

}
