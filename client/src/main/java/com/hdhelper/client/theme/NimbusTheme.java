package com.hdhelper.client.theme;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.Painter;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class NimbusTheme {
	
	public NimbusTheme() {
		setTheme();
	}
	
	public void setTheme() {
		
		            UIManager.put("control", Color.GRAY);
		            UIManager.put("TabbedPane:TabbedPaneTab[Focused+Selected].backgroundPainter", new Painter() {
	                    public void paint(Graphics2D g, Object c, int w, int h) {
	                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	                        g.setColor(Color.WHITE);
	                        g.fillRoundRect(0, 6, w -1, 35, 1, 1);
	                    }
	                });
		            UIManager.put("TabbedPane:TabbedPaneTabArea[Enabled].backgroundPainter", new Painter() {
	                    public void paint(Graphics2D g, Object c, int w, int h) {
	                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	                        g.setColor(Color.GRAY);
	                        g.fill(new Rectangle(0, 0, w, h));
	                    }
	                });	
	
	}

}
