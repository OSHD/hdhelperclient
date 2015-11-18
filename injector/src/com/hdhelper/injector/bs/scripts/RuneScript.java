package com.hdhelper.injector.bs.scripts;

import com.hdhelper.agent.bs.scripts.collection.DualNode;
import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSRuneScript;

@ByteScript(name = "RuneScript")
public class RuneScript extends DualNode implements RSRuneScript {

    @BField int intArgCount;
    @BField int stringArgCount;

    @BField int intStackCount;
    @BField int stringStackCount;

    @BField int[] intOperands;
    @BField String[] stringOperands;

    @BField int[] opcodes;


    @Override
    public int getIntArgCount() {
        return intArgCount;
    }

    @Override
    public int getStrArgCount() {
        return stringArgCount;
    }

    @Override
    public int getIntLocalCount() {
        return intStackCount;
    }

    @Override
    public int getStrLocalCount() {
        return stringStackCount;
    }

    @Override
    public int[] getIntOperands() {
        return intOperands;
    }

    @Override
    public String[] getStrOperands() {
        return stringOperands;
    }

    @Override
    public int[] getOpcodes() {
        return opcodes;
    }
}
