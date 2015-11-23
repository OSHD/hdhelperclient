package com.hdhelper.agent.event;

import java.util.EventObject;

/**
 * The root class from which all event state objects shall be derived.
 *
 * All Events are constructed with a reference to the object, the "source",
 * that is logically deemed to be the object upon which the Event in question
 * initially occurred upon. In cases where the source is non-explicit, its
 * often set null.
 */
public abstract class RSEvent extends EventObject {

    int id;     // The event's id.

    int cycle;  // The engine cycle the when event was produced.

    /**
     * The event mask for selecting renderable events.
     * @see MessageEvent
     */
    public static final long MESSAGE_EVENT_MASK     = 1L << 1;

    /**
     * The event mask for selecting action events.
     * @see ActionEvent
     */
    public static final long ACTION_EVENT_MASK      = 1L << 2;

    /**
     * The event mask for selecting variable events.
     * @see VariableEvent
     */
    public static final long VARIABLE_EVENT_MASK    = 1L << 3;

    public static final Object NULL_SOURCE = new Object();

    public RSEvent(int id, int cycle) {
        this(null,id,cycle);
    }

    public RSEvent(Object source, int id, int cycle) {
        super(source == null ? NULL_SOURCE : source); //EventObject does not allow for null sources
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
        return source == NULL_SOURCE;
    }

    public int getEventCycle() {
        return cycle;
    }

}
