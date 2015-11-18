package com.hdhelper.loader.net;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

// Data Object for extracting interesting information within
// a java-config.ws resource which is used to configure their
// 'Jagex Applet Viewer' (JAV).
public class JAVJavaConfig {

    private static final String ADVERTURL_KEY      = "adverturl";
    private static final String CODEBASE_KEY       = "codebase";
    private static final String INITIAL_JAR_KEY    = "initial_jar";
    private static final String INITIAL_CLASS_KEY  = "initial_class";

    private Map<String,String> params;
    private Map<String,String> messages;
    private Map<String,String> misc;

    private JAVJavaConfig() {
        params = new HashMap<String, String>();
        messages = new HashMap<String, String>();
        misc = new HashMap<String, String>();
    }


    public String getParam(String id) {
        return params.get(id);
    }

    public String getMessage(String key) {
        return messages.get(key);
    }

    public String getCodeBase() {
        return misc.get(CODEBASE_KEY);
    }

    public String getInitialJar() {
        return misc.get(INITIAL_JAR_KEY);
    }

    public String getInitialClass() {
        return misc.get(INITIAL_CLASS_KEY);
    }

    public URL getJarURL() {
        try {
            return new URL(getCodeBase() + getInitialJar());
        } catch (MalformedURLException ignored) {
            throw new Error(ignored); //TODO handle this case?
        }
    }

    public static JAVJavaConfig parse(BufferedReader in) throws IOException {
        JAVJavaConfig cfg = new JAVJavaConfig();
        String line;
        while ((line = in.readLine()) != null) {
            int k = line.indexOf('=');
            String key = line.substring(0, k);
            String value = line.substring(k + 1, line.length());
            if(key.equals("param")) {
                int d = value.indexOf('=');
                String param_key = value.substring(0, d);
                String param_val = value.substring(d+1,value.length());
                cfg.params.put(param_key,param_val);
                continue;
            } else if(key.equals("msg")) {
                int d = value.indexOf('=');
                String msg_key = value.substring(0, d);
                String msg_val = value.substring(d+1,value.length());
                cfg.messages.put(msg_key,msg_val);
                continue;
            }
            cfg.misc.put(key,value);
        }
        return cfg;
    }

    public static void main(String[] args) {

        try {

            URL url = new URL("http://oldschool1.runescape.com/jav_config.ws");

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            System.out.println(parse(in).getInitialJar());

            in.close();

        } catch (MalformedURLException ignored) {
        } catch (IOException ignored) {
        }

    }



}
