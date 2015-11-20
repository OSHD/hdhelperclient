package com.hdhelper.client.frame.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

@SuppressWarnings("serial")
public class DecorationComponent extends JPanel {

	public DecorationComponent() {
		
		//decoration_panel.add(this);
		setSize((int)title_width, 25);
		add(title_bar);
		title_bar.setSize(100, 20);
		
		add(close_button);
		close_button.setBackground(Color.orange);
		
		setVisible(true);
		
	}
	
	public void setComponentSize(int x, int y) {
		//setSize(x, y);
	}
	
	private JLabel title_bar		= new JLabel("HDHelper");
	
	private JButton close_button    = new JButton();
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private double title_width = screenSize.getWidth();
	
}
