package com.hdhelper.agent.bus;

import java.lang.reflect.Array;
import java.util.EventListener;

public class Multicaster implements EventListener {

    protected final EventListener a, b;

    /**
     * Creates an event multicaster instance which chains listener-a
     * with listener-b. Input parameters <code>a</code> and <code>b</code>
     * should not be <code>null</code>, though implementations may vary in
     * choosing whether or not to throw <code>NullPointerException</code>
     * in that case.
     * @param a listener-a
     * @param b listener-b
     */
    protected Multicaster(EventListener a, EventListener b) {
        this.a = a; this.b = b;
    }

    /**
     * Removes a listener from this multicaster.
     * <p>
     * The returned multicaster contains all the listeners in this
     * multicaster with the exception of all occurrences of {@code oldl}.
     * If the resulting multicaster contains only one regular listener
     * the regular listener may be returned.  If the resulting multicaster
     * is empty, then {@code null} may be returned instead.
     * <p>
     * No exception is thrown if {@code oldl} is {@code null}.
     *
     * @param oldl the listener to be removed
     * @return resulting listener
     */
    protected EventListener remove(EventListener oldl) {
        if (oldl == a)  return b;
        if (oldl == b)  return a;
        EventListener a2 = removeInternal(a, oldl);
        EventListener b2 = removeInternal(b, oldl);
        if (a2 == a && b2 == b) {
            return this;        // it's not here
        }
        return addInternal(a2, b2);
    }



    /**
     * Returns the resulting multicast listener from adding listener-a
     * and listener-b together.
     * If listener-a is null, it returns listener-b;
     * If listener-b is null, it returns listener-a
     * If neither are null, then it creates and returns
     * a new AWTEventMulticaster instance which chains a with b.
     * @param a event listener-a
     * @param b event listener-b
     */
    protected static EventListener addInternal(EventListener a, EventListener b) {
        if (a == null)  return b;
        if (b == null)  return a;
        return new Multicaster(a, b);
    }

    /**
     * Returns the resulting multicast listener after removing the
     * old listener from listener-l.
     * If listener-l equals the old listener OR listener-l is null,
     * returns null.
     * Else if listener-l is an instance of AWTEventMulticaster,
     * then it removes the old listener from it.
     * Else, returns listener l.
     * @param l the listener being removed from
     * @param oldl the listener being removed
     */
    protected static EventListener removeInternal(EventListener l, EventListener oldl) {
        if (l == oldl || l == null) {
            return null;
        } else if (l instanceof Multicaster) {
            return ((Multicaster)l).remove(oldl);
        } else {
            return l;           // it's not here
        }
    }


    private static int getListenerCount(EventListener l, Class<?> listenerType) {
        if (l instanceof Multicaster) {
            Multicaster mc = (Multicaster)l;
            return getListenerCount(mc.a, listenerType) +
                    getListenerCount(mc.b, listenerType);
        }
        else {
            // Only count listeners of correct type
            return listenerType.isInstance(l) ? 1 : 0;
        }
    }

    private static int populateListenerArray(EventListener[] a, EventListener l, int index) {
        if (l instanceof Multicaster) {
            Multicaster mc = (Multicaster)l;
            int lhs = populateListenerArray(a, mc.a, index);
            return populateListenerArray(a, mc.b, lhs);
        }
        else if (a.getClass().getComponentType().isInstance(l)) {
            a[index] = l;
            return index + 1;
        }
        // Skip nulls, instances of wrong class
        else {
            return index;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends EventListener> T[]
    getListeners(EventListener l, Class<T> listenerType)
    {
        if (listenerType == null) {
            throw new NullPointerException ("Listener type should not be null");
        }

        int n = getListenerCount(l, listenerType);
        T[] result = (T[]) Array.newInstance(listenerType, n);
        populateListenerArray(result, l, 0);
        return result;
    }

}
