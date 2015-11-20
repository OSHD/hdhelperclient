package com.hdhelper.client.cni;

import com.hdhelper.client.Main;

// Access point for the Loader Module
public class Wormhole {
    static ClassLoader loader;
    /**We 'Wormhole' the client-loader that is to be used
     * to initialize the CNI. We can not directly reference
     * the {@link ClientNative} class without it being initialized.
     * This allows us to essentially pass the client-loader argument
     * into for the static constructor to reference to.
     * @param loader The ClassLoader to be used to load the CNI/client
     */
    public static void setLoader(ClassLoader loader) {
        Wormhole.loader = loader;
    }

    /** kick-start the client **/
    public static void runClient() throws Exception {
        Main.run();
    }

}
