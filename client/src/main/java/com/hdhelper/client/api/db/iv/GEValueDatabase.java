package com.hdhelper.client.api.db.iv;

import com.hdhelper.client.api.db.iv.ge.GE;
import com.hdhelper.client.api.db.iv.ge.GEItemDetail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GEValueDatabase implements ItemValueDatabase {

    private Map<Integer,GEItemValue> values = new HashMap<Integer, GEItemValue>();

    ExecutorService executor;

    private final Object LOCK = new Object();

    protected GEValueDatabase() {
    }

    @Override
    public ItemValue getValue(int id) {

        synchronized (LOCK) {

            GEItemValue cache = values.get(id);
            if(cache != null) return cache;

            GEItemValue value = new GEItemValue(id);
            if(executor == null) {
                initPool();
            }

            executor.submit(new GEUpdateWorker(value));
            values.put(id,value);

            return value;

        }

    }

    private void initPool() {
        executor = Executors.newFixedThreadPool(1);
    }

}

class GEUpdateWorker implements Runnable {



    final GEItemValue item;

    public GEUpdateWorker(GEItemValue item) {
        this.item = item;
    }

    @Override
    public void run() {
        doUpdate(item,0);
    }

    private static void doUpdate(GEItemValue p, int attempt) {
        try {

            String spec = GE.getItemDetailUrl(p.getId());
            String json = parsePage(spec);

            if(json == null) {

                System.out.println("404:" + spec);
                p.setState(ItemValue.STATE_ERROR);

            } else if(!json.isEmpty() && !json.equals("RETRY")) {

                GEItemDetail detail = GE.parse(json);
                // ^ TODO should we also cache the other data?

                if(detail == null) {
                    System.out.println("Bad JSON:'" + json + "'");
                    p.setState(ItemValue.STATE_UPDATED);
                    return;
                }

                int price = detail.getCurrentPrice().getPrice();

                p.setValue(price);
                p.setState(ItemValue.STATE_UPDATED);

                System.out.println("Updated:" + p.getId() + "@" + price);

            }

            System.out.println("Try again(" + attempt + "):" + spec);
            Thread.sleep(5500);
            doUpdate(p,attempt+1);


        } catch (Throwable ignored) {

        //    System.out.println("EEK:");

            p.setState(ItemValue.STATE_ERROR);

            ignored.printStackTrace();

        }

    }

    private static String parsePage(String db) {
        InputStream in = null;
        try {
            URL url = new URL(db);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
            con.setRequestProperty("Accept-Encoding","gzip, deflate, sdch");
            con.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/web");

            if(con.getResponseCode() == 404) return null;

            if(con.getResponseCode() != 200) return "RETRY";

            in = url.openStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read;
            while ((read = in.read(buffer)) > 0) {
                baos.write(buffer, 0, read);
            }
            byte[] data = baos.toByteArray();

            if(data.length == 0) {
                System.out.println(con.getErrorStream().read());
            }
            return new String(data);
        } catch (Throwable ignored) {
        } finally {
            if(in != null) try {
                in.close();
            } catch (IOException ignored) {
            }
        }
        return "RETRY";
    }

}
