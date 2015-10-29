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
                new ItemTableMod(),
                new ItemDefintionMod(),
                new NodeMod(),
                new NodeTableMod(),
                new NpcDefintionMod(),
                new NpcMod(),
                new ObjectDefintionMod(),
                new PlayerMod(),
                new EntityMod(),
                new GroundItemMod(),
                new RenderMod(),
                new GraphicsEngineMod(),
                new GraphicsEngineMod(),
                new PlayerConfigMod()

        };
    }

}
