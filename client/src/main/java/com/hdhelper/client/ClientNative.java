package com.hdhelper.client;

import com.hdhelper.agent.CNI;
import com.hdhelper.agent.CNIRuntimeArgs;
import com.hdhelper.agent.CanvasFactory;
import com.hdhelper.agent.ClientCanvas;
import com.hdhelper.agent.RenderSwitch;
import com.hdhelper.client.api.ge.RTGlyphCapture;
import com.hdhelper.client.ui.HDCanvas;

public final class ClientNative {

    private static final CNI cni;
    private static final CNIRuntimeArgs args;

    static {
        try {
            // The CNI must be initialized at runtime before anything else.
            /** @see Wormhole#setLoader(ClassLoader) **/
            if(Wormhole.loader == null)
                throw new RuntimeException("loader == null");
            cni = CNI.get(Wormhole.loader);
            //  System.out.println("Initializing CNI...");
            args = getArgs();
            cni.init(args);
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(-1); // CNI init failed
            throw new Error(e); // Please the compiler due to finials
        }
    }

    private ClientNative() {
    }

    public static CNI get() {
        return cni;
    }

    private static CNIRuntimeArgs getArgs() {
        CNIRuntimeArgs args = new CNIRuntimeArgs();
        args.ren_switch     = RenderSwitch.renderAll();
        args.canvasFactory  = new HDCanvasFactory();
        args.glyphCaptureFactory = RTGlyphCapture.FACTORY;
        return args;
    }

    private static final class HDCanvasFactory implements CanvasFactory {
        @Override
        public ClientCanvas createClientCanvas() {
            return new HDCanvas();
        }
    }


    // CNI bridge objects:
    public static RenderSwitch getRenderSwitch() {
        return args.ren_switch;
    }


}

