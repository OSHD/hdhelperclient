package com.hdhelper.injector;

import com.bytescript.common.ReflectionProfiler;
import com.bytescript.compiler.BCompiler;
import com.hdhelper.injector.bs.ResolverImpl;
import com.hdhelper.injector.bs.scripts.Client;
import com.hdhelper.injector.bs.scripts.GPI;
import com.hdhelper.injector.bs.scripts.RuneScript;
import com.hdhelper.injector.bs.scripts.cache.ItemDefinition;
import com.hdhelper.injector.bs.scripts.cache.NpcDefinition;
import com.hdhelper.injector.bs.scripts.cache.ObjectDefinition;
import com.hdhelper.injector.bs.scripts.collection.Deque;
import com.hdhelper.injector.bs.scripts.collection.DualNode;
import com.hdhelper.injector.bs.scripts.collection.Node;
import com.hdhelper.injector.bs.scripts.collection.NodeTable;
import com.hdhelper.injector.bs.scripts.entity.Entity;
import com.hdhelper.injector.bs.scripts.entity.GroundItem;
import com.hdhelper.injector.bs.scripts.entity.Npc;
import com.hdhelper.injector.bs.scripts.entity.Player;
import com.hdhelper.injector.bs.scripts.graphics.Image;
import com.hdhelper.injector.bs.scripts.ls.*;
import com.hdhelper.injector.bs.scripts.util.ItemTable;
import com.hdhelper.injector.bs.scripts.util.PlayerConfig;
import com.hdhelper.injector.mod.*;
import com.hdhelper.injector.patch.GPatch;
import com.hdhelper.injector.util.ASMUtil;
import com.hdhelper.injector.util.ClassWriterFix;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;


public final class Injector {

    private static final Logger LOG = Logger.getLogger(Injector.class.getName());

    private HashMap<String, byte[]> classes_final = new HashMap<String,byte[]>();
    private HashMap<String, ClassNode> classes = new HashMap<String,ClassNode>();

    private final InjectorConfig cfg;

    public Injector(InjectorConfig cfg) {
        this.cfg = cfg;
    }

    private void loadJar() throws InterruptedException, IOException {
        LOG.info("Loading Jar...");
       // ClientLoader.loadClient(cfg.getWorld(),cfg.getClientLoc());
    }

    public void destroy() {
        classes.clear();
    }

    public static String getHooks() throws IOException {

        InputStream input = Injector.class.getResourceAsStream("hooks.gson");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(input));

        StringBuilder b = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            b.append(inputLine);
        in.close();

