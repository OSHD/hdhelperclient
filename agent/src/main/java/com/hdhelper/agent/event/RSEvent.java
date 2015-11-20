package com.hdhelper.agent.event;

import java.util.EventObject;

/**
 * The root class from which all event state objects shall be derived.
 *
 * All Events are constructed with a reference to the object, the "source",
 * that is logically deemed to be the object upon which the Event in question
 * initially occurred upon. In cases where the source is non-explicit, its
 * often set to the client of its context, or null.
 */
public abstract class RSEvent extends EventObject {

    int id;     // The event's id.

    int cycle;  // The engine cycle the when event was produced.

    /**
     * The event mask for selecting renderable events.
     * @see MessageEvent
     */
    public static final long MESSAGE_EVENT_MASK     = 1L << 1;


    public RSEvent(Object source, int id, int cycle) {
        super(source);
        this.id = id;
        this.cycle = cycle;
    }


    public int getId() {
        return id;
    }

    public int getCycle() {
        return cycle;
    }

    public boolean getStaticEvent() {
        return source == null;
    }

    public int getEventCycle() {
        return cycle;
    }

}
