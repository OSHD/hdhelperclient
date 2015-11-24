package com.hdhelper.client.ui;

import com.hdhelper.agent.CNI;
import com.hdhelper.agent.services.RSClient;
import com.hdhelper.agent.services.RSImage;
import com.hdhelper.agent.services.RSWidget;
import com.hdhelper.client.Main;
import com.hdhelper.client.api.ItemTable;
import com.hdhelper.client.api.action.ActionAdapter;
import com.hdhelper.client.api.action.tree.Action;
import com.hdhelper.client.api.action.tree.ExamineItemAction;
import com.hdhelper.client.api.ge.RTImage;
import com.hdhelper.client.cni.ClientNative;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

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

                final Applet a = bootModClient();


                setContentPane(a);

                pack();

                setVisible(true);

                Main.client.addActionListener(new ActionAdapter() {
                    @Override
                    public void actionPerformed(Action act) {
                        if(act instanceof ExamineItemAction) {
                            System.out.println(ItemTable.getItemAt(ItemTable.INVENTORY,((ExamineItemAction)act).getItemIndex()));
                        }

                    }
                });

            }
        });
    }

    void dump(RSWidget w) {
        if(w == null) return;
        if(w.getChildren() != null) {
            for(RSWidget c : w.getChildren()) {
                dump(c);
            }
        }

        if(w.getSpriteIds() == null) return;

        for(int i = 0; i < w.getSpriteIds().length; i++) {
            RSImage img = w.getImage(i);
            if(img == null) return;
            RTImage img0 = RTImage.create(img,true);
            img0.setRaster(new int[img0.getWidth() * img0.getHeight()], img0.getWidth(), img0.getHeight());
            try {
                img0.save(new FileOutputStream("./images/" + w.getSpriteIds()[i] + ".png"));
                System.out.println("Save:" + w.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private static Applet bootModClient() {
        try {
            CNI cni = ClientNative.get();
            cni.start();
            RSClient c = cni.get();
            Main.client = c;
            return (Applet) c;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }



    private void build() {


    }

}



