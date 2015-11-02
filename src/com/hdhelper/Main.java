package com.hdhelper;

import com.hdhelper.peer.RSClient;
import com.hdhelper.ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

    public static RSClient client;


    public static class MySecurityManager extends SecurityManager {
        public void checkRead(String s) {
            Class c[] = getClassContext();
            for (int i = 0; i < c.length; i++) {
                String name = c[i].getName();
                System.out.println(name);

            }
        }


        /**
         *
         * Node implements RSNode {
         *
         *     @BField(name = "duel_next");
         *     Node next;
         *     @BField
         *     Node prev;
         *
         *
         *
         *     @BMethod;
         *     public void remove(Node A, Node b) {
         *
         *     }
         *
         *
         *     public void cool() {
         *         remove(next,prev);
         *     }
         *
         *     @Override
         *     public RSNode getNext() {
         *         return next;
         *     }
         *
         *     @Override
         *     public RSNode getPrevious() {
         *         return prev;
         *     }
         *
         *
         *
         *
         * }
         *
         *
         *
         */
    }



    public static void main(String... args) throws IOException, InterruptedException {








       System.out.println("Start");

        try {
           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          //  UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel");
            UIManager.put("PopupMenu.consumeEventOnClose", Boolean.TRUE);
        } catch (final ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ignored) {
        }

        MainFrame frame = new MainFrame();
        frame.start();


      //  new Ok();
    }


    public static class Ok extends JFrame {

        public Ok() {
            super("FUN");
            setSize(500, 500);
            revalidate();
            setVisible(true);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            System.out.println("OK");
            other(g);
        }

    }

    public static void other(Graphics g0) {
        Font f = g0.getFont().deriveFont(32f);
        FontMetrics fontmetrics = g0.getFontMetrics(f);

        char c = 'B';

        int char_with = fontmetrics.charWidth(c);
        int y = fontmetrics.getMaxAscent();
        int char_height = fontmetrics.getMaxAscent() + fontmetrics.getMaxDescent();
        Image image = new BufferedImage(char_with,char_height,BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, char_with, char_height);
        graphics.setColor(Color.white);
        graphics.setFont(f);
        graphics.drawString((new StringBuilder()).append(c).append("").toString(), 0, y);

        g0.drawImage(image,50,50,null);
    }

}
