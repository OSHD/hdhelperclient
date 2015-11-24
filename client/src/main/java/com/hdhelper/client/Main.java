package com.hdhelper.client;

import com.hdhelper.agent.services.RSClient;
import com.hdhelper.client.cni.ClientNative;
import com.hdhelper.client.frame.components.varbos.StatsTab;
import com.hdhelper.client.frame.util.TimeStamp;
import com.hdhelper.client.theme.NimbusTheme;
import com.hdhelper.client.ui.MainFrame;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main { //Noughty works

    static {
        //Force the CNI to be initialized before calling any other code.
        //This enforces the guarantee of RuneTime accessibility of all game services.
        ClientNative.get();
    }


    public static RSClient client;

    public static long startTime;


    public static void run() throws Exception{
        Main.main();
    }

    public static void main(String... args) throws IOException, InterruptedException, InvocationTargetException { //NOOOOB
        System.out.println("Main Start");
        startTime = System.currentTimeMillis();
        final TimeStamp t = new TimeStamp();
        StatsTab.reportPerformance((int) ((System.currentTimeMillis() - t.duration(false, ""))));

        SwingUtilities.invokeAndWait(new Runnable() {
        	@Override
        	public void run() {
		        try {
		        	new NimbusTheme();
			        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			            if ("Nimbus".equals(info.getName())) {
			                UIManager.setLookAndFeel(info.getClassName());
			                break;
			            }
			        }
		        } catch (Exception e) {
				    // If Nimbus is not available, you can set the GUI to another look and feel.
				}
        	}
        });
        SwiftManager.getManager().start();
        // MainFrame frame = new MainFrame();
        // frame.start();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("PopupMenu.consumeEventOnClose", Boolean.TRUE);
        } catch (final ClassNotFoundException ignored) {
        } catch (final UnsupportedLookAndFeelException ignored) {
        } catch (final InstantiationException ignored) {
        } catch (final IllegalAccessException ignored) {
        }

     //   SwiftManager.getManager().start();
         MainFrame frame = new MainFrame();
         frame.start();
        //  new Ok();
    }

    /**
     * Gets the startTime.
     *
     * @return the startTime
     */
    public static long getStartTime() {
        return startTime;
    }

    /**
     * Sets the bastartTime.
     *
     * @param startTime the startTime to set.
     */
    public static void setStartTime(long startTime) {
        Main.startTime = startTime;
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
        Image image = new BufferedImage(char_with, char_height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, char_with, char_height);
        graphics.setColor(Color.white);
        graphics.setFont(f);
        graphics.drawString((new StringBuilder()).append(c).append("").toString(), 0, y);

        g0.drawImage(image, 50, 50, null);

    }

}
