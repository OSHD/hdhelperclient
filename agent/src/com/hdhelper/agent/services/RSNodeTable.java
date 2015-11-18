package com.hdhelper.agent.services;

public interface RSNodeTable {

    RSNode[] getBuckets();
    int getCapacity();

    /*default RSNode get(long key) {
        int capacity = getCapacity();
        RSNode[] buckets = getBuckets();
        RSNode last = buckets[(int) (key & (long) (capacity - 1))];
        RSNode current;
        for (current = last.getNext(); current != last; current = current.getNext()) {
            if (current.getKey() == key) {
                return current;
            }
        }
        return null;
    }

    default RSNode[] getValues() {
        int var1 = 0;
        int size = getCapacity();
        RSNode[] buckets = getBuckets();
        List<RSNode> nodes = new ArrayList<>();
        while (var1 < size) {
            RSNode var2 = buckets[var1];
            RSNode var3 = var2.getNext();
            while (true) {
                if (var3 == var2) {
                    ++var1;
                    break;
                }
                nodes.add(var3);
                var3 = var3.getNext();
            }
        }
        return nodes.toArray(new RSNode[nodes.size()]);
    }
*/

}
