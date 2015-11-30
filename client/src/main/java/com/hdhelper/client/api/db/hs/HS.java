package com.hdhelper.client.api.db.hs;

import com.hdhelper.client.api.Skill;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

//http://services.runescape.com/m=rswiki/en/Hiscores_APIs
public class HS {

    private static final String BASE_URL = "http://services.runescape.com/m=";
    private static final String MODE_NORMAL_HS = BASE_URL + "hiscore_oldschool/index_lite.ws?player=";
    private static final String MODE_IRONMAN_HS = BASE_URL + "hiscore_oldschool_ironman/index_lite.ws?player=";
    private static final String MODE_ULTIMATE_HS = BASE_URL + "hiscore_oldschool_ultimate/index_lite.ws?player=";
    private static final String MODE_DEADMAN_HS = BASE_URL + "hiscore_oldschool_deadman/index_lite.ws?player=";



    public static Hiscore getHighscore(String player, String data) {
        return parse(player, Hiscore.Mode.NORMAL, data);
    }

    public static Hiscore getIronmanHighscore(String player, String data) {
        return parse(player, Hiscore.Mode.IRONMAN, data);
    }

    public static Hiscore getUltimateIronmanHighscore(String player, String data) {
        return parse(player, Hiscore.Mode.ULTIMATE_IRONMAN, data);
    }

    public static Hiscore getDeadmanHighscore(String player, String data) {
        return parse(player, Hiscore.Mode.DEADMAN, data);
    }



    private static Hiscore parse(String player, Hiscore.Mode mode, String data) {
        try {
            return parse(new BufferedReader(new StringReader(data)), mode, player);
        } catch (IOException e) {
            return null;
        }
    }



    public static Hiscore parse(BufferedReader reader, Hiscore.Mode mode, String player) throws IOException {

        String line;

        Skill[] skills = Skill.values();
        Activity[] activities = Activity.values();

        int skill_index = 0;
        int activity_index = 0;

        boolean parsed_overall = false;

        Hiscore hs = new Hiscore(player,mode);

        while ((line = reader.readLine()) != null) {

            String[] split = line.split(",");

            if(split.length == 3) { //Skill entry

                if(skill_index == 0 && !parsed_overall) { //The first skill-entry-type contains the Overall data

                    int overallRank  = Integer.parseInt(split[0]);
                    int overallLevel = Integer.parseInt(split[1]);
                    int overallXp    = Integer.parseInt(split[2]);

                    hs.updateOverall(overallRank,overallLevel,overallXp);

                    parsed_overall = true;

                } else  {

                    Skill skill = skills[skill_index];
                    int rank    = Integer.parseInt(split[0]);
                    int level   = Integer.parseInt(split[1]);
                    int xp      = Integer.parseInt(split[2]);

                    SkillEntry entry = hs.getSkillEntry(skill);
                    entry.update(rank,level,xp);

                    skill_index++;

                }

            } else if(split.length == 2) { //Activity entry

                Activity activity = activities[activity_index];
                assert activity.getId() == activity_index;
                int rank  = Integer.parseInt(split[0]);
                int score = Integer.parseInt(split[1]);

                ActivityEntry entry = hs.getActivityEntry(activity);
                entry.update(rank,score);

                activity_index++;

            } else {

                throw new IOException("Bad entry: " + line);

            }
        }

        return hs;

    }








    public static void main(String[] args) {
        String spec = MODE_NORMAL_HS + "Brainfree";
        String data = read(spec);
        Hiscore hs = getHighscore("Brainfree",data);
        System.out.println(hs.getOverallRank());
    }

    private static String read(String db) {
        try {
            URL url = new URL(db);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if(con.getResponseCode() != 200) return null;
            InputStream in = url.openStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read;
            while ((read = in.read(buffer)) > 0) {
                baos.write(buffer, 0, read);
            }
            byte[] data = baos.toByteArray();
            return new String(data);
        } catch (Throwable ignored) {
        }
        return null;
    }

}
