package com.hdhelper.agent.bus;

import com.hdhelper.agent.peer.*;

// We dont not care about temp entities being added or removed,
// since they only exist temporally

public interface LandscapeAccess {

    // Hints that helps the event handler deal with the extreme load
    int LANDSCAPE_LOAD   = 1 << 1; // The landscape is being loaded
    int LANDSCAPE_CLEAR  = 1 << 2; // The landscape is being disposed
    int FLOOR_INIT       = 1 << 3; // An entire floor is being initialized

    void setHints(int hint);

    void objectAdded(RSEntityMarker m, boolean temp);
    void objectRemoved(RSEntityMarker m);

    void boundarySet(RSBoundary old, RSBoundary now);
    void boundaryDecoSet(RSBoundaryDecoration old, RSBoundaryDecoration now);
    void tileDecoSet(RSTileDecoration old, RSTileDecoration now);

    void tileDestroyed(RSLandscapeTile t);
    void tileCreated(RSLandscapeTile t);

}
