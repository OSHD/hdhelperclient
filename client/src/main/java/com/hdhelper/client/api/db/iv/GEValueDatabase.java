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
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class GEValueDatabase implements ItemValueDatabase {

    private Map<Integer,GEItemValue> values
            = new HashMap<Integer, GEItemValue>();
    private GEUpdateThread updateThread;
    private final Object LOCK = new Object();

    protected GEValueDatabase() {
    }

    @Override
    public ItemValue getValue(int id) {

        synchronized (LOCK) {

            GEItemValue cache = values.get(id);
            if(cache != null) return cache;

            GEItemValue value = new GEItemValue(id);
            if(updateThread == null) {
                initUpdateThread();
            }
            updateThread.updateLater(value);
            values.put(id,value);
            return value;

        }

    }

    public void initUpdateThread() {
        updateThread = new GEUpdateThread();
        updateThread.setName("GEUpdateThread");
        updateThread.setPriority(Thread.NORM_PRIORITY + 1);
        updateThread.setDaemon(true);
        updateThread.start();
    }

}

class GEUpdateThread extends Thread  {

    boolean doRun;
    final BlockingDeque<GEItemValue> deque
            = new LinkedBlockingDeque<GEItemValue>();

    public GEUpdateThread() {
        doRun = true;
    }


    void updateLater(GEItemValue item) {
        deque.add(item);
    }

    GEItemValue poll() throws InterruptedException {
        return deque.take();
    }

    @Override
    public void run() {
        while (doRun && !isInterrupted()) {
            try {
                GEItemValue next = poll();
                doUpdate(next);
            } catch (InterruptedException e) {
                //Respect the interrupt
                break;
            }
        }
    }


    private static void doUpdate(GEItemValue p) {
        try {

            String spec = GE.getItemDetailUrl(p.getId());
            String json = parsePage(spec);

            if(json == null) {
                p.setState(ItemValue.STATE_ERROR);
                return;
            }

            GEItemDetail detail = GE.parse(json);
            // ^ TODO should we also cache the other data?
            int price = detail.getCurrentPrice().getPrice();

            p.setValue(price);
            p.setState(ItemValue.STATE_UPDATED);

        } catch (Throwable ignored) {

            p.setState(ItemValue.STATE_ERROR);

            ignored.printStackTrace();

        }

    }

    private static String parsePage(String db) {
        InputStream in = null;
        try {
            URL url = new URL(db);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if(con.getResponseCode() != 200) return null;
            in = url.openStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read;
            while ((read = in.read(buffer)) > 0) {
                baos.write(buffer, 0, read);
            }
            byte[] data = baos.toByteArray();
            return new String(data);
        } catch (Throwable ignored) {
            ignored.printStackTrace();
        } finally {
            if(in != null) try {
                in.close();
            } catch (IOException ignored) {
            }
        }
        return null;
    }


}
