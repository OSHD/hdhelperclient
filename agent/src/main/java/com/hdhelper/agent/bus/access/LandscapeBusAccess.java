package com.hdhelper.agent.bus.access;

import com.hdhelper.agent.bus.LandscapeBus;
import com.hdhelper.agent.services.*;

public interface LandscapeBusAccess {
    LandscapeBus mkBus(RSLandscape src,RSClient client);

    void setHints(LandscapeBus bus, int hint);

    void objectAdded(LandscapeBus bus, RSEntityMarker m, boolean temp);
    void objectRemoved(LandscapeBus bus, RSEntityMarker m);

    void boundarySet(LandscapeBus bus, RSBoundary old, RSBoundary now);
    void boundaryDecoSet(LandscapeBus bus, RSBoundaryDecoration old, RSBoundaryDecoration now);
    void tileDecoSet(LandscapeBus bus, RSTileDecoration old, RSTileDecoration now);

    void tileDestroyed(LandscapeBus bus, RSLandscapeTile t);
    void tileCreated(LandscapeBus bus, RSLandscapeTile t);

}
