package com.hdhelper;

import com.hdhelper.agent.peer.RSClient;
import com.hdhelper.ui.MainFrame;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

    public static RSClient client;

    public static void main(String... args) throws IOException, InterruptedException {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("PopupMenu.consumeEventOnClose", Boolean.TRUE);
        } catch (final ClassNotFoundException ignored) {
        } catch (final UnsupportedLookAndFeelException ignored) {
        } catch (final InstantiationException ignored) {
        } catch (final IllegalAccessException ignored) {
        }

        SwiftManager.getManager().start();
       // MainFrame frame = new MainFrame();
       // frame.start();


      //  new Ok();
    }


    public static class Ok extends JFrame {

        public Ok() {
            super("FUN");
            setSize(500, 500);

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
