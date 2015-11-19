package com.hdhelper.loader.net;


import java.io.*;
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

    public String getJarURLSpec() {
        return getCodeBase() + getInitialJar();
    }

    public URL getJarURL() {
        try {
            return new URL(getJarURLSpec());
        } catch (MalformedURLException ignored) {
            throw new Error("bad url:" + ("base=" + getCodeBase() + ",initial=" + getInitialJar() +
                    ",url" + (getCodeBase() + getInitialJar())) );
        }
    }

    public static JAVJavaConfig decode(BufferedReader in) throws IOException {
        try {
            JAVJavaConfig cfg = new JAVJavaConfig();
            String line;
            while ((line = in.readLine()) != null) {
                int k = line.indexOf('=');
                String key   = line.substring(0, k);
                String value = line.substring(k + 1, line.length());
                if (key.equals("param")) {
                    int d = value.indexOf('=');
                    String param_key = value.substring(0, d);
                    String param_val = value.substring(d + 1, value.length());
                    cfg.params.put(param_key, param_val);
                    continue;
                } else if (key.equals("msg")) {
                    int d = value.indexOf('=');
                    String msg_key = value.substring(0, d);
                    String msg_val = value.substring(d + 1, value.length());
                    cfg.messages.put(msg_key, msg_val);
                    continue;
                }
                assert !key.isEmpty();
                cfg.misc.put(key, value);
            }
            return cfg;
        } catch (IOException stream_err) {
            throw stream_err; // Not our fault
        } catch (IndexOutOfBoundsException format_problem) {
            throw new Error("Decode error"); // Something is wrong with the format
        }
    }

    public static JAVJavaConfig decode(InputStream src) throws IOException {
        Reader reader = new InputStreamReader(src);
        BufferedReader buff_reader = new BufferedReader(reader);
        return decode(buff_reader);
    }

    public static JAVJavaConfig decode(byte[] bytes) throws IOException {
        return decode(new ByteArrayInputStream(bytes));
    }

}
