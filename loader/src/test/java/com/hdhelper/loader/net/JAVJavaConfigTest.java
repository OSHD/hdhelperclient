package com.hdhelper.loader.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class JAVJavaConfigTest {

    public static void main(String[] args) {

        try {

            URL url = new URL("http://oldschool1.runescape.com/jav_config.ws");

            InputStream stream = url.openStream();

            JAVJavaConfig cfg = JAVJavaConfig.decode(stream);

            System.out.println(cfg.getJarURL());

            stream.close();

        } catch (MalformedURLException ignored) {
            ignored.printStackTrace();
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }

    }

}
