package com.hdhelper.client.api.runeswing.runescript.decompiler;

import java.util.HashMap;

import static com.hdhelper.client.api.runeswing.runescript.RSOpcodes.*;


public class StackHound {

    static boolean ref2;

    public static String getArgDesc(Script script) {

        final int num_ints = script.num_int_params;
        final int num_strings = script.num_string_params;
        if(num_ints == 0 && num_strings == 0) return "";

/*
        if(script.id == 228) {
            return "ISI";
        }

        if(script.id == 31) {
            return "IIIIIIII";
        }

        if(script.id == 72) {
            return "III";
        }
*/

        //IIIIIIIIII


        //IIIIIIII
        //IIIIIIIIII
        int len = num_strings + num_ints;

        StringBuilder desc = new StringBuilder(len);

        for(int sa = 0 ; sa < num_strings; sa++) {
            desc.append('S');
        }

        for(int ia = 0; ia < num_ints; ia++) {
            desc.append('I');
        }

        return desc.toString();

    }

    static class JumpNode {
        final int index;
        final boolean jump;

        JumpNode(int index, boolean jump) {
            this.index = index;
            this.jump = jump;
        }

        JumpNode next;
    }


    public static HashMap<Integer,String> DESC = new HashMap<Integer,String>();

    public static FrameInfo simulate(Script script) {
       return simulate(script,0);
    }

    class ObjectSpec {
        HashMap<Integer, FieldSpec> specs;
    }

    public static class FieldSpec {
        int obj;
        int var;
        boolean str;
        String name;
        String owner;

        public String toString() {
            return obj + "[" + owner + "#" + name + "@" + (str ? 'S' : 'I') + "]:" + var;
        }
    }

    public static class FrameInfo {
        public String ret_stack;
        HashMap<Integer,FieldSpec> objs;
        int num_objects;
    }


    public static class FrameSpec {
        String return_desc;
        String arg_desc;
        boolean is_object;
    }

