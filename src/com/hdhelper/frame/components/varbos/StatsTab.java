package com.hdhelper.frame.components.varbos;

import javax.swing.*;

import com.hdhelper.frame.components.PluginExplorer;

public class StatsTab extends JPanel {

	private JLabel run_time = new JLabel("Runtime: ");
	
	private JLabel threads = new JLabel("# Threads: ");
	
	private final JButton plugin_explorer = new JButton("Plugin Explorer");
	
	public StatsTab() {
		
		super();
		setLayout(null);
		JLabel lblToggleables = new JLabel("Stats:");
		lblToggleables.setBounds(6, 2, 107, 16);
		add(lblToggleables);
		
		run_time.setBounds(6, 20, 128, 23);
		add(run_time);
		
		threads.setBounds(6, 40, 128, 23);
		add(threads);
		
		
		
	}
	
	
}
