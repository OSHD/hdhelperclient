package com.hdhelper.agent.mod;

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.util.Map;

public abstract class InjectionModule implements Opcodes {

    public abstract void inject(Map<String,ClassNode> classes);

    public static InjectionModule[] getModules() {
        return new InjectionModule[]{
            new ClientMod(),
                new CharacterMod(),
                new DequeMod(),
                new ItemContainerMod(),
                new ItemDefintionMod(),
                new NodeMod(),
                new NodeTableMod(),
                new NpcDefintionMod(),
                new NpcMod(),
                new ObjectDefintionMod(),
                new PlayerMod(),
                new RenderableMod(),
                new GroundItemMod(),
             //   new RenderMod()
        };
    }

}
