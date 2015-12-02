package com.hdhelper.client.api;

import com.hdhelper.agent.services.RSWidget;
import com.hdhelper.client.api.runeswing.Widget;
import com.hdhelper.client.api.runeswing.runescript.RuneScript;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//http://runescape.wikia.com/wiki/Combat_styles
public class Combat {

    private static final Object[][][] styles;

    private static final Object SHARED = new Object();

    static {

        final int SLOT_0 = 0;
        final int SLOT_1 = 1;
        final int SLOT_2 = 2;
        final int SLOT_3 = 3;

        styles = new Object[25][4][];

        //http://pastebin.com/BHhG92kq
        //////////////////////////////////////
        init(styles,0,SLOT_0,Skill.ATTACK);
        init(styles,0,SLOT_1,Skill.STRENGTH);
        init(styles,0,SLOT_3,Skill.DEFENCE);
        ///////////////////////////////////////
        init(styles,1,SLOT_0,Skill.ATTACK);
        init(styles,1,SLOT_1,Skill.STRENGTH);
        init(styles,1,SLOT_2,Skill.STRENGTH);
        init(styles,1,SLOT_3,Skill.DEFENCE);
        ///////////////////////////////////////
        init(styles,2,SLOT_0,Skill.ATTACK);
        init(styles,2,SLOT_1,Skill.STRENGTH);
        init(styles,2,SLOT_3,Skill.DEFENCE);
        ///////////////////////////////////////
        init(styles,3,SLOT_0,Skill.RANGED);
        init(styles,3,SLOT_1,Skill.RANGED);
        init(styles,3,SLOT_3,Skill.RANGED,Skill.DEFENCE);
        ///////////////////////////////////////
        init(styles,4,SLOT_0,Skill.ATTACK);
        init(styles,4,SLOT_1,Skill.STRENGTH);
        init(styles,4,SLOT_2,SHARED);
        init(styles,4,SLOT_3,Skill.DEFENCE);
        ///////////////////////////////////////
        init(styles,5,SLOT_0,Skill.RANGED);
        init(styles,5,SLOT_1,Skill.RANGED);
        init(styles,5,SLOT_3,Skill.RANGED,Skill.DEFENCE);
        ///////////////////////////////////////

    }


    static void init(Object[][][] dest, int weaponType, int slot, Object... skills) {
        dest[weaponType][slot] = skills;
    }


    public static int getWeaponType() {
        return Config.get(843);
    }

    public static int getAttackStyle() {
        return Config.get(43);
    }

    private static RSWidget getSelectedStyleWidget() {
        int style = getAttackStyle();
        return Widget.get0(593, 3 + style * 4);
    }

    private static Pattern XP_PATTERN = Pattern.compile("^\\(([A-Za-z]+) XP\\)$");

    //Gets the skills the current combat style trains by scanning the tooltip/popup runeScript
    public static String[] getStyleSkills() {

        if(Game.isLoggedIn()) {
            update();
        } else {
            return null;
        }

        RSWidget widget = getSelectedStyleWidget();
        if(widget == null) return null;
        Object[] listener = widget.getMouseHoverListener();
        if(listener == null) return null;
        if(((Integer)listener[0]) != 38) return null; //id of the renderscript is expected to be 38...

        String popupText = (String) listener[3]; // 2nd augment of the script is the text to display...
        String[] rows = popupText.split("<br>");

        Set<String> skillSet = new HashSet<String>();
        for(String row : rows) {
            Matcher m = XP_PATTERN.matcher(row);
            if(m.matches()) {
                String skill_name = m.group(1);
                skillSet.add(skill_name);
            }
        }

        return skillSet.toArray(new String[skillSet.size()]);

    }

    private static int last_weapon_type = -1;
    private static void update() {
        int cur_weapon_type = getWeaponType();
        if(last_weapon_type != cur_weapon_type) {
            RuneScript.run(420); //Force the engine to update the combat styles so we can grab them
            last_weapon_type = cur_weapon_type;
        }
    }

}
