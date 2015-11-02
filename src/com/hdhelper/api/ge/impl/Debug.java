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

    public boolean useNewGraphics = true;
    public boolean benchmark = false;

    @Override
    public void paint(RTGraphics g) {

        if(useNewGraphics) {
            long i = System.nanoTime();
            drawNewDebug(g);
            long f = System.nanoTime();
            if(benchmark) {
                System.out.println("[Benchmark]: RuneTek Graphics:" + ((f-i)/1E9D) + "MILISECONDS");
            }
        } else {
            Graphics2D g0 = g.getGraphics();
            long i = System.nanoTime();
            drawOldDebug(g0);
            long f = System.nanoTime();
            if(benchmark) {
                System.out.println("[Benchmark]: Java Graphics" + ((f-i)/1E9D) + "MILISECONDS");
            }
        }

    }

    void drawNewDebug(RTGraphics g) {

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

                RSPlayerConfig cfg = p.getConfig();
                warrior.drawString(p.getName() + " | Lvl:" + p.getCombatLevel() + " | Anim:" + p.getAnimation() + " | Target:" + p.getTargetIndex() + " | Orientation:" + p.getOrientation(), P.x, P.y);
                if(cfg == null) {
                    warrior.drawString("cfg==null", P.x, P.y-15);
                } else {
                    warrior.drawString("Z=" + p.getZ() + " | Female=" + cfg.isFemale() + " | Equip = " + Arrays.toString(cfg.getEquipment()) + " | EquipColors = " + Arrays.toString(cfg.getEquipmentColors())  , P.x, P.y-15);
                }
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
                    warrior.drawString("[" + rx + "_" + ry + "] => " + Arrays.toString(key), lol.x(),lol.y());
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

            // But it does not hert to help the garbage collector a bit
            warrior.finish();
        }

    }

    static void drawOldDebug(Graphics2D g0) {

        RSClient client = Main.client;

        g0.setColor(Color.GREEN);

        int base_x = 15;
        int base_y = 20;
        int gap = 15;
        int i = 0;


        assert client != null;

        final int bx = client.getRegionBaseX();
        final int by = client.getRegionBaseY();

        final int floor = client.getFloor();

        //Local debug:
        RSPlayer me = client.getMyPlayer();
        if (me != null) {

            int rx = me.getRegionX();
            int ry = me.getRegionY();
            int x = bx + rx;
            int y = by + ry;

            g0.drawString("Name:" + me.getName(), base_x, base_y + gap * i++);
            g0.drawString("X:" + x + "(" + rx + ")", base_x, base_y + gap * i++);
            g0.drawString("Y:" + y + "(" + ry + ")", base_x, base_y + gap * i++);

        }

        //Render Players:
        g0.setColor(Color.RED);
        for (RSPlayer p : client.getPlayers()) {
            if (p == null) continue;
            int rx = p.getRegionX();
            int ry = p.getRegionY();
            W2S.draw3DBox(floor, rx, ry, p.getHeight(), g0);
            Point P = W2S.tileToViewport(p.getStrictX(), p.getStrictY(), floor, p.getHeight());
            if (P.x == -1) continue;
            g0.drawString(p.getName() + " | Lvl:" + p.getCombatLevel() + " | Anim:" + p.getAnimation() + " | Target:" + p.getTargetIndex() + "Orintation:" + p.getOrientation(), P.x, P.y);
        }

        //Render Npcs:
        g0.setColor(Color.BLUE);
        for (RSNpc p : client.getNpcs()) {
            if (p == null) continue;
            int rx = p.getRegionX();
            int ry = p.getRegionY();
            W2S.draw3DBox(floor, rx, ry, p.getHeight(), g0);
            if (p.getDef() == null) continue;
            Point P = W2S.tileToViewport(p.getStrictX(), p.getStrictY(), floor, p.getHeight());
            if (P.x == -1) continue;
            RSNpcDefintion def = p.getDef();
            g0.drawString(def.getName() + " | Anim:" + p.getAnimation() + " | Target:" + p.getTargetIndex() + "| Orintation:" + p.getOrientation(), P.x, P.y);
        }


        //Render GroundItems:
        g0.setColor(Color.YELLOW);
        RSDeque[][] items = client.getGroundItems()[floor];
        for (int x = 0; x < 104; x++) {
            for (int y = 0; y < 104; y++) {
                RSDeque pile = items[x][y];
                if (pile == null) continue;
                RSNode[] nodes = pile.toArray();
                for (RSNode node : nodes) {
                    RSGroundItem g = (RSGroundItem) node;
                    W2S.draw3DBox(floor, x, y, g.getHeight(), g0);
                    final int sx = (x << 7) + 64;
                    final int sy = (y << 7) + 64;
                    int id = g.getId();
                    RSItemDefinition def = client.getItemDef(id);
                    Point P = W2S.tileToViewport(sx, sy, floor, g.getHeight());
                    if (P.x == -1) continue;
                    g0.drawString(def.getName() + "(" + id + ") x " + g.getQuantity(), P.x, P.y);
                }
            }
        }


        g0.setColor(Color.GREEN);

        g0.drawString("Floor:" + client.getFloor(), base_x, base_y + gap * i++);
        i += 1;

        g0.drawString("Pitch:" + client.getPitch(), base_x, base_y + gap * i++);
        g0.drawString("Yaw:" + client.getYaw(), base_x, base_y + gap * i++);
        g0.drawString("CamX:" + client.getCameraX(), base_x, base_y + gap * i++);
        g0.drawString("CamY:" + client.getCameraY(), base_x, base_y + gap * i++);
        g0.drawString("CamZ:" + client.getCameraZ(), base_x, base_y + gap * i++);
        i += 1;

        g0.drawString("Scale:" + client.getViewportScale(), base_x, base_y + gap * i++);
        g0.drawString("Width:" + client.getViewportWidth(), base_x, base_y + gap * i++);
        g0.drawString("Height:" + client.getViewportHeight(), base_x, base_y + gap * i++);
        i += 1;

        // XTEA Debug:
        int[][] keys = client.getKeys();
        int[] chunks = client.getChunkIds();
        if (chunks != null && keys != null) {
            int num_chunks = keys.length;
            g0.drawString("Chunks:" + num_chunks, base_x, base_y + gap * i++);
            for (int k = 0; k < num_chunks; k++) {
                int chunkId = chunks[k];
                int[] key = keys[k];
                int rx = chunkId >> 8 & 255;
                int ry = chunkId & 255;
                g0.drawString("<" + rx + "_" + ry + "> => " + Arrays.toString(key), base_x, base_y + gap * i++);
            }
        }
        i += 1;

        //Equipment Debug:
        for (Equipment.Slot s : Equipment.Slot.values()) {
            g0.drawString(s.name() + ":" + s.get(), base_x, base_y + gap * i++);
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