    public static FrameInfo simulate(Script script, int depth) {
        //Simulates the operand stack to see whats left within the frame,
        // and thus is pushed onto the invokers frame

        String stack = "";

        final int[] opcodes     = script.opcodes;
        final int[] int_pool    = script.int_operands;
        final String[] str_pool = script.string_operands;

       /* System.out.println(Arrays.toString(opcodes));
        System.out.println(Arrays.toString(int_pool));
        System.out.println(Arrays.toString(str_pool));*/


        boolean loops = false;

        for(int op = 0; op < opcodes.length; op++) {
            switch (opcodes[op]) {
                case GOTO:
                case IF_ICMPNE:
                case IF_ICMPEQ:
                case IF_ICMPLT:
                case IF_ICMPGT:
                case IF_ICMPLE:
                case IF_ICMPGE: {
                    int jump = int_pool[op];
                    if (jump < 0) {
                        loops = true;
                        break;
                    }
                }
            }
        }

        HashMap<Integer,Boolean> map = null;

        if(loops) {
            System.out.println("Script " + script.id + " loops! Mapping...");
            map = new StackExplorer().compute(0,script.opcodes,script.int_operands);
            System.out.println("Map: " + map);
            if(map == null)
                throw new Error("Failed to map " + script.id);
        }

        HashMap<Integer,FieldSpec> specs = new HashMap<Integer,FieldSpec>();
        int num_objects = 0;

        for(int swi = 0; swi < opcodes.length; swi++) {

            int opcode = opcodes[swi];

            for(int i = 0; i < depth; i++) {
                System.out.print("\t");
            }

            System.out.print(swi + ":" +  Converter.op2String(opcode) + "(" + opcode + "):");

            if(opcode == RETURN) {

                //  stack = stack.replace("S", STRING_DESC);
               System.out.println("Done(" + script.id + "): '" + stack + "'");

                FrameInfo info = new FrameInfo();
                info.num_objects = num_objects;
                info.objs = specs;
                info.ret_stack = stack;


                return info;
            }

            if(opcode == INVOKE) {

                final int method_id = int_pool[swi];
                Script method = Script.get(method_id);
                String params = getArgDesc(method);
                String old = new String(params);

                System.out.println(method_id + " ==> " + old);

                params = alignDesc(stack, params);



                ref2 = false;
                String return0 = DESC.get(method_id);
                if(return0 == null) {
                    System.out.println(method_id + "...");
                    return0 = simulate(method,depth+1).ret_stack;
                    DESC.put(method_id, return0);
                }
                ref2 = false; //since its static...

                for(int i = 0; i < depth; i++) {
                    System.out.print("\t");
                }

                if(return0.length() == 0) return0 = "V";
                System.out.println("Invoke -> Method" + method_id + "(" + params + ")" + return0);

                for(int i = 0; i < depth; i++) {
                    System.out.print("\t");
                }

              //  System.out.println("Return Desc of " + method_id + " == '" + return0 + "'");
                stack = pop(stack, "(" + params + ")" + return0);
/*

                if(return0.length() > 1) {
                    int str_idx = 0;
                    int int_idx = 0;
                    boolean verified = false;
                    int obj_id = -1;
                    for(int next = swi + 1, c = 0; next < (swi + return0.length() + 1); next++, c++) {

                        int op_code = opcodes[next];
                        int var_pos = int_pool[next];

                  //      FieldSpec spec = specs.get(var_pos);
                        if(!specs.containsKey(var_pos)) {
                            if(verified) throw new Error("Object Fragment");
                            if(obj_id == -1)
                                obj_id = num_objects++; // Only visited once
                        } else {
                            verified = true;
                            continue;
                        }

                        if(op_code == RSOpcodes.ISTORE) {
                            if(return0.charAt(c) == 'I') {
                                FieldSpec spec = new FieldSpec();
                                spec.obj = obj_id;
                                spec.str = false;
                                spec.var = var_pos;
                                spec.name = "int" + (int_idx++);
                                spec.owner = "Method" + method_id;
                                specs.put(var_pos,spec);
                                continue;
                            } else throw new Error("Bad store");
                        }

                        if(op_code == RSOpcodes.SSTORE) {
                            if(return0.charAt(c) == 'S') {
                                FieldSpec spec = new FieldSpec();
                                spec.obj = obj_id;
                                spec.str = true;
                                spec.var = var_pos;
                                spec.name = "string" + (str_idx++);
                                spec.owner = "Method" + method_id;
                                specs.put(var_pos,spec);
                                continue;
                            } else throw new Error("Bad store");
                        }

                        if(op_code == RSOpcodes.IPOP
                       || op_code == RSOpcodes.SPOP) break; // Method was just invoked, not stored locally

                        throw new Error("Bad object handle");
                    }

                    System.out.println("Object Spec Found: " + specs);
                }


*/

                continue;

            }

            ref2 = opcode >= 2000 && opcode < 3100;
            if(ref2) opcode -= 1000;

            //   System.out.println(op2String(opcode) + " ~ " + ref2);

            switch (opcode) {
                case ILDC: { stack = pop(stack,"()I"); continue; }
                case GETVAR: { stack = pop(stack,"()I"); continue; }
                case PUTVAR: { stack = pop(stack,"(I)V"); continue; }
                case SLDC: { stack = pop(stack,"()S"); continue; }
                case GETVARPB: { stack = pop(stack,"()I"); continue; }
                case PUTVARPB: { stack = pop(stack,"(I)V"); continue; }
                case ILOAD: { stack = pop(stack,"()I"); continue; }
                case ISTORE: { stack = pop(stack,"(I)V"); continue; }
                case SLOAD: { stack = pop(stack,"()S"); continue; }
                case SSTORE: { stack = pop(stack,"(S)V"); continue; }
                case SAPPX: { // Dynamic
                    final int num_strings = int_pool[swi];
                    String pop = "";
                    for(int i = 0; i < num_strings; i++) {
                        pop += 'S';
                    }
                    stack = pop(stack,"(" + pop + ")S"); continue;
                }
                case IPOP: { stack = pop(stack,"(I)V"); continue; }
                case SPOP: { stack = pop(stack,"(S)V"); continue; }
                case GETSTATICI: { stack = pop(stack,"()I"); continue; }
                case PUTSTATICI: { stack = pop(stack,"(I)V"); continue; }
                case IACLR: { stack = pop(stack,"(I)V"); continue; }
                case IALOAD: { stack = pop(stack,"(I)I"); continue; }
                case IASTORE: { stack = pop(stack,"(II)V"); continue; }
                case GETSTATICS: { stack = pop(stack,"()S"); continue; }
                case PUTSTATICS: { stack = pop(stack,"(S)V"); continue; }
                //----------------------------------------------------
                case NEWW: { stack = pop(stack,"(III)V"); continue; }
                case DELW: { stack = pop(stack,"()V"); continue; }
                case DELAC: { stack = pop(stack,"(I)V"); continue; }
                case GET_WIDGET: { stack = pop(stack,"(II)I"); continue; }
                //----------------------------------------------------
                case PUT_WPOS: { stack = pop(stack,"(II)V"); continue; }
                case PUT_WDIM: { stack = pop(stack,"(II)V"); continue; }
                case PUT_WVIS: { stack = pop(stack,"(I)V"); continue; }
                //----------------------------------------------------
                case PUT_WINSETS: { stack = pop(stack,"(II)V"); continue; }
                case PUT_WTXTCLR: { stack = pop(stack,"(I)V"); continue; }
                case PUT_1102: { stack = pop(stack,"(I)V"); continue; }
                case PUT_WALPHA: { stack = pop(stack,"(I)V"); continue; }
                case PUT_1104: { stack = pop(stack,"(I)V"); continue; }
                case PUT_WTEXTURE: { stack = pop(stack,"(I)V"); continue; }
                case PUT_WSPRIROT: { stack = pop(stack,"(I)V"); continue; }
                case PUT_1107: { stack = pop(stack,"(I)V"); continue; }
                case PUT_WMODEL1: { stack = pop(stack,"(I)V"); continue; }
                case PUT_WMODEL: { stack = pop(stack,"(IIIIII)V"); continue; }
                case PUT_1110: { stack = pop(stack,"(I)V"); continue; }
                case PUT_1111: { stack = pop(stack,"(I)V"); continue; }
                case PUT_WTXT: { stack = pop(stack,"(S)V"); continue; }
                case PUT_WFONT: { stack = pop(stack,"(I)V"); continue; }
                case PUT_1114: { stack = pop(stack,"(III)V"); continue; }
                case PUT_1115: { stack = pop(stack,"(I)V"); continue; }
                case PUT_WBORDERTHK: { stack = pop(stack,"(I)V"); continue; }
                case PUT_WSHADOWCLR: { stack = pop(stack,"(I)V"); continue; }
                case PUT_WVIRFLP: { stack = pop(stack,"(I)V"); continue; }
                case PUT_WHORFLP: { stack = pop(stack,"(I)V"); continue; }
                case PUT_WSCROLDIM: { stack = pop(stack,"(II)V"); continue; }
                case PUT_PROCW: { stack = pop(stack,"()V"); continue; }
                //----------------------------------------------------------
                case PUT_WITEM: { stack = pop(stack,"(II)V"); continue; }
                case PUT_MODEL2: { stack = pop(stack,"(I)V"); continue; }
                case PUT_MODEL3: { stack = pop(stack,"()V"); continue; }
                case PUT_MOD1205: { stack = pop(stack,"(II)V"); continue; }
                //-----------------------------------------------------------
                case PUT_WACT: { stack = pop(stack,"(IS)V"); continue; }
                case PUT_WPARENT: { stack = pop(stack,"(I)V"); continue; }
                case PUT_1302: { stack = pop(stack,"(I)V"); continue; }
                case PUT_1303: { stack = pop(stack,"(I)V"); continue; }
                case PUT_1304: { stack = pop(stack,"(I)V"); continue; }
                case PUT_1305: { stack = pop(stack,"(S)V"); continue; }
                case PUT_WTITL: { stack = pop(stack,"(S)V"); continue; }
                case DEL_WACTS: { stack = pop(stack,"()V"); continue; }
                //-----------------------------------------------------------
                case GET_WX:
                case GET_WY:
                case GET_WW:
                case GET_WH:
                case IS_WVIS:
                case GET_WPAR:
                { stack = pop(stack,"()I"); continue; }
                //------------------------------------------------------------
                case GET_WINSETX: { stack = pop(stack,"()I"); continue; }
                case GET_WINSETY: { stack = pop(stack,"()I"); continue; }
                case GET_WTXT: { stack = pop(stack,"()S"); continue; }
                case GET_WSCROLW:
                case GET_WSCROLH:
                case GET_WZOOM:
                case GET_WROTX:
                case GET_WROTY:
                case GET_WROTZ:
                { stack = pop(stack,"()I"); continue; }
                //------------------------------------------------------------
                case GET_WITEM:
                case GET_WIAMT:
                case GET_WIDX:
                { stack = pop(stack,"()I"); continue; }
                //------------------------------------------------------------
                case GET_WSPELTRGT: { stack = pop(stack,"()I"); continue; }
                case GET_WACTION: { stack = pop(stack,"(I)S"); continue; }
                case GET_WNAME: { stack = pop(stack,"()S"); continue; }
                //-------------------------------------------------------------
                case SEND_3100: { stack = pop(stack,"(S)V"); continue; }
                case SET_ANIM:  { stack = pop(stack,"(II)V"); continue; }
                case CLOSE_MAJOR:  { stack = pop(stack,"()V"); continue; }
                case SEND_3104:  { stack = pop(stack,"(S)V"); continue; }
                case SEND_3105:  { stack = pop(stack,"(S)V"); continue; }
                case SEND_3106:  { stack = pop(stack,"(S)V"); continue; }
                case SEND_3107:  { stack = pop(stack,"(IS)V"); continue; }
                case SEND_3108:  { stack = pop(stack,"(III)V"); continue; }
                case SEND_3109:  { stack = pop(stack,"(I)V"); continue; }
                case PUTSTATIC_3110:  { stack = pop(stack,"(I)V"); continue; }
                case IS_3111:  { stack = pop(stack,"()I"); continue; }
                case PUT_3112:  { stack = pop(stack,"(I)V"); continue; }
                case OPEN_URL:  { stack = pop(stack,"(SI)V"); continue; }
                //-------------------------------------------------------------
                case SET_AUDIO3200:  { stack = pop(stack,"(III)V"); continue; }
                case SET_AUDIO3201:  { stack = pop(stack,"(I)V"); continue; }
                case SET_AUDIO3202:  { stack = pop(stack,"(II)V"); continue; }
                //-------------------------------------------------------------
                case GET_CCYCLE:  { stack = pop(stack,"()I"); continue; }
                case GET_CITEMID: { stack = pop(stack,"(II)I"); continue; }
                case GET_CITEMAMT: { stack = pop(stack,"(II)I"); continue; }
                case GET_CAMT: { stack = pop(stack,"(II)I"); continue; }
                case REF2VARID: { stack = pop(stack,"(I)I"); continue; }
                case GET_CLVL: { stack = pop(stack,"(I)I"); continue; }
                case GET_RLVL: { stack = pop(stack,"(I)I"); continue; }
                case GET_XP: { stack = pop(stack,"(I)I"); continue; }
                case GET_LOC: { stack = pop(stack,"()I"); continue; }
                case LOC2X: { stack = pop(stack,"(I)I"); continue; }
                case LOC2Y: { stack = pop(stack,"(I)I"); continue; }
                case LOC2Z: { stack = pop(stack,"(I)I"); continue; }
                case IS_MEMWORLD: { stack = pop(stack,"()I"); continue; }
                case GET_CITEMID2: { stack = pop(stack,"(II)I"); continue; }
                case GET_CITEMAMT2: { stack = pop(stack,"(II)I"); continue; }
                case GET_CAMT2: { stack = pop(stack,"(II)I"); continue; }
                case GET_RIGHTS:
                case GET_3317:
                case GET_WORLD:
                case GET_ENERGY:
                case GET_WEIGHT:
                case IS_MALE:
                case GET_WORLDINFO:
                case IS_EXCHANGE_OFFER_SLOT_USED:
                case GET_EXCHANGE_OFFER_ITEM_ID:
                case GET_EXCHANGE_OFFER_ITEM_QUANTITY:
                case GET_EXCHANGE_OFFER_ITEM_PRICE:
                case GET_EXCHANGE_OFFER_TRANSFERRED:
                case IS_EXCHANGE_OFFER_PURCHASED:
                { stack = pop(stack,"()I"); continue; }
                //--------------------------------------------------------------
                case GET_SCACHEVAL:  { stack = pop(stack,"(II)S"); continue; }
                case GET_GCACHEVAL:  { stack = pop(stack,"(IIII)?"); continue; } //TODO generic wildcard return
                //--------------------------------------------------------------
                case GET_NUMFRIENDS:   { stack = pop(stack,"()I"); continue; }
                case GET_FRIENDNAME:   { stack = pop(stack,"(I)SS"); continue; } //TODO Object return
                case GET_FRIENDWORLD:   { stack = pop(stack,"(I)I"); continue; }
                case GET_FRIENDIDX:   { stack = pop(stack,"(I)I"); continue; }
                case SEND_3604:   { stack = pop(stack,"(SI)V"); continue; }
                case ADD_FRIEND:   { stack = pop(stack,"(I)V"); continue; }
                case REMOVE_FRIEND:   { stack = pop(stack,"(S)V"); continue; }
                case ADD_IGNORED:   { stack = pop(stack,"(S)V"); continue; }
                case REMOVE_IGNORED:   { stack = pop(stack,"(S)V"); continue; }
                case HAS_PREVNAMEF:   { stack = pop(stack,"(S)I"); continue; }
                case GET_CLANOWNER:   { stack = pop(stack,"()S"); continue; }
                case GET_CLANSIZE:   { stack = pop(stack,"()I"); continue; }
                case GET_CMEMNAME:   { stack = pop(stack,"(I)S"); continue; }
                case GET_CMEMWORLD:   { stack = pop(stack,"(I)I"); continue; }
                case GET_CMEMRANK:   { stack = pop(stack,"(I)I"); continue; }
                case GET_3616:   { stack = pop(stack,"()I"); continue; }
                case SEND_3617:   { stack = pop(stack,"(S)V"); continue; }
                case GET_LOCALCRANK:   { stack = pop(stack,"()I"); continue; }
                case SEND_3619:   { stack = pop(stack,"(S)V"); continue; }
                case SEND_3620:   { stack = pop(stack,"()V"); continue; }
                case GET_NUMIGNORED:   { stack = pop(stack,"()I"); continue; }
                case GET_IGNOREDNAME:   { stack = pop(stack,"(I)SS"); continue; } //TODO Object return
                case HAS_PREVNAMEI:   { stack = pop(stack,"(S)I"); continue; }
                case IS_IGNOREME:   { stack = pop(stack,"(I)I"); continue; }
                case GET_CLANCHAN:   { stack = pop(stack,"()S"); continue; }
                //----------------------------------------------------------------
                case IADD:  { stack = pop(stack,"(II)I"); continue; }
                case ISUB:  { stack = pop(stack,"(II)I"); continue; }
                case IMUL:  { stack = pop(stack,"(II)I"); continue; }
                case IDIV:  { stack = pop(stack,"(II)I"); continue; }
                case IRNDF:  { stack = pop(stack,"(I)I"); continue; }
                case IRNDC:  { stack = pop(stack,"(I)I"); continue; }
                case U4006:  { stack = pop(stack,"(IIIII)I"); continue; }
                case U4007:  { stack = pop(stack,"(II)I"); continue; }
                case SETBIT:  { stack = pop(stack,"(II)I"); continue; }
                case CLRBIT:  { stack = pop(stack,"(II)I"); continue; }
                case TESTBIT:  { stack = pop(stack,"(II)I"); continue; }
                case IREM:  { stack = pop(stack,"(II)I"); continue; }
                case POW:  { stack = pop(stack,"(II)I"); continue; }
                case NROOT:  { stack = pop(stack,"(II)I"); continue; }
                case IAND:  { stack = pop(stack,"(II)I"); continue; }
                case IOR:  { stack = pop(stack,"(II)I"); continue; }
                //---------------------------------------------------------------
                case APPI:  { stack = pop(stack,"(SI)S"); continue; }
                case APPS:  { stack = pop(stack,"(SS)S"); continue; }
                case APPIR:  { stack = pop(stack,"(SI)S"); continue; }
                case LCS:  { stack = pop(stack,"(S)S"); continue; }
                case TIME2DATE:  { stack = pop(stack,"(I)S"); continue; }
                case ST_GEN:  { stack = pop(stack,"(SS)S"); continue; }
                case I2S:  { stack = pop(stack,"(I)S"); continue; }
                case CMPS:  { stack = pop(stack,"(SS)I"); continue; }
                case U4108:  { stack = pop(stack,"(SII)I"); continue; }
                case U4109:  { stack = pop(stack,"(SII)I"); continue; }
                case ST_C1:  { stack = pop(stack,"(SSI)S"); continue; }
                case TLC:  { stack = pop(stack,"(S)S"); continue; }
                case APPC:  { stack = pop(stack,"(SI)S"); continue; }
                case IS_4113:  { stack = pop(stack,"(I)I"); continue; }
                case IS_LWC:  { stack = pop(stack,"(I)I"); continue; }
                case IS_ALP:  { stack = pop(stack,"(I)I"); continue; }
                case IS_DGT:  { stack = pop(stack,"(I)I"); continue; }
                case SLENGTH:  { stack = pop(stack,"(S)I"); continue; }
                case SUBS:  { stack = pop(stack,"(SII)S"); continue; }
                case DEL_LC:  { stack = pop(stack,"(S)S"); continue; }
                case IDXOFC:  { stack = pop(stack,"(SI)I"); continue; }
                case IDXOFS:  { stack = pop(stack,"(SSI)I"); continue; }
                //---------------------------------------------------------------------
                case GET_INAME:  { stack = pop(stack,"(I)S"); continue; }
                case GET_ITACT:  { stack = pop(stack,"(II)S"); continue; }
                case GET_IGACT:  { stack = pop(stack,"(II)S"); continue; }
                case GET_IVAL:  { stack = pop(stack,"(I)I"); continue; }
                case IS_ISTKBL:  { stack = pop(stack,"(I)I"); continue; }
                case NOTE2ITEM:  { stack = pop(stack,"(I)I"); continue; }
                case ITEM2NOTE:  { stack = pop(stack,"(I)I"); continue; }
                case IS_MEMBERS:  { stack = pop(stack,"(I)I"); continue; }
                //-----------------------------------------------------------------------
                case GET_5000:  { stack = pop(stack,"()I"); continue; }
                case SEND_5001:  { stack = pop(stack,"(III)V"); continue; }
                case SEND_5002:  { stack = pop(stack,"(SII)V"); continue; }
                case GETMSGM:  { stack = pop(stack,"(II)IISSS"); continue; } //TODO Object return
                case GETMSGW:  { stack = pop(stack,"(I)IISSS"); continue; } //TODO object return
                case GET_5005:  { stack = pop(stack,"()I"); continue; }
                case SEND_5008:  { stack = pop(stack,"(SI)V"); continue; }
                case SEND_5009:  { stack = pop(stack,"(SS)V"); continue; }
                case LOCALNAME:  { stack = pop(stack,"()S"); continue; }
                case GET_5016:  { stack = pop(stack,"()I"); continue; }
                case NUM_MSGS:  { stack = pop(stack,"(I)I"); continue; }
                case PREV_MSG:  { stack = pop(stack,"(I)I"); continue; }
                case NEXT_MSG:  { stack = pop(stack,"(I)I"); continue; }
                case PROC_CMD:  { stack = pop(stack,"(S)V"); continue; }
                case PUT_5021:  { stack = pop(stack,"(S)V"); continue; }
                case GET_5022:  { stack = pop(stack,"()S"); continue; }
                //-----------------------------------------------------------------------
                case PUT_5504: { stack = pop(stack,"(II)V"); continue; }
                //-----------------------------------------------------------------------
                case PUTES_MOUSE_PRESSED:
                case PUTES_MOUSE_DRAGGED_OVER:
                case PUTES_MOUSE_RELEASED:
                case PUTES_MOUSE_ENTERED:
                case PUTES_MOUSE_EXITED:
                case PUTES_1405:
                case PUTES_1406:
                case PUTES_1407:
                case PUTES_1408:
                case PUTES_1409:
                case PUTES_1410:
                case PUTES_MOUSE_DRAGGED:
                case PUTES_MOUSE_MOVED:
                case PUTES_1413:
                case PUTES_1414:
                case PUTES_1415:
                case PUTES_1416:
                case PUTES_1417:
                case PUTES_1418:
                case PUTES_1419:
                case PUTES_1420:
                case PUTES_1421:
                case PUTES_1422:
                case PUTES_1423:
                case PUTES_1424: {

                    int argDesc = ref2 ? swi - 2 : swi - 1;
                    String desc = str_pool[argDesc];
                    if (desc == null)
                        throw new Error();

                    StringBuilder pop = new StringBuilder(desc.length() + 2);

                    pop.append('S');

                    System.out.println(desc);
                    if (desc.length() > 0) {
                        if (desc.charAt(desc.length() - 1) == 'Y') {
                            pop.append('I');

                            int num_triggers = int_pool[ref2 ? swi - 2 : swi - 1]; //TODO right entry?
                            System.out.println(int_pool[swi-2] + "," + int_pool[swi-1] + "," + int_pool[swi - 0]);
                            if(num_triggers > 0) {
                                System.out.println("Pushing " + num_triggers + " triggers");
                                while (num_triggers-- > 0) {
                                    pop.append('I');
                                }
                            }
                            desc = desc.substring(0, desc.length() - 1);
                        }
                    }

                    System.out.println(desc + ":" + pop);

                    for (int n150 = desc.length(); n150 >= 1; --n150) {
                        System.out.println(desc.charAt(n150 - 1));
                        if (desc.charAt(n150 - 1) == 's') {
                            pop.append('S');
                        } else {
                            pop.append('I');
                        }
                        System.out.println(pop);
                    }

                    pop.append('I'); //id of the script...
                    pop = pop.reverse();
                    String pop0 = pop.toString();
                   // System.out.println("Popping Args: " + "[" + desc + "] -> " + pop0);
                    for(int i = 0; i < depth; i++) {
                        System.out.print("\t");
                    }

                    stack = pop(stack, "(" + pop0 + ")V");
                    // swi++;

                    continue;
                }

            }

            // Jump instructions

            switch (opcode) {

                case IF_ICMPNE:
                case IF_ICMPEQ:
                case IF_ICMPLT:
                case IF_ICMPGT:
                case IF_ICMPLE:
                case IF_ICMPGE: {
                    stack = pop(stack, "(II)V");
                    int target = swi + int_pool[swi]; // Default: Always fails
                    if (map != null) {
                        Boolean passes = map.get(swi);
                        if (passes == null)
                            throw new Error("Jump Instruction @" + swi + " was not mapped!");
                        System.out.println(swi + " -> " + passes);
                        if (passes) target = swi;
                        else        target = swi + int_pool[swi];
                    }
                    swi = target;
                    continue;
                }

                case GOTO: {
                    swi += int_pool[swi]; // Always true, jump
                    continue;
                }
            }

            throw new Error("Unhandled: " + Converter.op2String(opcode) + ":" + opcode);

        }
        return null;
    }


