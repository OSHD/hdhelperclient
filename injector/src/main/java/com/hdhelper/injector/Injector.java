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
import com.hdhelper.injector.util.ClassWriterFix;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.logging.Logger;
import java.util.zip.ZipFile;


public final class Injector extends AbstractInjector {

    private static final Logger LOG = Logger.getLogger(Injector.class.getName());

    private HashMap<String, byte[]> classes_final = new HashMap<String,byte[]>();
    private HashMap<String, ClassNode> classes = new HashMap<String,ClassNode>();

    public Injector(InjectorConfig cfg) {
        super(cfg);
    }

    @Override
    public JarFile inject(JarFile target) throws Exception {
        cfg.verify();
        byte[] config = getConfig(target);
        Map<String,byte[]> classes = inject0(target);
        System.out.println("Verifying...");
        verifyBytecode(classes);
        System.out.println("Saving...");
        save(classes, config, cfg.getOutputFile());
        return new JarFile(cfg.getOutputFile(), true, ZipFile.OPEN_READ);
    }

    // Verify the bytecode by having the VM load the classes (and in term verify the class).
    // This assumed class-verification is not disabled within this virtual machine.
    // TODO verify this VM verifies class defs.
    private static void verifyBytecode(Map<String, byte[]> classDefs) {
        ByteClassLoader loader = new ByteClassLoader(classDefs);
        loader.loadAll();
        loader.destroy();
    }

    private static void save(Map<String,byte[]> classes, byte[] config, File dest) throws IOException {

        JarOutputStream out = null;

        try {

            FileOutputStream fos = new FileOutputStream(dest);
            out = new JarOutputStream(fos);
            for (Map.Entry<String,byte[]> cn : classes.entrySet()) {
                JarEntry entry = new JarEntry(cn.getKey().replace( '.', '/' ) + ".class");
                out.putNextEntry(entry);
                out.write(cn.getValue());
                out.closeEntry();
            }

            // Write the config:
            JarEntry cfg = new JarEntry("META-INF/config.ws");
            out.putNextEntry(cfg);
            out.write(config);
            out.closeEntry();

        } finally {
            if(out != null) out.close();
        }
    }

    @Override
    public boolean verifyExisting(JarFile injected) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void destroy() {
        classes.clear();
        classes_final.clear();
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


    private Map<String,byte[]> inject0(JarFile file) throws Exception {

        Map<String,byte[]> defs = loadClasses(file);
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

    /*  for(MethodNode mn : (List<MethodNode>) classes.get("ae").methods) {
            if(mn.name.equals("<init>")) {
                InsnList stack = new InsnList();
                stack.add(new VarInsnNode(Opcodes.ILOAD, 1));
                stack.add(new VarInsnNode(Opcodes.ALOAD, 2));
                stack.add(new VarInsnNode(Opcodes.ALOAD, 3));
                stack.add(new VarInsnNode(Opcodes.ALOAD, 4));
                stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Callback.class),"msg",ASMUtil.getMethodDescriptor(Void.TYPE,int.class,String.class,String.class,String.class)));
                mn.instructions.insertBefore(mn.instructions.getFirst().getNext().getNext().getNext(),stack);
            }
        }*/

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



     /*  for(MethodNode mn : (List<MethodNode>) classes.get("eg").methods) {
            if(mn.name.equals("q") && mn.desc.endsWith("Lae;")) {
                for(AbstractInsnNode ain : mn.instructions.toArray()) {
                    if(ain.getOpcode() == Opcodes.ARETURN) {
                        InsnList stack = new InsnList();
                        stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC,Type.getInternalName(Callback.class),"ok",ASMUtil.getMethodDescriptor(Object.class,Object.class)));
                        stack.add(new TypeInsnNode(Opcodes.CHECKCAST,"ae"));
                        *//*stack.add(new InsnNode(Opcodes.DUP));
                        stack.add(new LdcInsnNode(""));
                        stack.add(new FieldInsnNode(Opcodes.PUTFIELD,"ae","o",Type.getDescriptor(String.class)));*//*
                        mn.instructions.insertBefore(ain,stack);
                    }
                }
              *//*  InsnList stack = new InsnList();
                stack.add(new TypeInsnNode(Opcodes.NEW, "ae"));
                stack.add(new InsnNode(Opcodes.DUP));
                stack.add(new LdcInsnNode(90));
                stack.add(new LdcInsnNode("Jamie"));
                stack.add(new LdcInsnNode("MESSSAGE"));
                stack.add(new InsnNode(Opcodes.ACONST_NULL));
                stack.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,"ae","<init>", ASMUtil.getMethodDescriptor(Void.TYPE,int.class,String.class,String.class,String.class)));
                stack.add(new InsnNode(Opcodes.ARETURN));
                mn.instructions.insertBefore(mn.instructions.getFirst(),stack);*//*
            }
        }*/

        System.out.println("Compiling...");
        compile(default_def_loader);

        return classes_final;

    }

    // Gets the config used to define the gamepack, which must be retained
    private static byte[] getConfig(JarFile file) {
        JarEntry cfg_entry = file.getJarEntry("META-INF/config.ws"); // We store it within the default package
        if(cfg_entry == null) return null;
        try {
            // Get the config data:
            InputStream stream = file.getInputStream(cfg_entry);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int r;
            while ((r = stream.read(buffer)) > 0) {
                bos.write(buffer, 0, r);
            }
            stream.close();
            return bos.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    Map<String,byte[]> loadClasses(JarFile jar) throws IOException {
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
    
    public static void main(String[] args) {
    	System.out.println("HI");
    }

}
