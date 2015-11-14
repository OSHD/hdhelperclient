package com.hdhelper;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.hdhelper.frame.KitFrame;

/**
 * Manages the users swift client.
 * @author Vexia
 *
 */
public class SwiftManager {

	/**
	 * The singleton instance of the manager.
	 */
	private static final SwiftManager MANAGER = new SwiftManager();


	/**
	 * Constructs a new {@Code SwiftManager} {@Code Object}
	 */
	public SwiftManager() {
		/**
		 * empty.
		 */
	}

	/**
	 * Starts the swift manager.
	 */
	public void start() {
		//setLookAndFeel();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				KitFrame.getInstance().init();
			}
		});
	}

	/**
	 * Saves the manager.
	 */
	public void save() {
	}

	/** 
	 * Sets the look and feel.
	 */
	private final void setLookAndFeel() {
		
	}
	/**
	 * Gets the manager.
	 * @return the manager.
	 */
	public static SwiftManager getManager() {
		return MANAGER;
	}

}
