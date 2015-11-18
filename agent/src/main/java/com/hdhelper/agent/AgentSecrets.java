package com.hdhelper.agent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * A repository of "shared secrets", which are a mechanism for calling implementation-private methods
 * in another package without using reflection. A package-private class implements a public interface
 * and provides the ability to call package-private methods within that package; the object implementing
 * that interface is provided through a third package to which access is restricted. This framework avoids
 * the primary disadvantage of using reflection for this purpose, namely the loss of compile-time checking.
 */
public class AgentSecrets {

    private static final Unsafe unsafe = getTheUnsafe();

    private static ClientCanvasAccess clientCanvasAccess;

    public static void setClientCanvasAccess(ClientCanvasAccess access) {
        clientCanvasAccess = access;
    }



    public static ClientCanvasAccess getClientCanvasAccess() {
        if(clientCanvasAccess == null) {
            // Ensure ClientCanvas is initialized; we know that that class
            // provides the shared secret
            unsafe.ensureClassInitialized(ClientCanvas.class);
        }
        return clientCanvasAccess;
    }


    private static Unsafe getTheUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Object o = f.get(null);
            return (Unsafe) o;
        } catch (Throwable e) {
            throw new Error(e);
        }
    }

}
