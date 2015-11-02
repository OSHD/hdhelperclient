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
        JCheckBoxMenuItem render_ls;
        JCheckBoxMenuItem draw_debug;

        //Create the menu bar.


        //Build the first menu.
        menu = new JMenu("Stuff");

        render_ls = new JCheckBoxMenuItem("Render landscape");
        render_ls.setSelected(true);
        render_ls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Environment.RENDER_LANDSCAPE = render_ls.isSelected();
            }
        });

        draw_debug = new JCheckBoxMenuItem("Draw Debug");
        draw_debug.setSelected(false);
        draw_debug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Environment.RENDER_DEBUG = draw_debug.isSelected();
            }
        });

        menu.add(render_ls);
        menu.add(draw_debug);

        this.add(menu);

    }

}
