package com.hdhelper.frame.components.varbos;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.hdhelper.frame.components.FeatureTab;

public class VarbosFrame extends JFrame {
	
	private JTabbedPane varbos_tabs = new JTabbedPane();
	
	private StatsTab statisticsTab = StatsTab.INSTANCE.init();
	
	public VarbosFrame() {
		super();
		setLayout(null);
		setSize(700, 600);
		varbos_tabs.setBounds(0, 6, 690, 590);
		varbos_tabs.setTabPlacement(JTabbedPane.TOP);		
		
		addTab(null, (statisticsTab = new StatsTab()), "Stats");
		//addTab(null, (stats_tab = new StatsTab()), "Debug");
		//addTab(null, (stats_tab = new StatsTab()), "Performance");
		
		add(varbos_tabs);
		
		varbos_tabs.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				FeatureTab tab = (FeatureTab) varbos_tabs.getComponentAt(varbos_tabs.getSelectedIndex());
				tab.open();
			}

		});
		
		setVisible(true);
		
	}
	
	/**
	 * Adds a tab.
	 * @param image the image.
	 * @param panel the panel.
	 * @param tooltip the tooltip.
	 */
	private void addTab(String image, JPanel panel, String tooltip) {
		varbos_tabs.addTab("", null, panel, tooltip);
	}

}
