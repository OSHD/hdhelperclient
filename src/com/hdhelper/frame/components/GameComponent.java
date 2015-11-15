package com.hdhelper.frame.components;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JPanel;

import com.hdhelper.Context;
import com.hdhelper.Main;
import com.hdhelper.agent.peer.RSClient;

public class GameComponent extends JPanel {

	public GameComponent() {
		super();
		setBackground(Color.black);
		setOpaque(false);
		setLocation(0, 20);
		setSize(new Dimension(765, 528));
		Applet a = bootModClient();

		
        add(a);

        revalidate();
        setVisible(true);
	}
	
	private static Applet bootModClient() {
        try {
            Context c = Context.create();
            Main.client = (RSClient) c.applet;
            return c.applet;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
	
	/**
	 * The canvas to display.
	 */
	private final Canvas canvas = null;
	
	/**
	 * Gets the canvas.
	 * @return the canvas.
	 */
	public Canvas getCanvas() {
		return canvas;
	}

	
}
