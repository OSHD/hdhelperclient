package com.hdhelper.injector.bs.scripts.ls;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.SharedAgentSecrets;
import com.hdhelper.agent.bus.LandscapeBus;
import com.hdhelper.agent.bus.access.LandscapeBusAccess;
import com.hdhelper.agent.services.RSEntityMarker;
import com.hdhelper.agent.services.RSLandscape;
import com.hdhelper.agent.services.RSLandscapeTile;
import com.hdhelper.injector.Piston;

@ByteScript(name = "Landscape")
public class Landscape implements RSLandscape {


    @BField
    public static boolean[][][][] visibilityMap;



    @BField
    LandscapeTile[][][] tiles;
    @BField(name = "tempEntityMarkers")
    EntityMarker[] tempEntities;







    @Piston
    public Boundary getBoundaryAt(int floor, int regionX, int regionY) {
        LandscapeTile var4 = this.tiles[floor][regionX][regionY];
        return var4 != null ? var4.boundary : null;
    }

    @Piston
    public BoundaryDecoration getBoundaryDecorationAt(int floor, int regionX, int regionY) {
        LandscapeTile var4 = this.tiles[floor][regionX][regionY];
        return var4 != null ? var4.boundaryDecoration : null;
    }

    @Piston
    public TileDecoration getTileDecorationAt(int floor, int rx, int ry) {
        LandscapeTile var4 = this.tiles[floor][rx][ry];
        return var4 != null ? var4.tileDecoration : null;
    }

    @Piston
    public EntityMarker getObjectAt(int floor, int regionX, int regionY) {
        LandscapeTile var4 = this.tiles[floor][regionX][regionY];
        if(var4 != null) {
            for (int var5 = 0; var5 < LandscapeTile.MAX_ENTITIES; ++var5) {
                EntityMarker var6 = var4.markers[var5];
                if(var6 == null) continue;
                if ((var6.uid >> 29 & 3) == 2 && var6.regionX == regionX && var6.regionY == regionY) {
                    return var6;
                }
            }
        }
        return null;
    }






    @Piston
    public int getBoundaryUID(int floor, int rx, int ry) {
        LandscapeTile t = tiles[floor][rx][ry];
        return (t != null && t.boundary != null) ? t.boundary.uid : 0;
    }

    @Piston
    public int getBoundaryDecorationUID(int floor, int rx, int ry) {
        LandscapeTile t = this.tiles[floor][rx][ry];
        return t != null ? t.boundaryDecoration.uid : 0;
    }

    @Piston
    public int getTileDecorationUID(int floor, int rx, int ry) {
        LandscapeTile t = tiles[floor][rx][ry];
        return (t != null && t.tileDecoration != null) ? t.tileDecoration.uid : 0;
    }

    @Piston
    public int getObjectUID(int floor, int rx, int ry) {
        LandscapeTile t = tiles[floor][rx][ry];
        if(t != null) {
            for(int i = 0; i < LandscapeTile.MAX_ENTITIES; i++) { //TODO hook landscapeTile#numEntities and remove null check
                EntityMarker m = t.markers[i];
                if( m != null
                        && ((m.uid >> 29 & 3) == 2) // It's an object-entity-type
                        && t.regionX == rx && t.regionY == ry ) {
                    return m.uid;
                }
            }
        }
        return 0;
    }




    @Piston
    public int getConfigForUID(int floor, int rx, int ry, int UID) {
        LandscapeTile t = tiles[floor][rx][ry];
        if(t == null) {
            return -1;
        } else if(t.boundary != null && t.boundary.uid == UID) {
            return t.boundary.config & 255;
        } else if(t.boundaryDecoration != null && t.boundaryDecoration.uid == UID) {
            return t.boundaryDecoration.config & 255;
        } else if(t.tileDecoration != null && t.tileDecoration.uid == UID) {
            return t.tileDecoration.config & 255;
        } else {
            for(int i = 0; i < LandscapeTile.MAX_ENTITIES; i++) { //TODO hook landscapeTile#numEntities and remove null check
                if(t.markers[i] != null && t.markers[i].uid == UID) {
                    return t.markers[i].config & 255;
                }
            }
        }
        return -1;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////

    public LandscapeBus bus;
    private static EntityMarker ret;
    private static boolean temp;
    private static final LandscapeBusAccess access
            = SharedAgentSecrets.getLandscapeBusAccess();

    //Util methods:

    public void setTileDeco(LandscapeTile target, TileDecoration set_value) {
        if(bus == null) return; // Not interested yet
        access.tileDecoSet(bus,target.tileDecoration,set_value);
    }

    public void setBoundary(LandscapeTile target, Boundary set_value) {
        if(bus == null) return;
        access.boundarySet(bus, target.boundary, set_value);
    }

    public void setBoundaryDeco(LandscapeTile target, BoundaryDecoration set_value) {
        if(bus == null) return;
        access.boundaryDecoSet(bus,target.boundaryDecoration, set_value);
    }

    public void objectAdded() {
        if(bus== null) return;
        access.objectAdded(bus, ret, temp);
    }

    public void objectRemoved(EntityMarker m) {
        if(bus == null) return;
        access.objectRemoved(bus, m);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////


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
