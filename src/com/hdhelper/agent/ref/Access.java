package com.hdhelper.agent.ref;

//TODO make private, and accessible only to the client
public class Access {

    public static RefNode createRoot() {
        RefNode root = new RefNode();
        root.next = root;
        root.prev = root;
        return root;
    }

    public static void add(RefNode root, RefNode next) {
        next.prev = root.prev;
        next.next = root;
        next.prev.next = next;
        next.next.prev = next;
    }

    public static void destroy(RefNode root) {
        while (true) {
            RefNode var1 = root.next;
            if (var1 == root) {
                return;
            }
            var1.destroy();
        }
    }

}
