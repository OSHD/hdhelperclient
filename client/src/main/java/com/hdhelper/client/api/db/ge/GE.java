package com.hdhelper.client.api.db.ge;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GE {

    private static final String BASE_URL = "http://services.runescape.com/m=itemdb_oldschool";
    private static final String ITEM_DETAIL_ENDPOINT = BASE_URL + "/api/catalogue/detail.json?item=";

    private static final ThreadLocal<Gson> GSON = new ThreadLocal<Gson>() {
        protected Gson initialValue() {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            return builder.create();
        }
    };

    public static void main(String[] args) throws IOException {
        int itemId = 1337;
        String db = getItemDetailUrl(itemId);
        String json = read(db);
        GEItemDetail detail = parse(json);

        System.out.println(detail.current.getPrice());
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

    public static String getItemDetailUrl(int id) {
        return ITEM_DETAIL_ENDPOINT + id;
    }

    public static GEItemDetail parse(String json) {
        Gson handle = GSON.get();
        return parse(GSON.get(),json);
    }

    private static GEItemDetail parse(Gson parser, String json) {
        ReturnObject detail = parser.fromJson(json, ReturnObject.class);
        return detail.item;
    }

}
