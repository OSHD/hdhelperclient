package com.hdhelper.frame.components.feat;

import com.hdhelper.Environment;
import com.hdhelper.frame.components.FeatureTab;
import com.hdhelper.frame.components.PluginExplorer;
import com.hdhelper.frame.components.varbos.VarbosFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsTab extends FeatureTab {

	/**
	 * The serial UID.
	 */
	private static final long serialVersionUID = 1909559638852132377L;
	
	/**
	 * The modern fkeys check box.
	 */
	private final JCheckBox draw_npcs = new JCheckBox("Debug NPCs");
	
	private final JCheckBox draw_players = new JCheckBox("Debug Players");
	
	private final JCheckBox draw_ground_items = new JCheckBox("Debug Ground Items");
	
	private final JCheckBox draw_boundary_items = new JCheckBox("Debug Boundaries");
	
	private final JCheckBox draw_tile_decorations = new JCheckBox("Debug Tile Decorations");
	
	private final JCheckBox draw_boundary_decorations = new JCheckBox("Debug Boundary Decorations");
	
	private final JCheckBox draw_objects = new JCheckBox("Debug Objects");
	
	private final JCheckBox draw_misc = new JCheckBox("Debug Misc");
	
	private final JButton plugin_explorer = new JButton("Plugin Explorer");
	
	private final JButton stats_tab = new JButton("Statistics");
	
	/**
	 * Constructs a new {@Code SettingsTab} {@Code Object}
	 */
	public SettingsTab() {
		super();
		
		JLabel lblToggleables = new JLabel("Settings:");
		lblToggleables.setBounds(6, 6, 107, 16);
		add(lblToggleables);
		
		draw_npcs.setSelected(false);
        draw_npcs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Environment.RENDER_NPC_DEBUG = draw_npcs.isSelected();
			}
		});
        draw_npcs.setBounds(6, 30, 128, 23);
		add(draw_npcs);
		
		draw_players.setSelected(false);
		draw_players.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Environment.RENDER_PLAYER_DEBUG = draw_players.isSelected();
			}
		});
		draw_players.setBounds(6, 53, 128, 23);
		add(draw_players);
		
		draw_ground_items.setSelected(false);
	    draw_ground_items.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Environment.RENDER_GROUND_ITEM_DEBUG = draw_ground_items.isSelected();
			}
		});
	    draw_ground_items.setBounds(6, 76, 128, 23);
	    add(draw_ground_items);
		
        draw_boundary_items.setSelected(false);
        draw_boundary_items.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Environment.RENDER_BOUNDARY_DEBUG = draw_boundary_items.isSelected();
			}
		});
        draw_boundary_items.setBounds(6, 99, 128, 23);
        add(draw_boundary_items);
        
        draw_tile_decorations.setSelected(false);
        draw_tile_decorations.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Environment.RENDER_TILE_DECO_DEBUG = draw_tile_decorations.isSelected();
			}
		});
        draw_tile_decorations.setBounds(6, 118, 150, 23);
        add(draw_tile_decorations);
        
        draw_boundary_decorations.setSelected(false);
        draw_boundary_decorations.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Environment.RENDER_BOUNDARY_DECO_DEBUG = draw_boundary_decorations.isSelected();
			}
		});
        draw_boundary_decorations.setBounds(6, 141, 170, 23);
        add(draw_boundary_decorations);
        
        draw_objects.setSelected(false);
        draw_objects.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Environment.RENDER_OBJECT_DEBUG = draw_objects.isSelected();
			}
		});
        draw_objects.setBounds(6, 164, 128, 23);
        add(draw_objects);
        
        draw_misc.setSelected(false);
        draw_misc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Environment.RENDER_MISC_DEBUG = draw_misc.isSelected();
			}
		});
        draw_misc.setBounds(6, 187, 128, 23);
        add(draw_misc);
        
        plugin_explorer.setSelected(false);
        plugin_explorer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PluginExplorer();
			}
		});
        plugin_explorer.setBounds(6, 220, 128, 30);
        add(plugin_explorer);
        
        stats_tab.setSelected(false);
        stats_tab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VarbosFrame();
			}
		});
        stats_tab.setBounds(6, 250, 128, 30);
        add(stats_tab);
        
	}

	/**
	 * Gets the chckbxModernFkeys.
	 * @return the chckbxModernFkeys.
	 */
	public JCheckBox getChckbxModernFkeys() {
		return draw_npcs;
	}
}
