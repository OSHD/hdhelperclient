package com.hdhelper.client.frame.components;

import com.hdhelper.client.Client;
import com.hdhelper.client.ClientNative;
import com.hdhelper.client.Main;
import com.hdhelper.client.api.action.ActionAdapter;
import com.hdhelper.agent.CNI;
import com.hdhelper.agent.event.VariableEvent;
import com.hdhelper.agent.event.VariableListener;
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
		setSize(new Dimension(767, 549));

		Applet a = bootModClient();
		
		a.setSize(767, 549);

		//thought u set it to canvas here?, idk i was fucking with it, should have 
		// not pushed this
        add(a);
        
        addListeners();

        
     //   revalidate();
        setVisible(true);
	}
	
	public void addListeners() {
		
		Client.get().addVariableListener(new VariableListener() {

			@Override
			public void variableChanged(VariableEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e);
				
			}
			
		});
		
	}
	
	private static Applet bootModClient() {
        try {
			CNI cni = ClientNative.get();
			cni.initAndStartGame();
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
