package com.hdhelper.frame.util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;

public class KitConstants {

	/**
	 * The size of the frame.
	 */
	public static final Dimension SIZE = new Dimension(1060, 561);
	
	public static boolean SIDE_PANEL_ENABLED;
	
	public static final Font FONT = new Font("Lucida Grande", Font.PLAIN, 10);
	
	/**
	 * Gets the screen coordinates to place panels in the center
	 * @param {Dimension} the panel size, null to use default frame size
	 * @return {Point} the center dimension
	 */
	public static Point getCenterPoint(Dimension panelSize) {
		if (panelSize == null)
			panelSize = SIZE;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		return new Point((int) ((screen.getWidth() / 2) - (panelSize.getWidth() / 2)), 
				(int) ((screen.getHeight() / 2) - (panelSize.getHeight() / 2)));
	}
	
}
