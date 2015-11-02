package com.hdhelper.agent.bs.impl.scripts.ls;

import com.hdhelper.agent.bs.impl.scripts.collection.Node;
import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.*;

@ByteScript(name = "LandscapeTile")
public class LandscapeTile extends Node implements RSLandscapeTile {

    @BField(name = "boundaryDecorationStub")
    BoundaryDecoration boundaryDecoration;

    @BField(name = "boundaryStub")
    Boundary boundary;

    @BField(name = "tileDecorationStub")
    TileDecoration tileDecoration;

    @BField(name = "entityMarkers")
    EntityMarker[] markers;

    @BField(name = "itemPile")
    ItemPile items;

    @BField int regionX;
    @BField int regionY;
    @BField int floorLevel;




    @Override
    public RSBoundaryDecoration getBoundaryDecoration() {
        return boundaryDecoration;
    }

    @Override
    public RSBoundary getBoundary() {
        return boundary;
    }

    @Override
    public RSEntityMarker[] getMarkers() {
        return markers;
    }

    @Override
    public RSTileDecoration getTileDecoration() {
        return tileDecoration;
    }

    @Override
    public RSItemPile getItemPile() {
        return items;
    }

    @Override
    public int getRegionX() {
        return regionX;
    }

    @Override
    public int getRegionY() {
        return regionY;
    }

    @Override
    public int getFloor() {
        return floorLevel;
    }
}
