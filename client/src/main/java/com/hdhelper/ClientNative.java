package com.hdhelper;

import com.hdhelper.agent.CNI;
import com.hdhelper.agent.CNIRuntimeArgs;
import com.hdhelper.agent.CanvasFactory;
import com.hdhelper.agent.ClientCanvas;
import com.hdhelper.agent.bridge.RenderSwitch;

public final class ClientNative {

    private static final CNI cni;
    private static final CNIRuntimeArgs args;

    static {
        // The CNI must be initialized at runtime before anything else.
        cni = CNI.get(Environment.INJECTED);
      //  System.out.println("Initializing CNI...");
        args = getArgs();
        try {
            cni.init(args);
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(-1); // CNI init failed
        }
    }

    private ClientNative() {
    }

    public static CNI get() {
        return cni;
    }

    private static CNIRuntimeArgs getArgs() {
        CNIRuntimeArgs args = new CNIRuntimeArgs();
        args.ren_switch    = new RenderSwitch();
        args.canvasFactory = new HDCanvasFactory();
        return args;
    }

    public static RenderSwitch getRenderSwitch() {
        return args.ren_switch;
    }

}

class HDCanvasFactory implements CanvasFactory {
    @Override
    public ClientCanvas createClientCanvas() {
        return new ClientCanvas();
    }
}
