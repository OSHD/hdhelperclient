package com.hdhelper.client.api.db;

public class GEValueDatabase implements ItemValueDatabase {

    @Override
    public ItemValue getValue(int id) {
        return null;
    }

   /* private UpdateThread updateThread;
    private void init() {
        if(updateThread == null) {
            updateThread = new UpdateThread();
            updateThread.setName("GEDBNetThread");
            updateThread.setPriority(Thread.NORM_PRIORITY+1);
            updateThread.setDaemon(true);
            updateThread.run();
        }
    }*/



}

class GEUpdateThread extends Thread  {

    boolean doRun;

    public GEUpdateThread() {
        doRun = true;
    }

    @Override
    public void run() {
        while (doRun && !isInterrupted()) {

        }
    }

}

