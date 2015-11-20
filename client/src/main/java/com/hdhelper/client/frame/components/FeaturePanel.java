package com.hdhelper.client.frame.components;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.hdhelper.client.frame.components.feat.SettingsTab;

public class FeaturePanel extends JPanel {

	/**
	 * The jtabbed pane.
	 */
	private final JTabbedPane tabbed_pane = new JTabbedPane();

	/**
	 * The settings tab.
	 */
	private final SettingsTab settingsTab;
	
	//private final HighscoreTab highscoreTab;

	/**
	 * Constructs a new {@Code ContentPanel} {@Code Object}
	 */
	public FeaturePanel() {
		super();
		setBounds(765, 16, 290, 521);
		setLayout(null);
		tabbed_pane.setBounds(0, 6, 290, 509);
		tabbed_pane.setTabPlacement(JTabbedPane.RIGHT);		
		//addTab("social.png", (socialTab = new SocialTab()), "Social Media");
		//addTab("", (highscoreTab = new HighscoreTab()), "Highscores");
		//priceTab = new PriceGuideTab();
		//	addTab("cart.png", (priceTab = new PriceGuideTab()), "Price Guide");
		//addTab("paper.png", (notepadTab = new NotepadTab()), "Notepad");
		//addTab("wrench.png", (toolTab = new ToolTab()), "Tools");
		addTab("", (settingsTab = new SettingsTab()), "Settings");
		add(tabbed_pane);
		tabbed_pane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				FeatureTab tab = (FeatureTab) tabbed_pane.getComponentAt(tabbed_pane.getSelectedIndex());
				tab.open();
			}

		});
	}

	/**
	 * Adds a tab.
	 * @param image the image.
	 * @param panel the panel.
	 * @param tooltip the tooltip.
	 */
	private void addTab(String image, JPanel panel, String tooltip) {
		tabbed_pane.addTab("", null, panel, tooltip);
	}

	/**
	 * Gets the highscoreTab.
	 * @return the highscoreTab.
	 */
	//public HighscoreTab getHighscoreTab() {
	//	return highscoreTab;
	//}
	
	/**
	 * Gets the settingsTab.
	 * @return the settingsTab.
	 */
	public SettingsTab getSettingsTab() {
		return settingsTab;
	}
}
