package com.hdhelper.client.plugins.overlays;

import com.hdhelper.agent.event.MessageEvent;
import com.hdhelper.agent.event.MessageListener;
import com.hdhelper.agent.services.RSClient;
import com.hdhelper.agent.services.RSImage;
import com.hdhelper.agent.services.RSMessage;
import com.hdhelper.client.api.Messages;
import com.hdhelper.client.api.ge.*;
import com.hdhelper.client.api.plugin.Plugin;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cooking extends Plugin {


    private RTFont font;
    private Set<Profile> profiles;
    DecimalFormat myFormatter;

    @Override
    public void init() {
        Messages.addMessageListener(new Listener());
        font = new RTFontImpl(RTGlyphVector.getB12Full());
        profiles = new HashSet<Profile>();
        myFormatter = new DecimalFormat("###,##");
        profiles.add(new Profile("shrimps",317));
        profiles.add(new Profile("anchovies",321));
    }


    private final class Listener implements MessageListener {
        @Override
        public void messageReceived(MessageEvent e) {
            RSMessage message = e.getMessage();
            if(message.getType() == 105) {
                for(Profile profile : profiles) {
                    profile.onMessage(message);
                }
            }
        }
    }

    @Override
    public void render(RTGraphics g) {
        int x = 20;
        int y = 15;
        for(Profile profile : profiles) {
            if(!profile.isEmpty()) {
                profile.render(x,y,font,g);
                y += (profile.img != null ? profile.img.getHeight() : 0) + 6;
            }
        }
    }

    public static void main(String[] args) {

        String msg = "You accidentally burn the shrimps.";

        String foodName = "shrimps";
        Pattern pattern = Pattern.compile("^You (successfully cook some|accidentally burn the) " + foodName + "\\.$");;
        Matcher matcher = pattern.matcher(msg);
        if(!matcher.matches()) return;
        String result = matcher.group(1);

        System.out.println(result);

    }

    private class Profile {

        final String foodName;
        final Pattern pattern;
        final int foodId;

        int totalCooked = 30;
        int totalBurned = 7;
        RTImage img;

        public Profile(String foodName, int foodId) {
            this.foodId = foodId;
            pattern = Pattern.compile(
                    "^You (successfully cook some|accidentally burn the) " + foodName + "\\.$"
            );
            this.foodName = (Character.toUpperCase(foodName.charAt(0)) + foodName.substring(1)); //Cap the first char
        }

        public boolean isEmpty() {
            return getTotal() == 0;
        }

        public int getTotal() {
            return totalCooked + totalBurned;
        }

        public void reset() {
            totalCooked = 0;
            totalBurned = 0;
        }

        public void onMessage(RSMessage message) {
            String msg = message.getMessage();
            if(msg == null || msg.isEmpty()) return;
            //Find original name from the message:
            Matcher matcher = pattern.matcher(msg);
            if(!matcher.matches()) return;
            String result = matcher.group(1);
            if(result == null || result.isEmpty()) return; //eek

            if(result.equals("successfully cook some")) {
                // We successfully cooked it!
                totalCooked++;

                System.out.println("We cooked a " + foodName);
            } else {
                assert result.equals("accidentally burn the");
                // We burned it!
                totalBurned++;

                System.out.println("We burned a " + foodName);
            }
        }

        public void render(int x, int y, RTFont font, RTGraphics g) {
            RTImage image = getImage();
            if(image == null) return;
            image.setGraphics(g);

            // Add commas for big numbers
            String totalStr       = String.format(Locale.US, "%,d", (totalCooked + totalBurned));
            String totalCookedStr = String.format(Locale.US, "%,d", totalCooked);
            String totalBurnedStr = String.format(Locale.US, "%,d", totalBurned);

            double rate = ((totalCooked / (double) (totalCooked+totalBurned))) * 100;
            String rateStr = myFormatter.format(rate);

            String txt = "Total:<col=0000FF>"   + totalStr       + "</col>" +
                         " Cooked:<col=00FF00>" + totalCookedStr + "</col>" +
                         " Burned:<col=FF0000>" + totalBurnedStr + "</col>" +
                         " Rate:<col=" + getRatioHex(rate) + "> " + rateStr + "% </col>";

            int width = font.getStringWidth(txt) + 9;

            g.fillRectangle(x, y, image.getWidth() + width + 9, image.getHeight(), Color.BLACK.getRGB(), 128);
            g.drawRectangle(x, y, image.getWidth() + width + 9, image.getHeight(), Color.YELLOW.getRGB(),128);

            image.f(x+3, y);
            int sx = x + image.getWidth() + 3;

            g.drawVerticalLine(sx,y,image.getHeight(),Color.YELLOW.getRGB(),128);

            sx += 3;
            font.setGraphics(g);
            font.drawWordWrap(foodName, sx, y, width, image.getHeight(), Color.YELLOW.getRGB(), -1, RTFont.ROW_LAYOUT_CENTER, RTFont.TEXT_LAYOUT_BASELINE, 0);

            font.drawWordWrap(txt, sx, y, width, image.getHeight(), Color.YELLOW.getRGB(), -1, RTFont.ROW_LAYOUT_CENTER, RTFont.TEXT_LAYOUT_TOP, 0);
        }

        private RTImage getImage() {
            if(img != null) return img;
            RSClient client = Cooking.this.client;
            RSImage img0 = client.getItemImage(foodId, 1, 1, 3153952, 2, false);
            if(img0 == null) return null;
            RTImage img00 = RTImage.create(img0,false);
            img = img00;
            img.crop();
            return img00;
        }

        private String getRatioHex(double ratio) { // change colors based on how well your doing...
            return "FF8100"; //TODO
        }

    }




}
