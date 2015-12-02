package com.hdhelper.agent.bus;

import com.hdhelper.agent.SharedAgentSecrets;
import com.hdhelper.agent.bus.access.LandscapeBusAccess;
import com.hdhelper.agent.services.*;

public class LandscapeBus extends SourceBus<RSLandscape> {

    // Hints that helps the event handler deal with the extreme load
    int LANDSCAPE_LOAD   = 1 << 1; // The landscape is being loaded
    int LANDSCAPE_CLEAR  = 1 << 2; // The landscape is being disposed
    int FLOOR_INIT       = 1 << 3; // An entire floor is being initialized

    protected LandscapeBus(RSLandscape ls, RSClient client) {
        super(ls,client);
    }



    ///////////////////////////////////////////////////////////////////////////////////////


    void setHints(int hint) {

    }



    void objectAdded(RSEntityMarker m, boolean temp) {

    }

    void objectRemoved(RSEntityMarker m) {

    }




    void boundarySet(RSBoundary old, RSBoundary now) {

    }

    void boundaryDecoSet(RSBoundaryDecoration old, RSBoundaryDecoration now) {

    }

    void tileDecoSet(RSTileDecoration old, RSTileDecoration now) {

    }




    void tileDestroyed(RSLandscapeTile t) {

    }

    void tileCreated(RSLandscapeTile t) {

    }



    ///////////////////////////////////////////////////////////////////////////////////////


    static {
        SharedAgentSecrets.setLandscapeBusAccess(new LandscapeBusAccess() {

            @Override
            public LandscapeBus mkBus(RSLandscape src, RSClient client) {
                return new LandscapeBus(src, client);
            }

            @Override
            public void setHints(LandscapeBus bus, int hint) {
                bus.setHints(hint);
            }

            @Override
            public void objectAdded(LandscapeBus bus, RSEntityMarker m, boolean temp) {
                bus.objectAdded(m, temp);
            }

            @Override
            public void objectRemoved(LandscapeBus bus, RSEntityMarker m) {
                bus.objectRemoved(m);
            }

            @Override
            public void boundarySet(LandscapeBus bus, RSBoundary old, RSBoundary now) {
                bus.boundarySet(old, now);
            }

            @Override
            public void boundaryDecoSet(LandscapeBus bus, RSBoundaryDecoration old, RSBoundaryDecoration now) {
                bus.boundaryDecoSet(old, now);
            }

            @Override
            public void tileDecoSet(LandscapeBus bus, RSTileDecoration old, RSTileDecoration now) {
                bus.tileDecoSet(old, now);
            }

            @Override
            public void tileDestroyed(LandscapeBus bus, RSLandscapeTile t) {
                bus.tileDestroyed(t);
            }

            @Override
            public void tileCreated(LandscapeBus bus, RSLandscapeTile t) {
                bus.tileCreated(t);
            }

        });
    }
}
