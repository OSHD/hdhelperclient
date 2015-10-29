package com.hdhelper.api.ge.impl;

import com.hdhelper.Main;
import com.hdhelper.api.Equipment;
import com.hdhelper.api.W2S;
import com.hdhelper.api.ge.BasicOverlay;
import com.hdhelper.api.ge.FontFactory;
import com.hdhelper.api.ge.RTFont;
import com.hdhelper.api.ge.RTGraphics;
import com.hdhelper.peer.*;

import java.awt.*;
import java.util.Arrays;

public class Debug extends BasicOverlay {

    private TextWarrior warrior;

    public Debug() {
    }


    @Override
    public void paint(RTGraphics g) {

        if(warrior == null) {
            warrior = new TextWarrior();
        }

        try {

            warrior.start(g);


            class SoLazy {

                int base_x = 15;
                int base_y = 20;
                int gap = 15;
                int i = 0;

                int x() {
                    return base_x;
                }
                int y() {
                    return base_y + gap * i++;
                }
            }

            SoLazy lol = new SoLazy();


            RSClient client = Main.client;

            assert client != null;

            final int bx = client.getRegionBaseX();
            final int by = client.getRegionBaseY();

            final int floor = client.getFloor();

            RSPlayer me = client.getMyPlayer();
            if (me != null) {

                warrior.setColor(Color.GREEN);

                int rx = me.getRegionX();
                int ry = me.getRegionY();
                int x = bx + rx;
                int y = by + ry;

                warrior.drawString("Name:" + me.getName(),lol.x(),lol.y());
                warrior.drawString("X:" + x + "(" + rx + ")",lol.x(), lol.y());
                warrior.drawString("Y:" + y + "(" + ry + ")",lol.x(), lol.y());

            }


            warrior.setColor(Color.RED);
            for (RSPlayer p : client.getPlayers()) {
                if (p == null) continue;
                int rx = p.getRegionX();
                int ry = p.getRegionY();
                W2S.draw3DBox(floor, rx, ry, p.getHeight(), g, Color.RED.getRGB());
                Point P = W2S.tileToViewport(p.getStrictX(), p.getStrictY(), floor, p.getHeight());
                if (P.x == -1) continue;
                warrior.drawString(p.getName() + " | Lvl:" + p.getCombatLevel() + " | Anim:" + p.getAnimation() + " | Target:" + p.getTargetIndex() + "Orintation:" + p.getOrientation(), P.x, P.y);
            }

            //Render Npcs:
            warrior.setColor(Color.BLUE);
            for (RSNpc p : client.getNpcs()) {
                if (p == null) continue;
                int rx = p.getRegionX();
                int ry = p.getRegionY();
                W2S.draw3DBox(floor, rx, ry, p.getHeight(), g, Color.BLUE.getRGB());
                if (p.getDef() == null) continue;
                Point P = W2S.tileToViewport(p.getStrictX(), p.getStrictY(), floor, p.getHeight());
                if (P.x == -1) continue;
                RSNpcDefintion def = p.getDef();
                warrior.drawString(def.getName() + " | Anim:" + p.getAnimation() + " | Target:" + p.getTargetIndex() + "| Orintation:" + p.getOrientation(), P.x, P.y);
            }


            //Render GroundItems:
            warrior.setColor(Color.YELLOW);
            RSDeque[][] items = client.getGroundItems()[floor];
            for (int x = 0; x < 104; x++) {
                for (int y = 0; y < 104; y++) {
                    RSDeque pile = items[x][y];
                    if (pile == null) continue;
                    RSNode[] nodes = pile.toArray();
                    for (RSNode node : nodes) {
                        RSGroundItem gi = (RSGroundItem) node;
                        W2S.draw3DBox(floor, x, y, gi.getHeight(), g, Color.YELLOW.getRGB());
                        final int sx = (x << 7) + 64;
                        final int sy = (y << 7) + 64;
                        int id = gi.getId();
                        RSItemDefinition def = client.getItemDef(id);
                        Point P = W2S.tileToViewport(sx, sy, floor, gi.getHeight());
                        if (P.x == -1) continue;
                        warrior.drawString(def.getName() + "(" + id + ") x " + gi.getQuantity(), P.x, P.y);
                    }
                }
            }


            warrior.setColor(Color.GREEN);

            warrior.drawString("Floor:" + client.getFloor(), lol.x(), lol.y());
            lol.i += 1;

            warrior.drawString("Pitch:" + client.getPitch(), lol.x(), lol.y());
            warrior.drawString("Yaw:" + client.getYaw(), lol.x(), lol.y());
            warrior.drawString("CamX:" + client.getCameraX(), lol.x(), lol.y());
            warrior.drawString("CamY:" + client.getCameraY(), lol.x(), lol.y());
            warrior.drawString("CamZ:" + client.getCameraZ(), lol.x(), lol.y());
            lol.i += 1;

            warrior.drawString("Scale:" + client.getViewportScale(), lol.x(), lol.y());
            warrior.drawString("Width:" + client.getViewportWidth(), lol.x(), lol.y());
            warrior.drawString("Height:" + client.getViewportHeight(), lol.x(), lol.y());
            lol.i += 1;

            // XTEA Debug:
            int[][] keys = client.getKeys();
            int[] chunks = client.getChunkIds();
            if (chunks != null && keys != null) {
                int num_chunks = keys.length;
                warrior.drawString("Chunks:" + num_chunks, lol.x(),lol.y());
                for (int k = 0; k < num_chunks; k++) {
                    int chunkId = chunks[k];
                    int[] key = keys[k];
                    int rx = chunkId >> 8 & 255;
                    int ry = chunkId & 255;
                    warrior.drawString("<" + rx + "_" + ry + "> => " + Arrays.toString(key), lol.x(),lol.y());
                }
            }
            lol.i += 1;

            //Equipment Debug:
            for (Equipment.Slot s : Equipment.Slot.values()) {
                warrior.drawString(s.name() + ":" + s.get(), lol.x(),lol.y());
            }
            
        } finally {
             // It's not critical we release references to the graphic.
             // But it does not heart to help the garbage collector a bit
            warrior.finish();
        }




    }


    private class TextWarrior {

        RTFont f;

        int color;

        public TextWarrior() {
            init();
        }

        void init() {
            System.out.println("Building Font...");
            Font font = new Font("Helvetica", 0, 12);
            f = FontFactory.create(font);
            System.out.println("...Font generated");
        }

        public void start(RTGraphics g) {
            f.setGraphics(g);
        }

        public void setColor(Color color) {
            setColor(color.getRGB());
        }

        public void setColor(int color) {
            this.color = color;
        }

        public void drawString(String str, int x, int y) {
            f.drawString(str,x,y,color);
        }

        public void finish() {
            f.flush(); // Release our reference to the graphics...
        }

    }

}
