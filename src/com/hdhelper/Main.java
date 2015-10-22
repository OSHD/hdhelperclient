package com.hdhelper;

import com.hdhelper.peer.RSClient;
import com.hdhelper.ui.MainFrame;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static RSClient client;

    public static void main(String... args) throws IOException, InterruptedException {

        System.out.println("Start");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("PopupMenu.consumeEventOnClose", Boolean.TRUE);
        } catch (final ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ignored) {
        }

        MainFrame frame = new MainFrame();
        frame.start();
        
    }

}