        return b.toString();

    }

    public ClassLoader inject() throws Exception {
        Map<String,byte[]> classes = inject0();
        ByteClassLoader loader = new ByteClassLoader(classes);
        loader.save(new File("")/*cfg.getSaveLoc()*/);
        loader.verify();

        return loader;
    }

    private Map<String,byte[]> inject0() throws Exception {

        this.cfg.verify();

        loadJar();

        Map<String,byte[]> defs = inflate();
        ClassLoader default_def_loader = new ByteClassLoader(defs);


        String gson = getHooks();

        GPatch cr = GPatch.parse(gson);
        BCompiler compiler = new BCompiler(new ReflectionProfiler(),new ResolverImpl(cr));


        System.out.println("COMPILE SCRIPTS");

        compiler.inject(Client.class, classes);

        compiler.inject(Node.class, classes);
        compiler.inject(com.hdhelper.injector.bs.scripts.entity.Character.class, classes);
        compiler.inject(Deque.class, classes);
        compiler.inject(DualNode.class, classes);
        compiler.inject(Entity.class, classes);
        //compiler.inject(GameEngine.class, classes);
        compiler.inject(ItemDefinition.class, classes);
        compiler.inject(NodeTable.class, classes);
        compiler.inject(Npc.class,classes);
        compiler.inject(NpcDefinition.class, classes);
        compiler.inject(ObjectDefinition.class,classes);
        compiler.inject(Player.class,classes);
        compiler.inject(PlayerConfig.class,classes);
        compiler.inject(GroundItem.class,classes);
        compiler.inject(ItemTable.class,classes);

        compiler.inject(Landscape.class,classes);
        compiler.inject(BoundaryDecoration.class,classes);
        compiler.inject(EntityMarker.class,classes);
        compiler.inject(ItemPile.class,classes);
        compiler.inject(LandscapeTile.class,classes);
        compiler.inject(TileDecoration.class, classes);
        compiler.inject(Boundary.class, classes);

        compiler.inject(Image.class, classes);

        compiler.inject(RuneScript.class,classes);

        compiler.inject(GPI.class, classes);



        new ClientCanvasMod(classes,cfg).inject();
        new GraphicsEngineMod(classes,cfg).inject();
        new RenderMod(classes,cfg).inject();
        new XTEADumpMod(classes,cfg).inject();


        new LandscapeMod(classes,cr).inject(classes,cr);

        EngineMod.inject(classes.get(cr.getGClass("GameEngine").getName()));

        System.out.println("DONE");

/*
        for(InjectionModule mod : InjectionModule.getModules()) {
            mod.inject(classes);
        }*/



      /*  int id = 0;
        for(MethodNode mn : (List<MethodNode>) classes.get("fl").methods) {
            if(mn.name.equals("e") && Modifier.isStatic(mn.access)) {
                InsnList stack = new InsnList();
                stack.add(new VarInsnNode(Opcodes.ALOAD, 0));
                stack.add(new FieldInsnNode(Opcodes.GETFIELD,"l","l",Type.getDescriptor(Object[].class)));
                stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Callback.class),"event","([Ljava/lang/Object;)V",false));
                mn.instructions.insertBefore(mn.instructions.getFirst(), stack);
            }
        }*/

      for(MethodNode mn : (List<MethodNode>) classes.get("ae").methods) {
            if(mn.name.equals("<init>")) {
                InsnList stack = new InsnList();
                stack.add(new VarInsnNode(Opcodes.ILOAD, 1));
                stack.add(new VarInsnNode(Opcodes.ALOAD, 2));
                stack.add(new VarInsnNode(Opcodes.ALOAD, 3));
                stack.add(new VarInsnNode(Opcodes.ALOAD, 4));
                stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Callback.class),"msg",ASMUtil.getMethodDescriptor(Void.TYPE,int.class,String.class,String.class,String.class)));
                mn.instructions.insertBefore(mn.instructions.getFirst().getNext().getNext().getNext(),stack);
            }
        }

       /* for(MethodNode mn : (List<MethodNode>) classes.get("h").methods) {
            if(mn.name.equals("e")) {
                for(AbstractInsnNode ain : mn.instructions.toArray()) {
                    if(ain.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                        MethodInsnNode min = (MethodInsnNode) ain;
                        if(min.owner.equals("aq") && min.name.equals("l")) {
                            InsnList stack = new InsnList();
                            stack.add(new InsnNode(Opcodes.DUP));
                            stack.add(new LdcInsnNode(90));
                            stack.add(new LdcInsnNode("zaka_edd"));
                            stack.add(new LdcInsnNode("Whip 1m zaka_edd"));
                            stack.add(new InsnNode(Opcodes.ACONST_NULL));
                            stack.add(new LdcInsnNode(1389061842));
                            stack.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,"ae","l",ASMUtil.getMethodDescriptor(Void.TYPE,int.class,String.class,String.class,String.class,int.class)));
                            mn.instructions.insert(ain,stack);
                        }
                    }
                }
            }
            //ae.l
        }
*/


        /**
         *
         * Message.setVisible(true/false);
         * Message.setType();  if(alreadySet) throw
         * Message.setSender();
         * Message.setMessage();
         * Message.setChannel();
         *
         * MessageFilter {
         * boolean accept(RSMessage msg);
         * }
         *
         * RootFilter implements MessageFilter {
         *
         *  if(!msg.isVisible()) return false;
         *  if(!chkOtherFilters()) return false;
         *
         *
         *
         */



       for(MethodNode mn : (List<MethodNode>) classes.get("eg").methods) {
            if(mn.name.equals("q") && mn.desc.endsWith("Lae;")) {
                for(AbstractInsnNode ain : mn.instructions.toArray()) {
                    if(ain.getOpcode() == Opcodes.ARETURN) {
                        InsnList stack = new InsnList();
                        stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC,Type.getInternalName(Callback.class),"ok",ASMUtil.getMethodDescriptor(Object.class,Object.class)));
                        stack.add(new TypeInsnNode(Opcodes.CHECKCAST,"ae"));
                        /*stack.add(new InsnNode(Opcodes.DUP));
                        stack.add(new LdcInsnNode(""));
                        stack.add(new FieldInsnNode(Opcodes.PUTFIELD,"ae","o",Type.getDescriptor(String.class)));*/
                        mn.instructions.insertBefore(ain,stack);
                    }
                }
              /*  InsnList stack = new InsnList();
                stack.add(new TypeInsnNode(Opcodes.NEW, "ae"));
                stack.add(new InsnNode(Opcodes.DUP));
                stack.add(new LdcInsnNode(90));
                stack.add(new LdcInsnNode("Jamie"));
                stack.add(new LdcInsnNode("MESSSAGE"));
                stack.add(new InsnNode(Opcodes.ACONST_NULL));
                stack.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,"ae","<init>", ASMUtil.getMethodDescriptor(Void.TYPE,int.class,String.class,String.class,String.class)));
                stack.add(new InsnNode(Opcodes.ARETURN));
                mn.instructions.insertBefore(mn.instructions.getFirst(),stack);*/
            }
        }

        compile(default_def_loader);

        return classes_final;

    }


    Map<String,byte[]> inflate() throws IOException {
        JarFile jar = new JarFile(new File("")/*cfg.getClientLoc()*/);
        Enumeration<JarEntry> jarEntries = jar.entries();
        Map<String,byte[]> def_map = new HashMap<String,byte[]>(jar.size());
        while (jarEntries.hasMoreElements()) {
            JarEntry entry = jarEntries.nextElement();
            String entryName = entry.getName();
            if (!entryName.endsWith(".class")) continue;
            ClassNode node = new ClassNode();
            InputStream in = jar.getInputStream(entry);
            ClassReader reader = new ClassReader(in);
            reader.accept(node, ClassReader.SKIP_DEBUG);
            classes.put(node.name, node);
            def_map.put(node.name,reader.b);
            in.close();
        }
        jar.close();
        return def_map;
    }


    private void compile(ClassLoader default_loader) {
        for(Map.Entry<String,ClassNode> clazz : classes.entrySet()) {
            try {
                String name = clazz.getKey();
                ClassNode node = clazz.getValue();
                ClassWriter writer = new ClassWriterFix( ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES, default_loader );
                node.accept(writer);
                byte[] def = writer.toByteArray();
                classes_final.put(name.replace("/", "."),def);
            } catch (Throwable e) {
                throw new Error("Failed to transform " + clazz.getKey(),e);
            }
        }
    }

}
