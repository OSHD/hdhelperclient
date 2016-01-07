package com.hdhelper.agent.Beacon;

import java.util.HashMap;
import java.util.Map;

public final class Beacons {

    // Map of registered beacons
    private static final Map<String,Beacon> beacons
            = new HashMap<String, Beacon>();

    private Beacons() {
    }

    //TODO make private?
    public static void register(Beacon set_beacon) {
        if(set_beacon == null)
            throw new IllegalStateException("beacon == null");
        synchronized (Beacons.class) {
            String name = set_beacon.getName();
            if(beacons.containsKey(name))
                throw new IllegalStateException(name + " is already registered!");
            beacons.put(name,set_beacon);
        }
    }

    public static Beacon get(String name) {
        synchronized (Beacons.class) {
            return beacons.get(name);
        }
    }

}
