package com.hdhelper.ui;

import com.hdhelper.Environment;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bar extends JMenuBar {

    public Bar() {
        build();
    }


    private void build() {

        JMenu menu;
        JCheckBoxMenuItem cbMenuItem;

        //Create the menu bar.


        //Build the first menu.
        menu = new JMenu("Stuff");

        cbMenuItem = new JCheckBoxMenuItem("Render landscape");
        cbMenuItem.setSelected(true);
        cbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Environment.RENDER_LANDSCAPE = cbMenuItem.isSelected();
            }
        });

        menu.add(cbMenuItem);

        this.add(menu);

    }

}
