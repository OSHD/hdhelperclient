package com.hdhelper.agent.bus;

import com.hdhelper.agent.peer.*;

public class LandscapeBus implements LandscapeAccess {

    private static LandscapeBus instance;

    public static LandscapeBus getInstance() {
        if(instance == null) {
            instance = new LandscapeBus();
        }
        return instance;
    }

    private LandscapeBus() {

    }


    @Override
    public void setHints(int hint) {

    }

    @Override
    public void objectAdded(RSEntityMarker m,  boolean temp) {

        //System.out.println("Object:" + m + "," + temp);
    }

    @Override
    public void objectRemoved(RSEntityMarker m) {

        //System.out.println("ORemove:" + m);
    }

    @Override
    public void boundarySet(RSBoundary old, RSBoundary now) {
       // System.out.println("Boundary:" + old + "," + now);
    }

    @Override
    public void boundaryDecoSet(RSBoundaryDecoration old, RSBoundaryDecoration now) {
      //  System.out.println("BoundaryDeco:" + old + "," + now);
    }

    @Override
    public void tileDecoSet(RSTileDecoration old, RSTileDecoration now) {
        //System.out.println("TileDeco:" + old + "," + now);
    }

    @Override
    public void tileDestroyed(RSLandscapeTile t) {

    }

    @Override
    public void tileCreated(RSLandscapeTile t) {

    }
}
