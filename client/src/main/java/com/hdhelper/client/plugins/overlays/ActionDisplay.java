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
import java.util.HashMap;
import java.util.Map;

//AKA Mouseover Text
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
    private enum SkillBoost {

        COMBAT_POTION(new int[] { 9745, 9743, 9741, 9739 }, // 1,2,3,4 Dose
                SkillDelta.levelChange(Skill.ATTACK, +6),
                SkillDelta.levelChange(Skill.STRENGTH, +6)
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

        if ((x + width) >= HDCanvas.width) {
            int cursor_width = 18;


            g.fillRectangle(x - width - cursor_width, y, width, height, Color.BLACK.getRGB(), 128);
            font.drawString(
                    acceptableTopMostAction,
                    x - width - cursor_width + 2,
                    y +  font.getMaxAscent() + ( row_height - font.getHeight() ) / 2, Color.CYAN.getRGB());

            if(profile != null) {
                int x0 = x - width - cursor_width + 2;
                int y0 = y + row_height;
                renderPotionDeltas(profile,x0,y0,width,row_height, g);
            }

        } else {

            g.fillRectangle(x, y, width, height, Color.BLACK.getRGB(), 128);

            font.drawString(acceptableTopMostAction,
                    x + 2,
                    y + font.getMaxAscent() + ( row_height - font.getHeight() ) / 2,
                    Color.CYAN.getRGB()
            );

            if(profile != null) {
                renderPotionDeltas(profile,x,y+row_height,width, row_height, g);
            }
        }

    }

    private void renderPotionDeltas(SkillBoost profile, int x, int y, int width, int row_height, RTGraphics g) {
        RSClient client = super.client;

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

                    double lvlDelta = Math.abs( (delta.change / 100D) * real_level );
                    int ceil  = (int) Math.ceil(lvlDelta);
                    int floor = (int) Math.floor(lvlDelta);

                    int level_change = delta.min;

                    if(delta.change < 0) {
                        level_change -= ceil;
                    } else {
                        level_change += floor;
                    }

                    if(delta.change > 0) { // We're going to gain levels
                        assert delta.min >= 0; // We lost %level, I assume the min change is also negative
                        final_level = real_level + Math.max(cur_change,level_change);
                        change = Math.max(cur_change,level_change) - cur_change;
                        any_change = change;
                        waist = cur_change >= change;
                    } else { // We're going to lose levels
                        assert delta.min <= 0; // We lost %level, I assume the min change is also negative
                        final_level = cur_level + level_change;
                        change = level_change; // The amount of levels that can decrease has no floor
                        any_change = level_change;
                        waist = false;
                    }

                    break;
                }

                case SkillDelta.TYPE_RANDOM: {
                    break;
                }
                default: return;
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


            String final_txt = change_txt + " " + level_txt;

            final int y0 = y + font.getMaxAscent() + ( row_height - font.getHeight() ) / 2;

            font.drawString(skill.getName(), x, y0, Color.GREEN.getRGB());

            font.drawLeftString(final_txt, x + width - 2, y0, Color.YELLOW.getRGB());

            y += row_height;

        }
    }


}
