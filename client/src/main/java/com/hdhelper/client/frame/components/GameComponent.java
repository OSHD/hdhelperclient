package com.hdhelper.client.frame.components;

import com.hdhelper.client.cni.ClientNative;
import com.hdhelper.client.Main;
import com.hdhelper.agent.CNI;
import com.hdhelper.agent.services.RSClient;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;

public class GameComponent extends JPanel {

	public GameComponent() {
		super();
		setBackground(Color.black);
		setOpaque(false);
		setLocation(0, 20);
		setSize(new Dimension(765, 528));

		Applet a = bootModClient();

		//thought u set it to canvas here?, idk i was fucking with it, should have 
		// not pushed this
        add(a);

        
     //   revalidate();
        setVisible(true);
	}
	
	private static Applet bootModClient() {
        try {
			CNI cni = ClientNative.get();
			cni.start();
			RSClient c = cni.get();
			Main.client = c;
			return (Applet) c;
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
