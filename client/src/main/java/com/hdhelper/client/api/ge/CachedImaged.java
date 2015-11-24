package com.hdhelper.client.api.ge;

import com.hdhelper.agent.services.RSImage;
import com.hdhelper.client.Main;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CachedImaged {

    public static final int GRAND_EXCHANGE_OFFER = 1112;
    public static final int HIGH_ALCH = 41;

    private static Map<Integer,RSImage> cache
            = new HashMap<Integer, RSImage>();

    public static RSImage getImage(int id) {
        RSImage img = cache.get(id);
        if(img != null) return img;
        try {
            ClassLoader loader = Main.client.getClass().getClassLoader();
            Field f = loader.loadClass("bj").getDeclaredField("u");
            Method m = loader.loadClass("ar").getDeclaredMethod("j", loader.loadClass("fa"), int.class, int.class, int.class);
            f.setAccessible(true);
            m.setAccessible(true);
            Object o = f.get(null);
            Object oo = m.invoke(null, o, id, 0, 2097572161);
            RSImage hit = (RSImage) oo;
            cache.put(id,hit);
            return hit;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

}
