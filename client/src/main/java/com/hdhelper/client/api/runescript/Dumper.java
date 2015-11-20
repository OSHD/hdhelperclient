package com.hdhelper.client.api.runescript;

import com.hdhelper.agent.services.RSRuneScript;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jamie on 11/16/2015.
 */
public class Dumper {

    static Map<Integer,String> OP_NAMES;
    private static void initNames() {
        if(OP_NAMES != null) return;
        Map<Integer,String> map = new HashMap<Integer,String>();
        for(Field f : RSOpcodes.class.getFields()) {
            try {
                final int op = f.getInt(null);
                final String name = f.getName();
                map.put(op,name);
            } catch (IllegalAccessException e) {
                throw new Error(e);
            }
        }
        OP_NAMES = Collections.unmodifiableMap(map);
    }

    private static String opName(int id) {
        initNames();
        String name = OP_NAMES.get(id);
        if(name != null) return name;
        return "UNKNOWN_" + id;
    }


    public static void dump(RSRuneScript rs) {
        System.out.println("(i_local=" + rs.getIntArgCount() + ",s_local=" + rs.getStrArgCount() + ",i_args=" + rs.getIntArgCount() + ",s_args=" + rs.getStrLocalCount() + "):");
        int[] opz       = rs.getOpcodes();
        String[] s_pool = rs.getStrOperands();
        int[] i_pool    = rs.getIntOperands();
        for(int i = 0; i < opz.length; i++) {
            int op = opz[i];
            String name = opName(op);
            System.out.println(i + ":"  + name + " <" + s_pool[i] + "," + i_pool[i] + ">");
        }
    }

}
