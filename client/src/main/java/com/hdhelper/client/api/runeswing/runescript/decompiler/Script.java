package com.hdhelper.client.api.runeswing.runescript.decompiler;


import com.hdhelper.agent.services.RSClient;
import com.hdhelper.agent.services.RSRuneScript;
import com.hdhelper.client.Client;

public class Script {


    public int id;

    public int num_int_params;
    public int num_string_params;
    //Frame specs
    public int max_local_strings;
    public int max_local_ints;

    public int[] int_operands;
    public int[] opcodes;
    public String[] string_operands;

    Script() {
    }

    static Script get(int id) {
        try {

            RSClient client = Client.get();
            RSRuneScript script = client.getRuneScript(id);
            if(script == null) return null;
            Script rs = new Script();

            rs.id = id;
            rs.num_int_params = script.getIntArgCount();
            rs.num_string_params = script.getStrArgCount();
            rs.max_local_ints = script.getIntLocalCount();
            rs.max_local_strings = script.getStrLocalCount();
            rs.int_operands = script.getIntOperands();
            rs.string_operands = script.getStrOperands();
            rs.opcodes = script.getOpcodes();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}