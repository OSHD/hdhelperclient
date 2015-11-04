package com.hdhelper.agent.bs.impl.scripts.ls;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSEntityMarker;
import com.hdhelper.peer.RSLandscape;
import com.hdhelper.peer.RSLandscapeTile;

@ByteScript(name = "Landscape")
public class Landscape implements RSLandscape {

    @BField
    LandscapeTile[][][] tiles;

    @BField(name = "tempEntityMarkers")
    EntityMarker[] tempEntities;

    @BField
    public static boolean[][][][] visibilityMap;




    @Override
    public RSLandscapeTile[][][] getTiles() {
        return tiles;
    }

    @Override
    public RSEntityMarker[] getTempEntities() {
        return tempEntities;
    }

    @Override
    public boolean[][][][] getVisibilityMap() {
        return visibilityMap;
    }

}
