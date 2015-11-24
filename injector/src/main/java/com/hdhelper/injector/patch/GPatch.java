package com.hdhelper.injector.patch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.objectweb.asm.Type;

import java.awt.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class GPatch {

    int rev;
    int crc;

    Map<String,GClass> classes;

    Map<String,String> private2Public = new HashMap<String,String>();

    public GClass getGClass(String pubName) {
        return classes.get(pubName);
    }

    public static GPatch parse(String gson) {
        GsonBuilder b = new GsonBuilder();
        b.setPrettyPrinting();
        Gson gson0 = b.create();
        GPatch patch = gson0.fromJson(gson, GPatch.class);
        override(patch);
        return patch;

    }


    private static void override(GPatch patch) {

        GClass client = patch.getGClass("Client");

        client.fields.put("screenScale",new GField( "client", "oy", "I", 163765829 ));
        client.fields.put("screenWidth",new GField( "client", "oo", "I", 1797905553  ));
        client.fields.put("screenHeight",new GField( "client", "oq", "I", 294769919  ));
        client.fields.put("engineCycle",new GField("client","q","I",-61212269));
        // client.fields.put("floorLevel",new GField("ez","gn", "I",238600101));
        //   client.fields.get("regionBaseX").decoder =  -1234650781;
        //  client.fields.get("regionBaseY").decoder = -706637253;
        client.methods.get("getItemDefinition").predicate = 0;


        client.fields.put("chunkIds", new GField("fg","dr","[I", null));
        client.fields.put("XTEAKeys", new GField("fu","di","[[I", null));

     /*   GClass character = patch.getGClass("Character");
        character.fields.put("targetIndex",new GField( character.name, "bb", "I",1495396491 ));*/

        // character.fields.put("orientation",new GField( character.name, "cg","I",527854075));*/

    /*    GClass player = patch.getGClass("Player");
        player.fields.put("height",new GField(player.name,"h","I",-1507293645));*/

    /*    GClass entityMarker = patch.getGClass("EntityMarker");
        entityMarker.fields.put("regionY",new GField(entityMarker.name,"h","I",-959893399));*/

        GClass gpi = patch.getGClass("GPI");
        gpi.fields.put("playerIndices",new GField("ae","p","[I",null));
        gpi.fields.put("playerCount",new GField("ae","s","I",907260627));

        patch.classes.put("Graphics",new GClass("cb"));

        patch.getGClass("Landscape").methods.put("addObject",new GMethod("cp","x","(IIIIIILcd;III)Z",null));
        patch.getGClass("Landscape").methods.put("removeObject",new GMethod("cp","u","(III)V",null));
    /*    GClass landscape = patch.getGClass("Landscape");
        landscape.fields.put("visibilityMap",new GField("ci","bw","[[[[Z",null));


*/

        client.fields.put("canvas", new GField("n","qd",Type.getDescriptor(Canvas.class),null));

        client.getMethod("getRuneScript").predicate = -849079302;

        //TODO hook
        GClass image = patch.getGClass("Sprite");
        image.fields.put("insetX",new GField(image.name,"l","I",null));
        image.fields.put("insetY",new GField(image.name,"u","I",null));
        image.fields.put("maxX",new GField(image.name,"a","I",null));
        image.fields.put("maxY",new GField(image.name,"h","I",null));
        image.fields.put("height",new GField(image.name,"f","I",null));
        image.fields.put("width",new GField(image.name,"m","I",null));

        GClass widget = patch.getGClass("Widget");
        if(widget.methods==null) widget.methods = new HashMap<String, GMethod>();
        widget.methods.put("getImage",new GMethod(widget.name,"e","(II)Lcq;",null));
        widget.fields.put("spriteIds", new GField(widget.name, "cp", "[I", null));

        //TODO hook
        GClass message = new GClass("av");
        message.fields.put("cycle",new GField("av","m","I",29620511));
        message.fields.put("index",new GField("av","j","I",684019183));
        message.fields.put("message",new GField("av","a",Type.getDescriptor(String.class),null));
        message.fields.put("channel",new GField("av","u",Type.getDescriptor(String.class),null));
        message.fields.put("sender",new GField("av","l",Type.getDescriptor(String.class),null));
        message.fields.put("type",new GField("av","f","I",-1804049261));
        patch.classes.put("Message", message);


        GClass character = patch.getGClass("Character"); //TODO hook
        character.fields.put("idleAnimation",new GField(character.name,"ax","I",-46880891));
        character.fields.put("walkAnimation",new GField(character.name,"av","I",1668954069));
        character.fields.put("runAnimation",new GField(character.name,"ac","I",-288021961));
        character.fields.put("anint2341",new GField(character.name,"bq","I",932293205));

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
