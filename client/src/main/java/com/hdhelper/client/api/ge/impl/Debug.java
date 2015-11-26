package com.hdhelper.client.api.ge.impl;

import com.hdhelper.agent.ref.Ref;
import com.hdhelper.agent.ref.RefSet;
import com.hdhelper.agent.services.*;
import com.hdhelper.client.Environment;
import com.hdhelper.client.Main;
import com.hdhelper.client.api.*;
import com.hdhelper.client.api.db.ItemValue;
import com.hdhelper.client.api.db.ItemValueDatabase;
import com.hdhelper.client.api.db.ItemValueDatabases;
import com.hdhelper.client.api.ge.*;

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

    static int camX;
    static int camY;
    static boolean[][] vismap;
    static int renderMinX;
    static int renderMinY;
    static int renderMaxX;
    static int renderMaxY;

    static boolean cull(int rx, int ry) {
        rx -= renderMinX;
        ry -= renderMinY;
        return !(rx > 0 && ry < 50 && ry > 0 && ry < 50) || !vismap[rx][ry];
    }


    private final RefSet<RSPlayer> queue = new RefSet<RSPlayer>();

    private Ref<RSPlayer> pref;

    void drawNewDebug(RTGraphics g) {

    //    System.out.println(Main.client.getBootState());
        if(!Game.isLoaded()) return;

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

        /*    RSPlayer meee = client.getMyPlayer();

            long a = System.nanoTime();
            for(RSPlayer p : client.getPlayers()) {
                if(p == null) continue;

                queue.add(p);


            }


            long b = System.nanoTime();
            System.out.println("" + (b-a));

            int n = 0;
            for(RSPlayer p : queue) {
                n++;
               // System.out.println("" + (n++) + p);
            }

            System.out.println(n);

            final AtomicInteger integer = new AtomicInteger();
            queue.accept(new RefVisitor<RSPlayer>() {
                @Override
                public boolean visit(Ref<RSPlayer> ref) {
                    integer.incrementAndGet();
                  //  System.out.println("YE:" + ref.get());
                    ref.unQueue();
                    return true;
                }
            });

            System.out.println(integer);


            queue.clear();

            queue.accept(new RefVisitor<RSPlayer>() {
                @Override
                public boolean visit(Ref<RSPlayer> ref) {
                    System.out.println("YE2:" + ref.get());
                    return true;
                }
            });
           *//* if(pref == null && meee != null) {
                pref = queue.add(meee);
                queue.add(meee);
                System.out.println(pref.get());
                queue.accept(new RefVisitor<RSPlayer>() {
                    @Override
                    public boolean visit(Ref<RSPlayer> ref) {
                        System.out.println("YE:" + ref.get());
                        return true;
                    }
                });
                meee.destroy();
                System.out.println(pref.get());

            }*/

            assert client != null;

            RSLandscape landscape = client.getLandscape();

            final int bx = client.getRegionBaseX();
            final int by = client.getRegionBaseY();

            final int floor = client.getFloor();



            RSLandscapeTile[][] ls_tiles = landscape.getTiles()[floor];


            final int pitch = client.getPitch();
            final int yaw = client.getYaw();

            if(pitch==0&&yaw==0) return;

            vismap = landscape.getVisibilityMap()[(pitch-128)/32][yaw/64];

            camX = client.getCameraX()/128;
            camY = client.getCameraY()/128;

            renderMinX = camX - 25;
            renderMinY = camY - 25;
            renderMaxX = camX + 25;
            renderMaxY = camY + 25;

            if(renderMinX < 0)   renderMinX = 0;
            if(renderMinY < 0)   renderMinY = 0;
            if(renderMinX > 103) renderMaxX = 103;
            if(renderMinY > 103) renderMaxY = 103;


            warrior.drawString("Logged in:" + Game.isLoggedIn(), lol.x(), lol.y());

            if(Environment.RENDER_PLAYER_DEBUG) {
                warrior.setColor(Color.RED);
                for (RSPlayer p : client.getPlayers()) {
                    if (p == null || p.getHeight() == 1000) continue;
                    int rx = p.getStrictX() >> 7;
                    int ry = p.getStrictY() >> 7;
                    if(cull(rx,ry)) continue;
                    W2S.draw3DBox(floor, rx, ry, p.getHeight(), g, Color.RED.getRGB());
                    Point P = W2S.tileToViewport(p.getStrictX(), p.getStrictY(), floor, p.getHeight());
                    if (P.x == -1) continue;
                    RSPlayerConfig cfg = p.getConfig();
                    warrior.drawString(p.getName() + " | Lvl:" + p.getCombatLevel() + " | Anim:" + p.getAnimation() + " | Target:" + p.getTargetIndex() + " | Orientation:" + p.getOrientation(), P.x, P.y);
                    if(cfg == null) {
                        warrior.drawString("cfg==null", P.x, P.y-15);
                    } else {
                        int i = 1;
                        final int s = 15;
                        warrior.drawString("Z=" + p.getZ() + " | Female=" + cfg.isFemale(),P.x, P.y-s*i++);
                        warrior.drawString("Equip = " + Arrays.toString(cfg.getEquipment()) ,P.x, P.y-s*i++);
                        warrior.drawString("EquipColors = " + Arrays.toString(cfg.getEquipmentColors()), P.x, P.y-s*i++);
                        boolean hp_bar_showing = p.getHealthBarCycle() > client.getEngineCycle();
                        warrior.drawString("BarShowing:" + hp_bar_showing + " HP:(" + p.getHitpoints() + "/" + p.getMaxHitpoints() + ")", P.x, P.y-s*i++);
                        warrior.drawString("IdleAnim:" + p.getIdleAnimation() + ",WalkAnim:" + p.getWalkAnimation() + ",RunAnim:" + p.getRunAnimation() + ",2341:" + p.getAnint2341(), P.x, P.y-s*i++);
                    }
                    W2S.drawStrictPoint(floor, p.getStrictX(), p.getStrictY(), g, Color.BLUE.getRGB(), Color.GREEN.getRGB());
                }
            }


            if(Environment.RENDER_NPC_DEBUG) {
                warrior.setColor(Color.BLUE);
                for (RSNpc p : client.getNpcs()) {
                    if (p == null || p.getHeight() == 1000) continue;
                    int rx = p.getStrictX() >> 7;
                    int ry = p.getStrictY() >> 7;
                    if(cull(rx,ry)) continue;
                    W2S.draw3DBox(floor, rx, ry, p.getHeight(), g, Color.BLUE.getRGB());
                    if (p.getDef() == null) continue;
                    Point P = W2S.tileToViewport(p.getStrictX(), p.getStrictY(), floor, p.getHeight());
                    if (P.x == -1) continue;
                    RSNpcDefinition def = p.getDef();
                    warrior.drawString(def.getName() + " | Lvl:" + def.getCombatLevel() + " | Anim:" + p.getAnimation() + " | Target:" + p.getTargetIndex() + "| Orintation:" + p.getOrientation(), P.x, P.y);
                    boolean hp_bar_showing = p.getHealthBarCycle() > client.getEngineCycle();
                    warrior.drawString("BarShowing:" + hp_bar_showing + " HP:(" + p.getHitpoints() + "/" + p.getMaxHitpoints() + ")", P.x, P.y-15);

                    W2S.drawStrictPoint(floor, p.getStrictX(), p.getStrictY(), g, Color.GREEN.getRGB(), Color.RED.getRGB());

                }
            }


            ItemValueDatabase db = ItemValueDatabases.getGeValueDatabase();

            if(Environment.RENDER_GROUND_ITEM_DEBUG) {
                //Render GroundItems:
                warrior.setColor(Color.YELLOW);
                RSDeque[][] items = client.getGroundItems()[floor];
                for (int x = renderMinX; x < renderMaxX; ++x) {
                    for (int y = renderMinY; y < renderMaxY; ++y) {
                        if(!vismap[x-camX+25][y-camY+25]) continue;
                        RSDeque pile = items[x][y];
                        if (pile == null) continue;
                        RSItemPile pile0 = ls_tiles[x][y].getItemPile();
                        assert pile0 != null;
                        if(pile0.getHeight() == 1000) continue;
                        RSNode[] nodes = Deque.toArray(pile);
                        for (RSNode node : nodes) {
                            RSGroundItem gi = (RSGroundItem) node;
                            W2S.draw3DBox(floor, x, y,  pile0.getHeight(),pile0.getHeight() + gi.getHeight(), g, Color.YELLOW.getRGB());
                            final int sx = (x << 7) + 64;
                            final int sy = (y << 7) + 64;
                            int id = gi.getId();
                            RSItemDefinition def = client.getItemDef(id);
                            Point P = W2S.tileToViewport(sx, sy, floor, pile0.getHeight() + gi.getHeight() );
                            if (P.x == -1) continue;

                            ItemValue v = db.getValue(id);
                            String str = v.info();

                            warrior.drawString(def.getName() + "(" + id + ",price=" + str + ") x " + gi.getQuantity(), P.x, P.y);


                        }
                    }
                }
            }

            for (int x = renderMinX; x < renderMaxX; ++x) {
                for (int y = renderMinY; y < renderMaxY; ++y) {
                    if(!vismap[x-camX+25][y-camY+25]) continue;

                    RSLandscapeTile tile = ls_tiles[x][y];
                    if(tile == null) continue;


                    if(Environment.RENDER_BOUNDARY_DEBUG) {

                        RSBoundary boundary = tile.getBoundary();
                        if(boundary != null) {

                            final int uid = boundary.getUid();
                            final int id = UID.getEntityID(uid);
                            final boolean is_intractable = UID.isIntractable(uid); // Doors/Functional boundaries are defined by this statement.
                            final boolean is_dynamic = // Does it animate or transform?
                                       boundary.getEntityA() instanceof RSDynamicObject
                                    || boundary.getEntityB() instanceof RSDynamicObject;

                            int height = 0;
                            if(boundary.getEntityA() != null) height = boundary.getEntityA().getHeight();
                            if(boundary.getEntityB() != null) height = boundary.getEntityB().getHeight();

                            if(height == 1000) continue;


                          //  W2S.draw3DBox(floor, x, y, height, g, Color.PINK.getRGB());

                            RSObjectDefinition def = client.getObjectDef(id);
                            if(is_dynamic) {
                                RSObjectDefinition t = def.transform();
                                if(t != null) def = t;
                            }


                            Point P = W2S.tileToViewport(boundary.getStrictX(),boundary.getStrictY(), floor, height);


                            if (P.x == -1) continue;
                            warrior.drawString(def.getName() + "(" + id + ") | Functional: " + is_intractable + " | Dynamic: " + is_dynamic, P.x, P.y);

                            W2S.drawStrictPoint(floor,boundary.getStrictX(),boundary.getStrictY(), g, Color.GREEN.getRGB(), Color.RED.getRGB());

                            Point B = W2S.tileToViewport(boundary.getStrictX(),boundary.getStrictY(), floor, 0);
                            g.drawLine(P,B,Color.BLUE.getRGB());
                        }

                    }

                    if(Environment.RENDER_BOUNDARY_DECO_DEBUG) {

                        RSBoundaryDecoration boundary = tile.getBoundaryDecoration();
                        if(boundary != null) {

                            final int uid = boundary.getUid();
                            final int id = UID.getEntityID(uid);
                            final boolean is_intractable = UID.isIntractable(uid); // Doors/Functional boundaries are defined by this statement.
                            final boolean is_dynamic = // Does it animate or transform?
                                    boundary.getEntityA() instanceof RSDynamicObject
                                            || boundary.getEntityB() instanceof RSDynamicObject;

                            int height = 0;
                            if(boundary.getEntityA() != null) height = boundary.getEntityA().getHeight();
                            if(boundary.getEntityB() != null) height = boundary.getEntityB().getHeight();

                            if(height == 1000) continue;



                            //  W2S.draw3DBox(floor, x, y, height, g, Color.PINK.getRGB());

                            RSObjectDefinition def = client.getObjectDef(id);
                            if(is_dynamic) {
                                RSObjectDefinition t = def.transform();
                                if(t != null) def = t;
                            }


                            Point P = W2S.tileToViewport(boundary.getStrictX(),boundary.getStrictY(), floor, height);


                            if (P.x == -1) continue;
                            warrior.drawString(def.getName() + "(" + id + ") | Functional: " + is_intractable + " | Dynamic: " + is_dynamic, P.x, P.y);

                            W2S.drawStrictPoint(floor,boundary.getStrictX(),boundary.getStrictY(), g, Color.GREEN.getRGB(), Color.RED.getRGB());
                            W2S.drawStrictPoint(floor, boundary.getStrictX() + boundary.getInsetX(), boundary.getStrictY() + boundary.getInsetY(), g, Color.GREEN.getRGB(), Color.BLUE.getRGB());


                        }

                    }

                    if(Environment.RENDER_TILE_DECO_DEBUG) {
                        RSTileDecoration boundary = tile.getTileDecoration();
                        if(boundary != null) {

                            final int uid = boundary.getUid();
                            final int id = UID.getEntityID(uid);
                            final boolean is_dynamic = // Does it animate or transform?
                                    boundary.getEntity() instanceof RSDynamicObject;

                            int height = boundary.getEntity().getHeight();
                            if(height == 1000) continue;

                            W2S.draw3DBox(floor, x, y, height, g, Color.PINK.getRGB());

                            RSObjectDefinition def = client.getObjectDef(id);
                            if(is_dynamic) {
                                RSObjectDefinition t = def.transform();
                                if(t != null) def = t;
                            }

                            Point P = W2S.tileToViewport(boundary.getStrictX(),boundary.getStrictY(), floor, height);
                            if (P.x == -1) continue;
                            warrior.drawString(def.getName() + "(" + id + ") | Dynamic: " + is_dynamic, P.x, P.y);

                        }

                    }

                    if(Environment.RENDER_OBJECT_DEBUG) {
                        RSEntityMarker[] markers = tile.getMarkers();
                        for(RSEntityMarker m : markers) {
                            if(m == null) continue;
                            if(UID.getEntityType(m.getUid()) != UID.TYPE_OBJECT) continue;
                            if(m.getRootRegionX() != x || m.getRootRegionY() != y) continue;
                            drawEntityBounds(m,Color.GREEN.getRGB(),Color.BLUE.getRGB(),g);
                            int id = UID.getEntityID(m.getUid());
                            RSObjectDefinition def = client.getObjectDef(id);
                            Point P = W2S.tileToViewport(m.getStrictX(),m.getStrictY(),floor,m.getEntity().getHeight());
                            warrior.drawString(def.getName() + "(" + id + ")", P.x, P.y);
                        }
                    }

                }
            }



            if(Environment.RENDER_MISC_DEBUG) {

                warrior.setColor(Color.GREEN);

                warrior.drawString("FPS:" + client.getFps(), lol.x(), lol.y());

                RSPlayer me = client.getMyPlayer();
                if (me != null) {

                    warrior.setColor(Color.GREEN);

                    int rx = me.getStrictX() >> 7;
                    int ry = me.getStrictY() >> 7;
                    int x = bx + rx;
                    int y = by + ry;

                    warrior.drawString("Name:" + me.getName(),lol.x(),lol.y());
                    warrior.drawString("X:" + x + "(" + rx + ")",lol.x(), lol.y());
                    warrior.drawString("Y:" + y + "(" + ry + ")",lol.x(), lol.y());

                }

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

                lol.i++;
                //GPI:
         //       warrior.drawString("PlayerCount:" + client.getPlayerCount(), lol.x(), lol.y());
          //      warrior.drawString("PlayerIndices:" + Arrays.toString(Arrays.copyOf(client.getPlayerIndices(), client.getPlayerCount())), lol.x(), lol.y());


            }

       //     warrior.drawString("<img=0><col=76EE00>5M</col> <img=1><col=76EE00>10M</col> ", HDCanvas.mouseX, HDCanvas.mouseY);

            warrior.draw();



           /* if(money == null) {
                RSImage img = client.getItemImage(995,Integer.MAX_VALUE,1,0,2,false);
                money = RTImage.create(img,true);
            }

            money.setGraphics(g);
            money.f(ClientCanvas.mouseX-money.getWidth(),ClientCanvas.mouseY-money.getHeight()); //huez*/

            // Keep this:

         /*   final int x = 100;
            final int y = 100;
            final int w = ClientCanvas.mouseX - x;
            final int h = ClientCanvas.mouseY - y;
            if(w <= 0 || h <= 0) return;
            RTFont f = warrior.f;
            f.fillRectangle(x, y, w, h, Color.BLACK.getRGB(), 128);
            int k = f.drawWordWrap("US ER N AME: awdadawdaw dawd awd aw daw dawda wdccc cc cc cccccccccccccccccc", x, y, w, h, 16777215, -1, RTFont.TEXT_LAYOUT_CENTER,RTFont.ROW_LAYOUT_CENTER, 0);
            warrior.drawString("" + k, x,y);*/

        } catch (Exception e) {
           e.printStackTrace();
        } finally {

            warrior.finish(); //Help the GC
        }

    }

    private static RTImage money = null;



    static void drawEntities(RSLandscapeTile t, int filter_type, RTGraphics g) {
        for(RSEntityMarker m : t.getMarkers()) {
            if(m == null) continue;
            if(m.getRootRegionX() != t.getRegionX()) continue;
            if(m.getRootRegionY() != t.getRegionY()) continue;
            if(UID.getEntityType(m.getUid()) != filter_type) continue;
            drawEntityBounds(m,Color.GREEN.getRGB(),Color.BLUE.getRGB(),g);
        }
    }

    static void drawEntityBounds(RSEntityMarker m, int bounds_color, int wall_color, RTGraphics g) {

        int x = m.getRootRegionX();
        int y = m.getRootRegionY();
        int floor = m.getFloorLevel();

        int mx = m.getMaxX()+1;
        int my = m.getMaxY()+1;

        int width = m.getMaxX()-x+1;
        int height = m.getMaxY()-y+1;

        int h = m.getEntity().getHeight();
        if(h == 1000) return;

        W2S.draw3DBox(floor, x, y, width, height, h, wall_color, g);

        Point A = W2S.regionToViewport(x, y, floor, 0);
        if(A == null) return;
        Point B = W2S.regionToViewport(x, my, floor, 0);
        if(B == null) return;
        Point C = W2S.regionToViewport(mx, my, floor, 0);
        if(C == null) return;
        Point D = W2S.regionToViewport(mx, y, floor, 0);
        if(D == null) return;

        Point E = W2S.regionToViewport(mx, my, floor,h);
        if(E == null) return;


        g.drawLine(A,B,bounds_color);
        g.drawLine(A,C,bounds_color);
        g.drawLine(A, D, bounds_color);
        g.drawLine(A, E, bounds_color);

        g.drawSquareDot(A, Color.YELLOW.getRGB());
        g.drawSquareDot(B, Color.RED.getRGB());
        g.drawSquareDot(C, Color.RED.getRGB());
        g.drawSquareDot(D,Color.RED.getRGB());

        g.drawSquareDot(E,Color.RED.getRGB());

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

            int rx = me.getStrictX() >> 7;
            int ry = me.getStrictY() >> 7;
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
            int rx = p.getStrictX() >> 7;
            int ry = p.getStrictY() >> 7;
            W2S.draw3DBox(floor, rx, ry, p.getHeight(), g0);
            Point P = W2S.tileToViewport(p.getStrictX(), p.getStrictY(), floor, p.getHeight());
            if (P.x == -1) continue;
            g0.drawString(p.getName() + " | Lvl:" + p.getCombatLevel() + " | Anim:" + p.getAnimation() + " | Target:" + p.getTargetIndex() + "Orintation:" + p.getOrientation(), P.x, P.y);
        }

        //Render Npcs:
        g0.setColor(Color.BLUE);
        for (RSNpc p : client.getNpcs()) {
            if (p == null) continue;
            int rx = p.getStrictX() >> 7;
            int ry = p.getStrictY() >> 7;
            W2S.draw3DBox(floor, rx, ry, p.getHeight(), g0);
            if (p.getDef() == null) continue;
            Point P = W2S.tileToViewport(p.getStrictX(), p.getStrictY(), floor, p.getHeight());
            if (P.x == -1) continue;
            RSNpcDefinition def = p.getDef();
            g0.drawString(def.getName() + " | Anim:" + p.getAnimation() + " | Target:" + p.getTargetIndex() + "| Orintation:" + p.getOrientation(), P.x, P.y);
        }


        //Render GroundItems:
        g0.setColor(Color.YELLOW);
        RSDeque[][] items = client.getGroundItems()[floor];
        for (int x = 0; x < 104; x++) {
            for (int y = 0; y < 104; y++) {
                RSDeque pile = items[x][y];
                if (pile == null) continue;
                RSNode[] nodes = Deque.toArray(pile);
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

        TextBlocker b = new TextBlocker(2000);

        void init() {
            System.out.println("Building Font...");
            Font font = new Font("Helvetica", 0, 14);
            f = GlyphFactory.create(font);

            RSImage ge = CachedImaged.getImage(CachedImaged.GRAND_EXCHANGE_OFFER);
            RSImage ha = CachedImaged.getImage(CachedImaged.HIGH_ALCH);

            RTImage ge0 = RTImage.create(ge,false);
            RTImage ha0 = RTImage.create(ha,false);

            f.setImages(new RTImage[] { ge0, ha0 });

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

          //  int w = f.getStringWidth(str)/2;
          //  int h = f.getBaseLine();

          //  b.put(x,y,w,h,str);

            f.drawString(str, x,y,color);
        }

        public void draw() {
         /*   while (b.hasNext()) {
                b.next();
                int x = b.getX();
                int y = b.getY();
          //      System.out.println(x + "," + y);
                String str = (String) b.getEntry();
                f.drawString(str,x,y,color);
            }*/
        }

        public void finish() {
            b.reset();
            f.flush(); // Release our reference to the graphics...
        }

    }



}
