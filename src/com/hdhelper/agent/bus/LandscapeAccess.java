package com.hdhelper.agent.bus;

import com.hdhelper.agent.peer.RSBoundary;
import com.hdhelper.agent.peer.RSBoundaryDecoration;
import com.hdhelper.agent.peer.RSEntityMarker;
import com.hdhelper.agent.peer.RSTileDecoration;

public interface LandscapeAccess {

    // Hints that all objects are being removed and helps the event handler deal with the extreme load
    int LANDSCAPE_CLEAR = 0;

    void setHints();

    void boundaryRemoved(int floor, int rx, int ry, RSBoundary b);
    void boundaryDecoRemoved(int floor, int rx, int ry, RSBoundaryDecoration bd);
    void tileDecoRemoved(int floor, int rx, int ry, RSTileDecoration td);
    void objectRemoved(int floor, int rx, int ry, RSEntityMarker m);

}