    static int charCount(String src, int from, char c) {
        int counter = 0;
        char[] chars = src.toCharArray();
        for(int i = from; i < chars.length; i++) {
            if(chars[i] == c || chars[i] == '?') counter++;
        }
        return counter;
    }

    static String alignDesc(String stack, String args) {

        final int head = stack.length() - args.length();

        System.out.println(stack + "," + args);

        int istack = charCount(stack,head,'I');
        int iargs  = charCount(args,0,'I');

        int sstack = charCount(stack,head,'S');
        int sargs  = charCount(args,0,'S');

        if(istack < iargs)
            throw new Error("Stack Alignment Failed: Stack=[" + stack + "] Args=[" + args + "]");

        if(sstack < sargs)
            throw new Error("Stack Alignment Failed: Stack=[" + stack + "] Args=[" + args + "]");

        return stack.substring(head, stack.length());

    }

    static String pop(String stack, String desc) {
        String stack0 = new String(stack);
        String pop = desc.substring(desc.indexOf('(') + 1,desc.indexOf(')'));
        if(ref2) pop =  pop + 'I';
        String push = desc.substring(desc.indexOf(')') + 1, desc.length());

        if(pop.length() > stack.length()) {
            throw new Error("Stack Underflow: [" + stack + "] Args:" + "[" + pop + "]");
        }

        for(int s = (stack.length() - pop.length()), p = 0; p < pop.length() && s < stack.length(); s++, p++) {
            char stack_ = stack.charAt(s);
            char pop_   = pop.charAt(p);
            if(stack_ != pop_ && stack_ != '?' && pop_ != '?') {
                throw new Error("Stack Underflow: [" + stack + "] Args:" + "[" + pop + "]");
            }
        }
       /* if(!stack.endsWith(pop)) {

         //   stack = "";

        }*/
        stack = stack.substring(0, stack.length() - pop.length());
        if(!push.equals("V")) {
            stack += push;
        }
        //  System.out.println(stack);

        System.out.println("[" + stack0 + "] - [" + pop + "]" + " + " + "[" + (push.equals("V") ? "" : push) + "] ==> " + stack);
        return stack;
    }


}
