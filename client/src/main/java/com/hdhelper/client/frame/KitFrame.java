package com.hdhelper.client.frame;

import java.awt.Canvas;

import javax.swing.JFrame;




import com.hdhelper.client.frame.components.FeaturePanel;
import com.hdhelper.client.frame.components.GameComponent;
import com.hdhelper.client.frame.components.TopSwiftMenu;
import com.hdhelper.client.frame.util.KitConstants;

public class KitFrame extends JFrame {

	private static final KitFrame instance = new KitFrame();
	
	private GameComponent game_component;
	
	/**
	 * The JMenu bar.
	 */
	private TopSwiftMenu topMenu;
	
	private FeaturePanel featurePanel;
	
	public void init() {
		
		setResizable(false);
		//setIconImage(getToolkit().getImage(KitConstants.getImageUrl("favicon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(KitConstants.SIZE);
		setLayout(null);
		game_component = new GameComponent(); 
		//addWindowListener(new SwiftFrameAdapter());
		getContentPane().setLayout(null);
		getContentPane().add(game_component);
		getContentPane().add((topMenu = TopSwiftMenu.create()));
		getContentPane().add((featurePanel = new FeaturePanel()));
		this.setLocation(KitConstants.getCenterPoint(null));
		setVisible(true);
		
	}
	
	/**
	 * Gets the game canvas.
	 * @return the canvas.
	 */
	public Canvas getGameCanvas() {
		return null;
	}
	
	
	public GameComponent getPanel() {
		return game_component;
	}
	
	/**
	 * Gets the instance.
	 * @return the instance.
	 */
	public static KitFrame getInstance() {
		return instance;
	}
	
	public TopSwiftMenu getTopMenu() {
		return topMenu;
	}
	
}
