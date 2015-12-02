package com.hdhelper.client.frame.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.hdhelper.client.frame.KitFrame;

public class SwiftMenuBar extends JMenuBar implements ActionListener {

	/**
	 * The serial UID.
	 */
	private static final long serialVersionUID = 7730869831372484781L;

	/**
	 * Constructs a new {@Code SwiftMenuBar} {@Code Object}
	 */
	public SwiftMenuBar(Dimension size, List<JMenu> menus) {
		super();
		setSize(size);
		for (JMenu menu : menus) {
			super.add(menu);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	/**
	 * Creates a menu item.
	 * @param imagePath the path.
	 * @return the item.
	 */
	protected static JMenu createMenu(String imagePath) {
		JMenu item = new JMenu();
		//Image image = KitFrame.getInstance().getToolkit().getImage(KitConstants.getImageUrl(imagePath));
		//item.setIcon(new ImageIcon(image));
		return item;
	}
	
}
