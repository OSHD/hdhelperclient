package com.hdhelper.agent;

import com.hdhelper.Main;
import com.hdhelper.api.W2S;
import com.hdhelper.peer.RSClient;
import com.hdhelper.peer.RSNpc;
import com.hdhelper.peer.RSNpcDefintion;
import com.hdhelper.peer.RSPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ClientCanvas extends Canvas {

    private BufferedImage rawImage;
    private BufferedImage backBuffer;

    private Canvas buddy;

    public ClientCanvas() {
        super();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width  = (int) screen.getWidth();
        int height = (int) screen.getHeight();
        rawImage   = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        backBuffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    }

    void draw0(Graphics2D g2) {

        g2.setColor(Color.GREEN);

        int base_x = 15;
        int base_y = 20;
        int gap = 15;
        int i = 0;


        RSClient client = Main.client;

        final int bx = client.getRegionBaseX();
        final int by = client.getRegionBaseY();

        final int floor = client.getFloor();

        RSPlayer me = client.getMyPlayer();
        if(me != null) {

            int rx = me.getRegionX();
            int ry = me.getRegionY();
            int x = bx + rx;
            int y = by + ry;

            g2.drawString( "Name:" + me.getName(), base_x, base_y + gap * i++);
            g2.drawString( "X:" + x + "(" + rx + ")", base_x, base_y + gap * i++);
            g2.drawString("Y:" + y + "(" + ry + ")", base_x, base_y + gap * i++);



        }

        g2.setColor(Color.RED);
        for(RSPlayer p : client.getPlayers()) {
            if(p == null) continue;
            int rx = p.getRegionX();
            int ry = p.getRegionY();
            W2S.draw3DBox(floor,rx,ry,p.getHeight(),g2);
            Point P = W2S.tileToViewport(p.getStrictX(),p.getStrictY(),floor,p.getHeight());
            if(P.x == -1) continue;
            g2.drawString(p.getName() + " | Lvl:" + p.getCombatLevel() + " | Anim:" + p.getAnimation() + " | Target:" + p.getTargetIndex(),P.x,P.y);
        }

        g2.setColor(Color.BLUE);
        for(RSNpc p : client.getNpcs()) {
            if(p == null) continue;
            int rx = p.getRegionX();
            int ry = p.getRegionY();
            W2S.draw3DBox(floor,rx,ry,p.getHeight(),g2);
            if(p.getDef()==null) continue;
            Point P = W2S.tileToViewport(p.getStrictX(), p.getStrictY(), floor, p.getHeight());
            if(P.x == -1) continue;
            RSNpcDefintion def = p.getDef();
            g2.drawString(def.getName() + " | Anim:" + p.getAnimation() + " | Target:" + p.getTargetIndex(), P.x, P.y);
        }


        g2.setColor(Color.GREEN);

        g2.drawString( "Floor:" + client.getFloor(),base_x,base_y + gap * i++) ;
        g2.drawString( "Pitch:" + client.getPitch(), base_x, base_y + gap * i++);
        g2.drawString( "Yaw:" + client.getYaw(), base_x, base_y + gap * i++);
        g2.drawString( "CamX:" + client.getCameraX(), base_x, base_y + gap * i++);
        g2.drawString( "CamY:" + client.getCameraY(), base_x, base_y + gap * i++);
        g2.drawString( "CamZ:" + client.getCameraZ(), base_x, base_y + gap * i++);
        g2.drawString( "Scale:" + client.getViewportScale(), base_x, base_y + gap * i++);
        g2.drawString( "Width:" + client.getViewportWidth(), base_x, base_y + gap * i++);
        g2.drawString( "Height:" + client.getViewportHeight(), base_x, base_y + gap * i++);



    }

    @Override
    public Graphics getGraphics() {

        Graphics g = super.getGraphics();

        Graphics2D paint = (Graphics2D) backBuffer.getGraphics();
        paint.clearRect(0, 0, getWidth(), getHeight());
        paint.drawImage(rawImage, 0, 0, null);
        draw0(paint);
        paint.dispose();
        g.drawImage(backBuffer, 0, 0, null);
        backBuffer.flush();
        g.dispose();
        Graphics rawG = rawImage.getGraphics();
        rawImage.flush();

        return rawG;

    }

    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);
    }

}

