package com.hdhelper.ui;

import com.hdhelper.Context;
import com.hdhelper.Main;
import com.hdhelper.peer.RSClient;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final int WIDTH = 781;
    private static final int HEIGHT = 563;

    public MainFrame() {
        super("HD Helper");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dim.width / 2) - (WIDTH / 2), (dim.height / 2) - (HEIGHT / 2));
    }



    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                setJMenuBar(new Bar());

                Applet a = bootModClient();


                setContentPane(a);

                pack();
                revalidate();
                setVisible(true);


            }
        });
    }


    private static Applet bootModClient() {
        try {
            Context c = Context.create();
            Main.client = (RSClient) c.applet;
            return c.applet;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }



    private void build() {


    }

}



