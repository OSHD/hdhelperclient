package com.hdhelper.client.plugins;

import com.hdhelper.agent.services.RSEntityMarker;
import com.hdhelper.agent.services.RSObjectDefinition;
import com.hdhelper.client.api.*;
import com.hdhelper.client.api.ge.*;
import com.hdhelper.client.api.plugin.Plugin;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class AltarLocator extends Plugin  {

    int lastRegionX;
    int lastRegionY;
    Set<RSEntityMarker> altars;
    RTFont b12_full;

    public AltarLocator() {
    }

    @Override
    public void init() {
        assert Game.isLoaded();
        altars = new HashSet<RSEntityMarker>();
        b12_full = new RTFontImpl(RTGlyphVector.getB12Full());
    }

    /**
     * We scan once per region load, and assume altars are
     * never unloaded from the region.
     */
    private void update() {
        int curRegionX = client.getRegionBaseX();
        int curRegionY = client.getRegionBaseY();
        if(lastRegionX != curRegionX || lastRegionY != curRegionY) {
            //Search for Altars:
            altars.clear();
            Landscape.accept(new LandscapeVisitor() {
                @Override
                public void acceptObject(RSEntityMarker marker) {
                    int id = UID.getEntityID(marker.getUid());
                    RSObjectDefinition def = client.getObjectDef(id);
                    if(def == null || def.getName() == null) return;
                    if(def.getName().equals("Altar")) {
                        altars.add(marker);
                    }
                }
            });
        }
    }

    @Override
    public void render(RTGraphics g) {

        update();

        int tile_color = Color.GREEN.getRGB();
        int wall_color = Color.GREEN.getRGB();
        b12_full.setGraphics(g);

        for(RSEntityMarker m : altars) {
          //  if(Landscape.cull(m.getRootRegionX(),m.getRootRegionY()))
            W2S.drawEntityBounds(m, tile_color, wall_color, g);

            Point P = Minimap.tileToMinimap(m.getRootRegionX(), m.getRootRegionY());

            g.fillRectangle(P.x, P.y, 4, 4, Color.YELLOW.getRGB());
            b12_full.drawString("ALTAR", P.x, P.y, Color.GREEN.getRGB(), Color.RED.getRGB());

        }

    }

}
