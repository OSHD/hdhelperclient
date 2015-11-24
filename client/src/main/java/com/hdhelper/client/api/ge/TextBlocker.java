package com.hdhelper.client.api.ge;

public class TextBlocker<T> {

    final int capacity;

    int[] xloc;
    int[] yloc;
    int[] widths;
    int[] heights;
    Object[] entries;

    int size;

    public TextBlocker(int max) {
        xloc    = new int[max];
        yloc    = new int[max];
        widths  = new int[max];
        heights = new int[max];
        entries = new Object[max];
        this.capacity = max;
    }


    public void reset() {
        size = 0;
        cur = 0;
    }

    public void flush() {
        xloc    = null;
        yloc    = null;
        widths  = null;
        heights = null;
        entries = null;
    }

    int cur = -1;
    int curX = -1;
    int curY = -1;
    Object entry;

    public boolean hasNext() {
        return cur < size;
    }

    public void put(int x, int y, int w, int h, Object o) {

        xloc[size] = x;
        yloc[size] = y;
        widths[size] = w;
        heights[size] = h;
        entries[size] = o;
        size++;
    }

    public void next() {

        if(!hasNext()) return;

        final int pos = cur++;

     //   System.out.println(pos);
        
        int x = xloc[cur];
        int y = yloc[cur];
        int width = widths[cur];
        int height = heights[cur];

       // System.out.println(cur + "," + width + "," + height);
        
        boolean next = true;

        while (next) {

            next = false;

            for (int i = 0; i < pos; ++i) {

                if (y + 2 > yloc[i] - heights[i]) {

                    if (y - height < 2 + yloc[i]) {

                        if (x - width < xloc[i] + widths[i]) {

                            if (x + width > xloc[i] - widths[i]) {

                                if (yloc[i] - heights[i] < y) {

                                    y = yloc[i] - heights[i];
                                    next = true;

                                }
                            }
                        }
                    }
                }
            }
        }


        curX = xloc[pos];
        curY = (yloc[pos] = y);
        entry = entries[pos];

    }

    public int getX() {
        return curX;
    }

    public int getY() {
        return curY;
    }

    public Object getEntry() {
        return entry;
    }

}
