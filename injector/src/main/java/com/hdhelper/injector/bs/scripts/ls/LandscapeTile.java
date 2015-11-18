package com.hdhelper.injector.bs.scripts.ls;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.*;
import com.hdhelper.injector.bs.scripts.collection.Node;

@ByteScript(name = "LandscapeTile")
public class LandscapeTile extends Node implements RSLandscapeTile {

    public static final int MAX_ENTITIES = 5;

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
