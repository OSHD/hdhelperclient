package com.hdhelper.client.api;

import com.hdhelper.agent.services.RSWidget;
import com.hdhelper.client.api.runeswing.Widget;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//http://runescape.wikia.com/wiki/Combat_styles
public class Combat {

    public static int getWeaponType() {
        return Config.get(843);
    }

    public static int getAttackStyle() {
        return Config.get(43);
    }

    private static RSWidget getSelectedStyleWidget() {
        int style = getAttackStyle();
        return Widget.get0(593, 3 + style * 4 );
    }

    private static Pattern XP_PATTERN = Pattern.compile("^\\(([A-Za-z]+) XP\\)$");

    //Gets the skills the current combat style trains by scanning the tooltip/popup runeScript
    public static String[] getStyleSkills() {

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

}
