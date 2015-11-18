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




       ;

        //Create the menu bar.


        //Build the first menu.
        JMenu menu = new JMenu("Stuff");

        final JCheckBoxMenuItem render_ls = new JCheckBoxMenuItem("Render landscape");
        render_ls.setSelected(true);
        render_ls.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
          if (render_ls.isSelected()) Environment.RENDER_LANDSCAPE = true;
          else Environment.RENDER_LANDSCAPE = false;
         }
        });
        menu.add(render_ls);

        final JCheckBoxMenuItem draw_npcs = new JCheckBoxMenuItem("Debug Npcs");
        draw_npcs.setSelected(false);
        draw_npcs.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
          Environment.RENDER_NPC_DEBUG = draw_npcs.isSelected();
         }
        });
        menu.add(draw_npcs);

        final JCheckBoxMenuItem draw_players = new JCheckBoxMenuItem("Debug Players");
        draw_players.setSelected(false);
        draw_players.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
          Environment.RENDER_PLAYER_DEBUG = draw_players.isSelected();
         }
        });
        menu.add(draw_players);

        final JCheckBoxMenuItem draw_ground_items = new JCheckBoxMenuItem("Debug Ground Items");
        draw_ground_items.setSelected(false);
        draw_ground_items.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
          Environment.RENDER_GROUND_ITEM_DEBUG = draw_ground_items.isSelected();
         }
        });
        menu.add(draw_ground_items);

        final JCheckBoxMenuItem draw_boundary_items = new JCheckBoxMenuItem("Debug Boundaries");
        draw_boundary_items.setSelected(false);
        draw_boundary_items.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
          Environment.RENDER_BOUNDARY_DEBUG = draw_boundary_items.isSelected();
         }
        });
        menu.add(draw_boundary_items);


        final JCheckBoxMenuItem draw_tile_deco_items = new JCheckBoxMenuItem("Debug Tile Decorations");
        draw_tile_deco_items.setSelected(false);
        draw_tile_deco_items.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
          Environment.RENDER_TILE_DECO_DEBUG = draw_tile_deco_items.isSelected();
         }
        });
        menu.add(draw_tile_deco_items);


        final JCheckBoxMenuItem draw_bound_deco_items = new JCheckBoxMenuItem("Debug Boundary Decorations");
        draw_bound_deco_items.setSelected(false);
        draw_bound_deco_items.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
          Environment.RENDER_BOUNDARY_DECO_DEBUG = draw_bound_deco_items.isSelected();
         }
        });
        menu.add(draw_bound_deco_items);

        final JCheckBoxMenuItem draw_objects_items = new JCheckBoxMenuItem("Debug Objects");
        draw_objects_items.setSelected(false);
        draw_objects_items.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
          Environment.RENDER_OBJECT_DEBUG = draw_objects_items.isSelected();
         }
        });
        menu.add(draw_objects_items);

        final JCheckBoxMenuItem draw_misc_items = new JCheckBoxMenuItem("Debug Misc");
        draw_misc_items.setSelected(false);
        draw_misc_items.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
          Environment.RENDER_MISC_DEBUG = draw_misc_items.isSelected();
         }
        });
        menu.add(draw_misc_items);

        this.add(menu);

    }

}
