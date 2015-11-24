package com.hdhelper.client.api.ge;

import com.hdhelper.agent.GlyphCapture;
import com.hdhelper.agent.GlyphCaptureFactory;

import java.util.HashMap;
import java.util.Map;

public class RTGlyphCapture extends GlyphCapture {

    public static final GlyphCaptureFactory FACTORY
            = new Factory();

    private static Map<String,RTGlyphCapture> captures
            = new HashMap<String, RTGlyphCapture>();

    private boolean captured = false;

    RTGlyphVector vector;

    private RTGlyphCapture(String name) {
        super(name);
    }

    static RTGlyphCapture get(String name) {
        RTGlyphCapture cache = captures.get(name);
        if(cache != null) return cache;
        RTGlyphCapture capture = new RTGlyphCapture(name);
        captures.put(name, capture);
        return capture;
    }

    public boolean isCaptured() {
        return captured;
    }

    @Override
    public void capture(byte[] meta, int[] drawOffsetX, int[] drawOffsetY, int[] widths, int[] height, int[] colorMap, byte[][] bitmap) {
        if(captured)
            throw new IllegalStateException(name + " was already captured");
        vector = new RTGlyphVector(meta,drawOffsetX,drawOffsetY,widths,height,colorMap,bitmap);
        captured = true;
    }

    private static final class Factory implements GlyphCaptureFactory {
        @Override
        public GlyphCapture getCapture(String name) {
            return get(name);
        }
    }

}
