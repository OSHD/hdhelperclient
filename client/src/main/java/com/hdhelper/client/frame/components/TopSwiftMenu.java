package com.hdhelper.client.frame.components;

import com.hdhelper.client.frame.KitFrame;
import com.hdhelper.client.frame.util.KitConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TopSwiftMenu extends SwiftMenuBar {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -6144789205519173892L;

	/**
	 * The link menu.
	 */
	private static final JMenu link = createMenu("nub");

	/**
	 * The scren.
	 */
	private static final JMenu screen = createMenu("nub");

	/**
	 * The hide button.
	 */
	private static final JMenu hide = createMenu("nub");

	private static final JMenu settings = new JMenu("Settings");
	
	/**
	 * The homepage.
	 */
	private static final JCheckBoxMenuItem plugins = new JCheckBoxMenuItem("Plugins");


	/**
	 * The forum page.
	 */
	private static final JMenu performance = createMenu("Performance");

	/**
	 * The account page.
	 */
	private static final JMenu varbos = createMenu("Varbos");

	/**
	 * The world map.
	 */
	private static final JMenuItem worldMap = createMenu("World");

	/**
	 * If the pane is hidden.
	 */
	private static boolean hidden;

	/**
	 * Constructs a new {@Code TopSwiftMenu} {@Code Object}
	 * @param size the size.
	 * @param menus the menus.
	 */
	public TopSwiftMenu(Dimension size, List<JMenu> menus) {
		super(size, menus);
	}

	/**
	 * Toggles the menu.
	 */
	protected void toggle() {
		hidden = !hidden;
		if (hidden) {
			KitFrame.getInstance().setSize((int) KitConstants.SIZE.getWidth() - 288, (int) KitConstants.SIZE.getHeight());
		} else {
			KitFrame.getInstance().setSize(KitConstants.SIZE);
		}
	}


	/**
	 * Creates the top swidt menu.
	 * @return the menu.
	 */
	public static TopSwiftMenu create() {
		List<JMenu> items = new ArrayList<JMenu>();
		items.add(settings);
		settings.add(plugins);
		settings.add(performance);
		settings.add(varbos);
		return new TopSwiftMenu(new Dimension((int) KitConstants.SIZE.getWidth(), 20), items);
	}

	/**
	 * Creates a menu item.
	 * @param name the name.
	 * @return the item.
	 */
	private static JMenuItem createMenuItem(String name) {
		JMenuItem item = new JMenuItem(name);
		item.addActionListener(new MenuItemListener());
		return item;
	}

	public boolean isCollapsed() {
		return hidden;
	}

	/**
	 * The link listener.
	 * @author Vexia
	 *
	 */
	public static class MenuItemListener implements ActionListener {

		/**
		 * The robot.
		 */
		private static Robot robot;


		/**
		 * Constructs a new {@Code MenuItemListener} {@Code Object}
		 */
		public MenuItemListener() {
			try {
				robot = new Robot();
			} catch (AWTException e2) {
				e2.printStackTrace();
			}
		}
		
		/**
		 * The image frame.
		 */
		final JFrame frame = new JFrame();

		/**
		 * Views a world map.
		 */
		private void viewWorldMap() {
			if (frame.isVisible()) {
				frame.setFocusable(true);
				frame.setFocusableWindowState(true);
				return;
			}
			//Image image = KitConstants.getImage("worldmap.jpg");
			frame.setSize(new Dimension(1000, 700));
			JLabel label = new JLabel();
			//label.setIcon(new ImageIcon(image));
			label.setSize(frame.getSize());
			JScrollPane scroll = new JScrollPane(label);
			scroll.setSize(frame.getSize());
			scroll.getVerticalScrollBar().setValue(1400);
			scroll.getHorizontalScrollBar().setValue(1400);
			frame.add(scroll);
			frame.setResizable(false);
			frame.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem item = (JMenuItem) e.getSource();
			if (e.getActionCommand().equals("Collapse") || e.getActionCommand().equals("Show")) {
				TopSwiftMenu m = KitFrame.getInstance().getTopMenu();
				m.toggle();
				if (m.isCollapsed()) {
					item.setText("Show");
				} else {
					m.setToolTipText("Collapse");
				}
				return;
			} else if (e.getActionCommand().equals("Capture")) {
				Canvas c = KitFrame.getInstance().getPanel().getCanvas();
				Point p = KitFrame.getInstance().getPanel().getCanvas().getLocationOnScreen();
				int  x = (int) p.getX();
				int y = (int) p.getY();
				int x2 = c.getWidth();
				int y2 = c.getHeight() - 25;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String name = format.format(new Date());
				BufferedImage image = robot.createScreenCapture(new Rectangle(x, y, x2, y2));
				//SwiftManager.getManager().writeScreenShot(image, name);
				return;
			} else if (e.getActionCommand().equals("View")) {
				viewWorldMap();
				return;
			}
		}

	}
}
