package com.hdhelper.injector.bs.scripts.cache;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSVarpbit;
import com.hdhelper.injector.bs.scripts.collection.DualNode;

@ByteScript(name = "Varpbit")
public class Varpbit extends DualNode implements RSVarpbit {

    @BField int varp;
    @BField int lowBit;
    @BField int highBit;

    @Override
    public int getVarp() {
        return varp;
    }

    @Override
    public int getLowBit() {
        return lowBit;
    }

    @Override
    public int getHighBit() {
        return highBit;
    }

    @Override
    public String toString() {
        return "Varpbit:(varp=" + varp + "[" + lowBit + "," + highBit + "])";
    }

}
