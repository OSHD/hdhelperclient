package com.hdhelper.client.plugins.overlays;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hdhelper.agent.services.RSClient;
import com.hdhelper.agent.services.RSItemDefinition;
import com.hdhelper.agent.services.RSPlayer;
import com.hdhelper.client.api.action.ActionTypes;
import com.hdhelper.client.api.action.tree.Action;
import com.hdhelper.client.api.action.tree.ExamineEntityAction;
import com.hdhelper.client.api.action.tree.GroundItemAction;
import com.hdhelper.client.api.action.tree.ItemOnItemAction;
import com.hdhelper.client.api.action.tree.NpcAction;
import com.hdhelper.client.api.action.tree.ObjectAction;
import com.hdhelper.client.api.action.tree.PlayerAction;
import com.hdhelper.client.api.action.tree.TableItemAction;
import com.hdhelper.client.api.ge.GlyphFactory;
import com.hdhelper.client.api.ge.RTFont;
import com.hdhelper.client.api.ge.RTFontImpl;
import com.hdhelper.client.api.ge.RTGlyphVector;
import com.hdhelper.client.api.ge.RTGraphics;
import com.hdhelper.client.api.ge.RTImage;
import com.hdhelper.client.api.plugin.Plugin;
import com.hdhelper.client.ui.HDCanvas;

public class ActionDisplay extends Plugin {
	
	
	RTImage lol;
	
	@Override
	public void init() {
		
	  /*	Font java_font = new Font("Verdana", Font.BOLD, 12);
	  	RTGlyphVector rs_font_data = GlyphFactory.createVector(java_font);*/
	  	
		Image img;
		try {
			img = ImageIO.read(new File("C:/Users/Jacob/Desktop/apple.png"));
			lol = new RTImage(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 font = new RTFontImpl(RTGlyphVector.getP12Full());
	}
	
	
	String getTopString() {
		RSClient engine = super.client;

		if (engine.getMenuCount() > 0) {

			for (int k = 0; k < engine.getMenuCount(); k++) {
				
				int i = engine.getMenuCount() - k - 1; // Start from the top down...
				
				String option = engine.getMenuOptions()[i];
				String actionStr = engine.getMenuActions()[i];
				
				int opcode = engine.getMenuOpcodes()[i];
				int arg0 = engine.getMenuArg0s()[i];
				int arg1 = engine.getMenuArg1s()[i];
				int arg2 = engine.getMenuArg2s()[i];

				Action act = Action.valueOf(opcode, arg0, arg1, arg2);

				String tryAct = acceptAction(option,actionStr,act); //See if this is somthing we like...
				
				if(tryAct != null) return tryAct; //We hit somthing interesting!

			}

		}
		
		
		return null;
	}
	
	
	String acceptAction(String option, String action, Action act) {
		
		if(act.isCancel()) return null;
		if(act.isWalkHere()) return null;
		if(act.isUseItem()) return null;
		
		//Quick opcode checks
		switch(act.getOpcode()) {
			case ActionTypes.EXAMINE_ITEM:
			case ActionTypes.EXAMINE_GROUND_ITEM:
				return null;
		}
		
		//Complex checks...
		
		if(act instanceof PlayerAction) {
			
			PlayerAction pact = (PlayerAction) act;
			
			RSPlayer p = pact.getPlayer0();
			
			return "<col=ee04f0>" + p.getName() + "</col>";
			
		}
		
		/*if(act instanceof ItemAction) {
			
			
			
		}*/
		
		if(action.equalsIgnoreCase("Drop")) return null;
		
		if(act instanceof TableItemAction) {
			TableItemAction tia = (TableItemAction) act;
			int item_id = tia.getItemID();
			RSItemDefinition def = client.getItemDef(item_id);
			def.getName();
			
			return action + " <col=fd9900>" + def.getName() + "</col>";
			
		}
		
		if(action.equalsIgnoreCase("Add friend") || action.equalsIgnoreCase("Message")) {
			String player = option;
			
			return action + " " + player;
		}
		
		if(act instanceof NpcAction) {
				
			NpcAction npc_action = (NpcAction) act;
			
			return action + " " + option;
			
		}
		
		if(act instanceof GroundItemAction) {
			return action + " " + option;
		}
		
		
		if(act instanceof ObjectAction) {
			if(action.equalsIgnoreCase("Bank")) return action;
			return action + " <col=fd9900>" + option + "</col>";
		}
		
		if(action.equalsIgnoreCase("Cast")) {
			return action + " " + option;
		}
		
		if(action.equalsIgnoreCase("Value")) {
			return action + " " + option;
		}
		
		if(action.contains("Remove")) {
			return action + " " + option;
		}
		
		if(action.equalsIgnoreCase("Store")) {
			return action + " " + option;
		}
		
		if(action.contains("Withdraw")) {
			return action + " " + option;
		}
		
		if(action.contains("Deposit")) {
			return action + " " + option;
		}
		
		if(act instanceof ItemOnItemAction) {
			
			ItemOnItemAction item = (ItemOnItemAction) act;
			
			return null;
			
		}
		
		if(action.equalsIgnoreCase("Remove")) return null;
		
		if(act instanceof ExamineEntityAction) return null;
		
		return action;
	}
	
	
	@Override
	public void render(RTGraphics g) {
		
		RSClient engine = super.client;
		
		int x = HDCanvas.mouseX + 15;
		int y = HDCanvas.mouseY;
		
		String acceptableTopMostAction = getTopString();
		
		if(acceptableTopMostAction == null) return; //Nothing interesting to show...
		
		int width  = font.getStringWidth(acceptableTopMostAction) + 2 + 2;
		int height = font.getHeight() + 2 + 2;
		
		if(engine.isMenuOpen()) return;
		
		font.setGraphics(g);
		
		
		//System.out.println(String.valueOf(x + action_font_width + 4));
		
	
		if( (x + width) >= HDCanvas.width ) {
				
			g.fillRectangle(x - width - 18, y, width, height, Color.BLACK.getRGB(), 128);
			font.drawString(
					acceptableTopMostAction, 
					x - width - 18 + 2,
					y + 2 + (height - 7), Color.CYAN.getRGB());
		} else {
			g.fillRectangle(x, y, width, height, Color.BLACK.getRGB(), 128);
			font.drawString(acceptableTopMostAction, x + 2, y + 2 + (height - 7), Color.CYAN.getRGB());
		}
		
	
		
		
	}
	
	private RTFontImpl font;

}
