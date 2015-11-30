package com.hdhelper.client.plugins.overlays;

import com.hdhelper.agent.services.RSClient;
import com.hdhelper.client.api.Skill;
import com.hdhelper.client.api.action.ActionTypes;
import com.hdhelper.client.api.action.tree.*;
import com.hdhelper.client.api.ge.RTFontImpl;
import com.hdhelper.client.api.ge.RTGlyphVector;
import com.hdhelper.client.api.ge.RTGraphics;
import com.hdhelper.client.api.plugin.Plugin;
import com.hdhelper.client.ui.HDCanvas;

import java.awt.*;
import java.util.*;

//AKA Mouseover Text + Tempoary Boosts
public class ActionDisplay extends Plugin {

    private RTFontImpl font;
    private Map<Integer, SkillBoost> profiles;
    private boolean displayPotionBoosts = true;

    @Override
    public void init() {
        font = new RTFontImpl(RTGlyphVector.getP12Full());
        initPotionProfiles();
    }

    private void initPotionProfiles() {
        profiles = new HashMap<Integer, SkillBoost>();
        for(SkillBoost profile : SkillBoost.values()) {
            for(int potionId : profile.potionIds) {
                profiles.put(potionId,profile);
            }
        }
    }

    private String getTopString() {

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

                String tryAct = acceptAction(option, actionStr, act); //See if this is something we like...

                if (tryAct != null) return tryAct; //We hit something interesting!

            }

        }


        return null;
    }

    private SkillBoost getProfile() {

        if(!displayPotionBoosts) return null;

        RSClient client = super.client;

        final int count = client.getMenuCount();

        if (count > 0) {

            for (int k = 0; k < count; k++) {

                int opcode = client.getMenuOpcodes()[k];

                if (TableItemAction.isInstance(opcode)) {

                    int arg0 = client.getMenuArg0s()[k];
                    int arg1 = client.getMenuArg1s()[k];
                    int arg2 = client.getMenuArg2s()[k];

                    Action act = Action.valueOf(opcode, arg0, arg1, arg2);

                    TableItemAction tia = (TableItemAction) act;

                    int item_id = tia.getItemID();

                    SkillBoost profile = profiles.get(item_id);

                    if(profile != null) return profile;

                    //Pretty sure their can only be one TableItemAction, if so, we should return now

                }


            }

        }


        return null;
    }


    //http://2007.runescape.wikia.com/wiki/Temporary_skill_boost
    private enum SkillBoost { //TODO

        AGILITY_POTION(new int[] {-1,-1,-1,-1},
            SkillDelta.levelChange(Skill.AGILITY,3)
        ),

        SUMMER_PIE(new int[] {-1,-1 }, // 2 bites
                SkillDelta.levelChange(Skill.AGILITY,5)
        ),

        ATTACK_POTION(new int[] {-1,-1,-1,-1},
                SkillDelta.percentGain(Skill.ATTACK, 10, 3)
        ),

        SUPER_ATTACK(new int[] {-1,-1,-1,-1},
                SkillDelta.percentGain(Skill.ATTACK, 15, 5)
        ),

        COMBAT_POTION(new int[] { 9745, 9743, 9741, 9739 }, // 1,2,3,4 Dose
                SkillDelta.percentGain(Skill.ATTACK, 10, +3),
                SkillDelta.percentGain(Skill.STRENGTH, 10, +3)
        ),

        SARADOMIN_BREW(new int[] { 6691, 6689, 6687, 6685 },
                SkillDelta.percentGain(Skill.DEFENCE,20,+2),
                SkillDelta.percentGain(Skill.HITPOINTS,15,+2),
                SkillDelta.percentGain(Skill.ATTACK,-10,0),
                SkillDelta.percentGain(Skill.STRENGTH,-10,0),
                SkillDelta.percentGain(Skill.MAGIC,-10,0),
                SkillDelta.percentGain(Skill.RANGED,-10,0)
        );

        final int[] potionIds;
        final SkillDelta[] deltas;

        SkillBoost(int[] potionIds, SkillDelta... deltas) {
            this.potionIds = potionIds;
            this.deltas   = deltas;
        }
    }




    private final static class SkillDelta {

        final Skill skill;
        int change;
        int type;
        int min;
        int minGain, maxGain;

        public static final int TYPE_LEVEL   = 1;
        public static final int TYPE_PERCENT = 2;
        public static final int TYPE_RANDOM  = 3;

        SkillDelta(Skill skill) {
            this.skill = skill;
        }

        static SkillDelta levelChange(Skill skill, int change) {
            SkillDelta delta = new SkillDelta(skill);
            delta.change = change;
            delta.type = TYPE_LEVEL;
            return delta;
        }

        static SkillDelta percentGain(Skill skill, int percentage, int minChange) {
            SkillDelta delta = new SkillDelta(skill);
            delta.change = percentage;
            delta.min = minChange;
            delta.type = TYPE_PERCENT;
            return delta;
        }

        static SkillDelta randomGain(Skill skill, int min, int max) {
            SkillDelta delta = new SkillDelta(skill);
            delta.minGain = min;
            delta.maxGain = max;
            delta.type = TYPE_RANDOM;
            return delta;
        }



    }


    private String acceptAction(String option, String action, Action act) {

        if (act.isCancel()) return null;
        if (act.isWalkHere()) return null;
        if (act.isUseItem()) return null;

        //Quick opcode checks
        switch (act.getOpcode()) {
            case ActionTypes.EXAMINE_ITEM:
            case ActionTypes.EXAMINE_GROUND_ITEM:
                return null;
        }

        //Complex checks...

        if (act instanceof PlayerAction) {

            return option;

		/*	PlayerAction pact = (PlayerAction) act;
			
			RSPlayer p = pact.getPlayer0();
			
			return "<col=ee04f0>" + p.getName() + "</col>";*/

        }
		
		/*if(act instanceof ItemAction) {
			
			
			
		}*/

        if (action.equalsIgnoreCase("Drop")) return null;


        if (act instanceof ObjectAction) {
            if (action.equalsIgnoreCase("Bank")) return action;
            return "<col=FFFFFF>" + action + "</col> " + option;
        }


        if (act instanceof ItemOnItemAction) { //TODO

            ItemOnItemAction item = (ItemOnItemAction) act;

            return null;

        }

        if (action.equalsIgnoreCase("Remove")) return null;

        if (act instanceof ExamineEntityAction) return null;

        return action + " " + option;
    }


    @Override
    public void render(RTGraphics g) {

        RSClient engine = super.client;

        if (engine.isMenuOpen()) return;

        int x = HDCanvas.mouseX + 15;
        int y = HDCanvas.mouseY;

        String acceptableTopMostAction = getTopString();

        SkillBoost profile = getProfile();

        if (acceptableTopMostAction == null && profile == null) return; //Nothing interesting to show...

        int row_height = font.getHeight() + 2 + 2;

        int width  = font.getStringWidth(acceptableTopMostAction) + 2 + 2;
        int height = row_height;

        if(profile != null) {
            height += (profile.deltas.length * row_height);
        }

        font.setGraphics(g);

        int cursor_width  = 18;
        int cursor_height = 0;

        if(x + width >= HDCanvas.width) {
            x -= (width+cursor_width);
        }

        if(y + height >= HDCanvas.height) {
            y -= (height+cursor_height);
        }


        g.fillRectangle(x , y, width, height, Color.BLACK.getRGB(), 156);

        font.drawString(
                acceptableTopMostAction,
                x + 2,
                y + font.getMaxAscent() + (row_height - font.getHeight()) / 2, Color.CYAN.getRGB());

        y += font.getHeight();

        if(profile != null) {
            renderPotionDeltas(profile, x+2,y+2,width,row_height, g);
        }

    }

    private void renderPotionDeltas(SkillBoost profile, int x, int y, int width, int row_height, RTGraphics g) {
        RSClient client = super.client;

        int max_final_level_txt_width = 0;
        boolean stared = false;

        class ChangeEntry {
            Skill skill;
            String change;
            String final_level_txt;
            boolean stared;
        }

       java.util.List<ChangeEntry> entries = new ArrayList<ChangeEntry>(profile.deltas.length);

        for(SkillDelta delta : profile.deltas) {

            Skill skill = delta.skill;

            final int cur_level  = client.getCurrentLevels()[skill.getId()];
            final int real_level = client.getLevels()[skill.getId()];
            final int cur_change = cur_level - real_level;

            int any_change = 0; // Any sort of change

            int change=0; // Also used as the min_change
            int final_level=0;
            int max_final_level = 0;
            int max_change=0;
            boolean waist=false;

            switch (delta.type) {

                case SkillDelta.TYPE_LEVEL:  {
                    if(delta.change < 0) {
                        final_level = cur_level - delta.change;
                        change = delta.change;
                        any_change = change;
                        waist = false;
                    } else if(delta.change > 0) {
                        final_level = cur_level + Math.max(cur_change,delta.change);
                        change = Math.max(cur_change,delta.change) - cur_change;
                        any_change = change;
                        waist = cur_change >= delta.change;
                    }
                    break;
                }

                case SkillDelta.TYPE_PERCENT: {

                    final double lvlDelta = (delta.change / 100.0D) * real_level;

                    int level_change;
                    if(delta.change >= 0) {
                        level_change = delta.min + (int) Math.floor(lvlDelta);
                    } else {
                        level_change = delta.min + (int) Math.ceil(lvlDelta);
                        if(level_change == 0) level_change = -1;
                    }

                    /**
                     * Gaining levels takes priority of what boost is better
                     * The floor for level reduction is 0
                     */

                    if(delta.change > 0) { // We're going to gain levels
                        assert delta.min >= 0; // We lost %level, I assume the min change is also negative

                        if(cur_change > 0) change = Math.max(level_change-cur_change,0);
                        else               change = level_change;

                        final_level = cur_level + change;
                        any_change = change;
                        waist = cur_change >= change;

                    } else { // We're going to lose levels
                        assert delta.min <= 0; // We lost %level, I assume the min change is also negative

                        final_level = Math.max(cur_level+level_change,0); //Can't take more levels then whats available (final_level floors at 0)
                        change = final_level - cur_level;
                        any_change = change;
                        waist = -level_change > cur_level; //It's a waist in the context that it does not resuse as much as it could
                    }

                    break;
                }

                case SkillDelta.TYPE_RANDOM: {
                    break;
                }

                default: {
                    return;
                }

            }


            String textColor;
            String lvlColor = "FF00FF";
            if(any_change == 0)     textColor = "FFFFFF"; // change == 0 : We gain nothing
            else if(any_change > 0) textColor = "00FF00"; // change > 0  : We gain levels
            else                    textColor = "FF0000"; // change < 0  : We lose levels


            String change_txt;
            String level_txt;
            if(delta.type == SkillDelta.TYPE_RANDOM) {
                change_txt = "<col=" + textColor + ">" + (change>0?"+":"") + "[" + change + "," + max_change + "]" + "</col>";
                level_txt = "[<u=" + lvlColor + ">" + final_level + "</u>,<u=" + lvlColor + ">" + max_final_level + "</u>]"  + (waist ? "*":"");
            } else {
                change_txt = "<col=" + textColor + ">" + (change>0?"+":"") + change + "</col>";
                level_txt = "<u=" + lvlColor  + ">" + final_level + "</u>"  + (waist ? "*":"");
            }

            if(waist) stared = true;

            String final_txt = change_txt + " " + level_txt;

            ChangeEntry entry = new ChangeEntry();
            entry.skill = skill;
            entry.change = change_txt;
            entry.final_level_txt = level_txt;
            entry.stared = waist;

            int lvl_txt_width = font.getStringWidth(level_txt);
            if(lvl_txt_width > max_final_level_txt_width) {
                max_final_level_txt_width = lvl_txt_width;
            }

            entries.add(entry);

        }


        int star_width = font.charWidth('*');

        for(ChangeEntry e : entries) {

            final int y0 = y + font.getMaxAscent() + ( row_height - font.getHeight() ) / 2;

            font.drawString(e.skill.getName(), x, y0, Color.GREEN.getRGB());
            font.drawLeftString(e.change, x + width - 4 - 8 - max_final_level_txt_width, y0, Color.YELLOW.getRGB());
            font.drawLeftString(e.final_level_txt, x + width - 4 - (stared&&!e.stared?star_width:0) , y0, Color.YELLOW.getRGB());

            y += row_height;

        }



    }


}
