package com.hdhelper.agent.bs.impl.patch;

import com.google.gson.*;
import jdk.internal.org.objectweb.asm.Type;

import java.text.NumberFormat;
import java.util.*;

public class GPatch {

    int rev;
    int crc;

    Map<String,GClass> classes;

    Map<String,String> private2Public = new HashMap<>();

    public GClass getGClass(String pubName) {
        return classes.get(pubName);
    }

    public static GPatch parse(String gson) {
        GsonBuilder b = new GsonBuilder();
        b.setPrettyPrinting();
        Gson gson0 = b.create();
        GPatch patch = gson0.fromJson(gson, GPatch.class);

        // Override:

        GClass client = patch.getGClass("Client");

        client.fields.put("screenScale",new GField( "client", "ob", "I", -900882037 ));
        client.fields.put("screenWidth",new GField( "client", "om", "I", -601427865  ));
        client.fields.put("screenHeight",new GField( "client", "oo", "I", 807668933  ));
        client.fields.put("floorLevel",new GField("ez","gn", "I",238600101));
        client.fields.get("regionBaseX").decoder =  -1234650781;
        client.fields.get("regionBaseY").decoder = -706637253;
        client.methods.get("getItemDefinition").predicate = -1194634781;

        client.fields.put("chunkIds", new GField("client","dm","[I", null));
        client.fields.put("XTEAKeys", new GField("ei","de","[[I", null));

     /*   GClass character = patch.getGClass("Character");
        character.fields.put("targetIndex",new GField( character.name, "bu", "I",-1836127123 ));
        character.fields.put("orientation",new GField( character.name, "cg","I",527854075));*/

    /*    GClass player = patch.getGClass("Player");
        player.fields.put("height",new GField(player.name,"h","I",-1507293645));*/

    /*    GClass entityMarker = patch.getGClass("EntityMarker");
        entityMarker.fields.put("regionY",new GField(entityMarker.name,"h","I",-959893399));*/

        GClass gpi = patch.getGClass("GPI");
        gpi.fields.put("playerIndices",new GField("ae","p","[I",null));
        gpi.fields.put("playerCount",new GField("ae","s","I",907260627));

        patch.classes.put("Graphics",new GClass("cq"));

    /*    GClass landscape = patch.getGClass("Landscape");
        landscape.fields.put("visibilityMap",new GField("ci","bw","[[[[Z",null));


*/

        GClass image = patch.getGClass("Sprite");
        image.fields.put("insetX",new GField(image.name,"d","I",null));
        image.fields.put("insetY",new GField(image.name,"f","I",null));
        image.fields.put("maxX",new GField(image.name,"r","I",null));
        image.fields.put("maxY",new GField(image.name,"l","I",null));

        return patch;

    }

    // private -> public
    public String mapPrivate2Public(String privateName) {
        String pub = private2Public.get(privateName);
        if(pub != null) return pub;
        for(Map.Entry<String,GClass> clazz : classes.entrySet()) {
            String pubName = clazz.getKey();
            GClass gclass  = clazz.getValue();
            if(gclass.name.equals(privateName)) {
                private2Public.put(privateName, pubName);
                return pubName;
            }
        }
        private2Public.put(privateName, privateName);
        return privateName;
    }

    public String mapDesc(String typeName) {
        Type var2 = Type.getType(typeName);
        switch(var2.getSort()) {
            case Type.ARRAY: {
                String var3 = mapDesc(var2.getElementType().getDescriptor());

                for (int var5 = 0; var5 < var2.getDimensions(); ++var5) {
                    var3 = '[' + var3;
                }

                return var3;
            }

            case Type.OBJECT: {
                String var4 = mapPrivate2Public(var2.getInternalName());
                if (var4 != null) {
                    return 'L' + var4 + ';';
                }
            }

            default:
                return typeName;
        }
    }

    public String mapMethodDesc(String var1) {
        if("()V".equals(var1)) {
            return var1;
        } else {
            Type[] var2 = Type.getArgumentTypes(var1);
            StringBuilder var3 = new StringBuilder("(");

            for(int var4 = 0; var4 < var2.length; ++var4) {
                var3.append(this.mapDesc(var2[var4].getDescriptor()));
            }

            Type var5 = Type.getReturnType(var1);
            if(var5 == Type.VOID_TYPE) {
                var3.append(")V");
                return var3.toString();
            } else {
                var3.append(')').append(this.mapDesc(var5.getDescriptor()));
                return var3.toString();
            }
        }
    }

    //TODO super lazy
    // GSon wraps numbers into their own
    public static Number toJavaNumber(Number n) {
        Number num = null;
        try {
            num = NumberFormat.getInstance().parse(n.toString());
        } catch (Exception e) {
            throw new Error(e);
        }
        return num;
    }


}
