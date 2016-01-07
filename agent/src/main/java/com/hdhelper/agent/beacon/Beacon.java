package com.hdhelper.agent.Beacon;

import com.hdhelper.agent.SharedAgentSecrets;

public final class Beacon {

    private final String name;

    private int count;

    private final Object BEEP_LOCK = new Object();
    private final Object QUEUE_LOCK = new Object();

    private EventQueueItem root;

    Beacon(String name) { //Package Private
        this.name = name;
        root = new EventQueueItem(null);
        root.next = root;
        root.prev = root;
    }

    private void beep() {

        // Fire invokeLater events
        synchronized (QUEUE_LOCK) { //Prevent any other observers from being added
            Runnable next;
            while ((next = pop()) != null) {
                try {
                    next.run();
                } catch (Throwable ignored) {
                }
            }
        }

        //Notify any interested threads
        synchronized (BEEP_LOCK) {
            count++;
            BEEP_LOCK.notifyAll();
        }

    }

    private void push(Runnable runnable) {
        synchronized (QUEUE_LOCK) {
            EventQueueItem e = new EventQueueItem(runnable);
            e.prev = this.root.prev;
            e.next = this.root;
            e.prev.next = e;
            e.next.prev = e;
        }
    }

    private Runnable pop() {
        // Called when we hold the queue lock
        EventQueueItem next = this.root.next;
        if(next == this.root) return null;
        Runnable runnable = next.runnable;
        delete(next);
        return runnable;
    }

    private static void delete(EventQueueItem e) {
        e.prev.next = e.next;
        e.next.prev = e.prev;
        e.next = null;
        e.prev = null;
        e.runnable = null;
    }

    public void invokeLater(Runnable runnable) {
        push(runnable);
    }

    public void invokeAndWait(Runnable runnable) throws InterruptedException {
        NotifyRunnable nr = new NotifyRunnable(runnable);
        push(nr);
        synchronized (nr.lock) {
            nr.wait();
        }
    }

    public void await() throws InterruptedException {
        synchronized (BEEP_LOCK) {
            BEEP_LOCK.wait();
        }
    }

    public void await(long ms) throws InterruptedException {
        synchronized (BEEP_LOCK) {
            BEEP_LOCK.wait(ms);
        }
    }

    public String getName() {
        return name;
    }
    public int getCount() {
        return count;
    }

    static {
        SharedAgentSecrets.setBeaconAccess(new BeaconAccess() {
            @Override
            public void beep(Beacon beacon) {
                beacon.beep();
            }
        });
    }

}


class EventQueueItem {
    Runnable runnable;
    EventQueueItem next;
    EventQueueItem prev;

    EventQueueItem() {
    }

    EventQueueItem(Runnable runnable) {
        if(runnable == null)
            throw new IllegalArgumentException("runnable == null");
        this.runnable = runnable;
    }
}

class NotifyRunnable implements Runnable {
    final Object lock = new Object();
    final Runnable runnable;
    NotifyRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
    @Override
    public void run() {
        try {
            runnable.run();
        } catch (Throwable ignored) {} //ensure we notify
        synchronized (lock) {
            lock.notify(); //Only one thread...
        }
    }
}

