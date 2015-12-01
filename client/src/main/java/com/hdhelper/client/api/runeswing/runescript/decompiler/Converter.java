package com.hdhelper.client.api.runeswing.runescript.decompiler;


import com.hdhelper.client.api.runeswing.runescript.RSOpcodes;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.CheckClassAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author Brainfree
 */
public class Converter implements RSOpcodes {


    // Each script must have >= number of local variables as arguments of the same type


    public static final Class STRING_CLASS = String.class;
    public static final Class INT_CLASS = int.class;

    public static final String STRING_DESC = "L" + Type.getInternalName(STRING_CLASS) + ";";
    public static final String INT_DESC = "I";

    public static final String MULTI_RETURN_DESC = "[L" + Type.getInternalName(Object.class) + ";";
    public static final String WIDGET_NAME = "Widget";
    public static final String WIDGET_DESC = "L" + WIDGET_NAME + ";";
    public static final String MESSAGE_NAME = "Message";
    public static final String MESSAGE_DESC = "L" + MESSAGE_NAME + ";";


    // int args are loaded first, then strings for invoking. meaning
    // that strings are pushed first onto the stack and then ints, thus
    // the description can be defined...

    public static String getArgDesc(Script script) {

        final int num_ints = script.num_int_params;
        final int num_strings = script.num_string_params;
        if(num_ints == 0 && num_strings == 0) return "";

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

    static enum VType {
        INT('I'),
        STRING('S');

        final char flag;

        VType(char flag) {
            this.flag = flag;
        }

        public static VType get(char flag) {
            switch (flag) {
                case 'I': return INT;
                case 'S': return STRING;
            }
            throw new Error();
        }
    }

    static class CSClassDef {

        String name;
        HashSet<CSField> fields = new HashSet<CSField>();

        int num_ints = 0;
        int num_strings = 0;

        String desc = "";

        public void addField(VType type) {
            CSField field = new CSField();
            field.owner = this;
            field.type = type;
            field.field_index = fields.size();
            if(type == VType.INT) {
                field.name = "int" + num_ints;
                desc += 'I';
                num_ints++;
            } else if(type == VType.STRING) {
                field.name = "string" + num_strings;
                desc += 'S';
                num_strings++;
            } else throw new Error();
            fields.add(field);
        }

        boolean isVoid() {
            return fields.size() == 0;
        }

        boolean isSimpleReturn() {
            return fields.size() == 1;
        }

        boolean isObjectReturn() {
            return fields.size() > 1;
        }

    }

    static class CSField {
        CSClassDef owner;
        String name;
        VType type;
        int field_index;
    }

    static enum VarType {
        Arg, // Used to hold the value of a augment for this script
        Normal,  // Used to hold 'Normal' single variables. (String/Int)
        Field // Used to hold a objects field value
    }

    static class CSLocalVar {

        int pointer; // The id of the pointer for this local variable
        VType type; // The description of the local variable
        VarType ref; // the type of local variable
        int obj = -1;

        CSLocalVar(int pointer, VType type, VarType ref) {
            this.pointer = pointer;
            this.type = type;
            this.ref = ref;
        }

        public String toString() {
            return "Var [" + type + "-" + ref + "] @ " + pointer;
        }

    }

    static class CSObject {
        int var; // variable id of this object
        CSClassDef def; // definition of the objects class
        CSLocalVar[] fields; // the local variables that hold the fields of this object

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Object " + var + "(" + def.name + ")[" + fields.length + "]:" + "\n");
            for(CSLocalVar var : fields) {
                builder.append(var.toString());
                builder.append("\n");
            }
            return builder.toString();
        }
    }

    static CSClassDef getClassDef(String clazz) {
        int scriptID = Integer.parseInt(clazz.replace("CS", ""));
        Script script = Script.get(scriptID);

        CSClassDef def = new CSClassDef();
        def.name = clazz;

        String desc = simulate(script);

        switch (scriptID) {
            case 218: {
                desc = "II";
                break;
            }
            case 31:
                desc = "";
                break;

        }

        for(char field : desc.toCharArray()) {
            def.addField(VType.get(field));
        }

        return def;
    }


    static boolean ref2 = false;

   public static String op2String(int op) {
        if(op >= 2000 && op < 3100) {
            op -= 1000;
        }
        for(Field ok : RSOpcodes.class.getDeclaredFields()) {
            ok.setAccessible(true);
            try {
                if(ok.getInt(null) == op) return ok.getName();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String simulate(Script script) {
     //Simulates the operand stack to see whats left within the frame,
     // and thus is pushed onto the invokers frame

        String stack = "";

        final int[] opcodes = script.opcodes;
        final int[] int_pool = script.int_operands;
        final String[] str_pool = script.string_operands;

        System.out.println(Arrays.toString(opcodes));
        System.out.println(Arrays.toString(int_pool));
        System.out.println(Arrays.toString(str_pool));

        for(int swi = 0; swi < opcodes.length; swi++) {

            int opcode = opcodes[swi];

            System.out.print(swi + ":" +  op2String(opcode) + "(" + opcode + "):");

            if(opcode == RETURN) {

              //  stack = stack.replace("S", STRING_DESC);
                System.out.println("Done(" + script.id + "): '" + stack + "'");



                return stack;
            }

            if(opcode == INVOKE) {

                final int method_id = int_pool[swi];
                Script method = Script.get(method_id);
                String params = getArgDesc(method);
                System.out.println("Invoke " + method_id + "(" + params + ")");
                ref2 = false;
                String return0 = StackHound.simulate(method).ret_stack;
                ref2 = false; //since its static...

                System.out.println("Return Desc of " + method_id + " == '" + return0 + "'");
                stack = pop(stack, "(" + params + ")" + return0);
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
                case SAPPX: {
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
                case GET_WSPELTRGT: { stack = pop(stack,"(I)V"); continue; }
                case GET_WACTION: { stack = pop(stack,"(I)S"); continue; }
                case GET_WNAME: { stack = pop(stack,"(I)S"); continue; }
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
                     { stack = pop(stack,"()I"); continue; }
                //--------------------------------------------------------------
                case GET_SCACHEVAL:  { stack = pop(stack,"(II)S"); continue; }
                case GET_GCACHEVAL:  { stack = pop(stack,"(IIII)I"); continue; } //TODO weird return...
                //--------------------------------------------------------------
                case GET_NUMFRIENDS:   { stack = pop(stack,"()I"); continue; }
                case GET_FRIENDNAME:   { stack = pop(stack,"(I)SS"); continue; }
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
                case GET_IGNOREDNAME:   { stack = pop(stack,"(I)SS"); continue; }
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
                case GETMSGM:  {
                    if(true) throw new InternalError("Object Return");
                    stack = pop(stack,"(II)V"); continue; } //TODO object return NOTE: If its a object items pushed are poped into local vars within the next instruction
                case GETMSGW:  {
                    if(true) throw new InternalError("Object Return");
                    stack = pop(stack,"(I)V"); continue; } //TODO object return
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

                    if(true)
                        throw new InternalError("nope");

                    int argDesc = ref2 ? swi - 2 : swi - 1;

                    String desc = str_pool[argDesc];
                    if (desc == null) throw new Error();
                    StringBuilder pop = new StringBuilder(desc.length() + 2);
                    pop.append('S');

                    if (desc.length() > 0) {
                        if (desc.charAt(desc.length() - 1) == 'Y') {
                            pop.append('I');
                            int num_triggers = int_pool[ref2 ? swi - 2 : swi - 1]; //TODO right entry?
                            if(num_triggers > 0) {
                                System.out.println("Pushing " + num_triggers + " triggers");
                                while (num_triggers-- > 0) {
                                    pop.append('I');
                                }
                            }
                            desc = desc.substring(0, desc.length() - 1);
                        }
                    }

                    Object[] array6 = new Object[desc.length() + 1];
                    for (int n150 = array6.length - 1; n150 >= 1; --n150) {
                        if (desc.charAt(n150 - 1) == 's') {
                            pop.append('S');
                        } else {
                            pop.append('I');
                        }
                    }

                    pop.append('I'); //id of the script...
                    pop = pop.reverse();
                    String pop0 = pop.toString();
                    System.out.println("Popping Args: " + "[" + desc + "] -> " + pop0);
                    stack = pop(stack, "(" + pop0 + ")V");
                   // swi++;

                    continue;
                }
            }

            switch (opcode) {
                case GOTO:
                    swi += int_pool[swi]; // Always true, jump
                    continue;
                case IF_ICMPNE:
                case IF_ICMPEQ:
                case IF_ICMPLT:
                case IF_ICMPGT:
                case IF_ICMPLE:
                case IF_ICMPGE:
                    stack = pop(stack,"(II)V");
                    swi += int_pool[swi]; // Always true, jump
                    continue;
            }

            throw new Error("Unhandled: " + op2String(opcode));

        }
        return null;
    }


    static String pop(String stack, String desc) {
        String stack0 = new String(stack);
        String args = desc.substring(desc.indexOf('(') + 1,desc.indexOf(')'));
        if(ref2) args =  args + 'I';
        String push = desc.substring(desc.indexOf(')') + 1, desc.length());
        if(!stack.endsWith(args)) {
            throw new Error("Stack Underflow: [" + stack + "] Args:" + "[" + args + "]");
        }
        stack = stack.substring(0,stack.length()-args.length());
        if(!push.equals("V")) {
            stack += push;
        }
      //  System.out.println(stack);

        System.out.println("[" + stack0 + "] - [" + args + "]" + " + " + "[" + (push.equals("V") ? "" : push) + "] ==> " + stack);
        return stack;
    }



    /*public static void test(RSMethod script) {

        CSLocalVar[] int_vars = new CSLocalVar[script.num_local_ints];
        CSLocalVar[] str_vars = new CSLocalVar[script.num_local_strings];
        int iv_idx = 0;
        int sv_idx = 0;

        final int max_objects = (script.num_local_ints + script.num_local_strings) / 2;
        ArrayList<CSObject> objects = new ArrayList<>(max_objects);

        for(int li = 0; li < script.num_int_args; li++) {
            int_vars[iv_idx] = new CSLocalVar(iv_idx, VType.INT, VarType.Arg);
            iv_idx++;
        }

        for(int si = 0; si < script.num_string_args; si++) {
            str_vars[sv_idx] = new CSLocalVar(sv_idx, VType.STRING, VarType.Arg);
            sv_idx++;
        }


        final int[] opcodes = script.opcodes;
        final int[] ipool = script.int_pool;

        for(int swi = 0; swi < opcodes.length; swi++) {


            int opcode = opcodes[swi];
            if(opcode == CSOpcodes.INVOKE) {

                final int nop = opcodes[swi + 1];
                if(nop == CSOpcodes.IPOP // Can not determine, object is not stored
                || nop == CSOpcodes.SPOP) continue;

                final int scriptID = ipool[swi];

                CSClassDef def = getClassDef("CS" + scriptID);
                if(def == null) throw new Error("Null Class Def: " + scriptID);
                System.out.println(def.fields.size() + "-" + scriptID);

               // if(!def.isObjectReturn()) continue;

                int num_ints = 0;
                int num_strs = 0;
                final int object_size = def.fields.size();
                String desc = "";
                int[] pointers = new int[object_size];
                int pidx = 0;

                while (swi++ < opcodes.length) {

                    opcode = opcodes[swi];

                    if(opcode == CSOpcodes.ISTORE) {
                        final int pointer = ipool[swi];
                        pointers[pidx++] = pointer;
                        desc += 'I';
                        num_ints++;
                    } else if(opcode == CSOpcodes.SSTORE) {
                        final int pointer = ipool[swi];
                        pointers[pidx++] = pointer;
                        desc += 'S';
                        num_strs++;
                    } else {
                        break;
                    }

                }

                if(def.isVoid()) {
                    if(desc.length() == 0) continue;
                    else {
                        throw new Error("Return Mismatch[" + scriptID + "]: Expected " +
                                "void return, got " + desc);
                    }
                }

                if(def.isSimpleReturn()) {
                    if(def.desc.equals(desc)) continue;
                    else {
                        throw new Error("Return Mismatch[" + scriptID + "]: Expected " +
                                "return type " + def.desc + ", got " + desc);
                    }
                }

                if(def.isObjectReturn()) {

                    if(def.num_ints != num_ints)
                        throw new Error("Object Gen[" + scriptID + "]: Expected " +
                                def.num_ints + " integer field values, got " + num_ints);

                    if(def.num_strings != num_strs)
                        throw new Error("Object Gen[" + scriptID + "]: Expected " +
                                def.num_strings + " string field values, got " + num_strs);

                    if(!def.desc.equals(desc))
                        throw new Error("Object Gen[" + scriptID + "]: Expected " +
                                def.desc + " field payload order, got " + desc);

                    boolean valid = false;
                    CSLocalVar[] vars = null;
                    int vidx = 0;

                    for(int p = 0; p < object_size; p++) {
                        VType type = VType.get(desc.charAt(p));
                        int pointer = pointers[p];
                        CSLocalVar var = type == VType.INT ?
                                int_vars[pointer] : str_vars[pointer];

                        if(var != null) { //Check if storing in the right object type...

                            if(var.ref != VarType.Field)
                                throw new Error("Expected field " + String.valueOf(pointer));

                            valid = true;
                            for(CSObject obj : objects) {

                                if(obj.def.name.equals(def.name)) continue; // same object type

                                for(CSLocalVar field : obj.fields) {
                                    if(field == var) throw new Error(def.name + " -> " + obj.def.name + " @ " + var);
                                }
                            }


                        } else {

                            if(valid) throw new Error("Object Fragmentation"); // some fields of the object were never created..?

                            if(vars == null) vars = new CSLocalVar[object_size];

                            var = new CSLocalVar(pointer,type,VarType.Field);

                            if(type == VType.INT) int_vars[pointer] = var;
                            if(type == VType.STRING) str_vars[pointer] = var;

                            vars[vidx++] = var;

                        }
                    }

                    if(vidx > 0) {
                        CSObject object = new CSObject();
                        object.fields = vars;
                        object.def = def;
                        object.var = objects.size();
                        objects.add(object);
                    }

                }

            }
        }

        *//** The rest of the variables are not used for arguments or objects, must be normal **//*

        for(int li = 0; li < int_vars.length; li++) {
            CSLocalVar var = int_vars[li];
            if(var == null) int_vars[li] = new CSLocalVar(li, VType.INT, VarType.Normal);
        }

        for(int si = 0; si < str_vars.length; si++) {
            CSLocalVar var = str_vars[si];
            if(var == null) str_vars[si] = new CSLocalVar(si, VType.STRING, VarType.Normal);
        }


        for(CSObject object : objects) {
            System.out.println(object.toString());
        }

        for(CSLocalVar var : int_vars) {
            System.out.println(var.toString());
        }

        for(CSLocalVar var : str_vars) {
            System.out.println(var.toString());
        }

    }*/







    static class ReturnDesc {
        int num_ints; // number of strings returned
        int num_strings; // number of ints returned
        String stackDesc; // tells the stack element types. EX: IIS
        String desc;
    }
    // Load the return
    //  LOAD A          C           STORE -> Store C  [0]
    //  LOAD B  --->    B     -->   STORE -> Store B  ]1]
    //  LOAD C          A           STORE -> Store A  [2]
    //  RETURN      [ C, B, A ]  <- Returned Values
    //                0  1  2
    static ReturnDesc getReturnDesc(Script script) {
        int num_ints = 0;
        int num_strings = 0;
        StringBuilder stackDesc = new StringBuilder();

        final int[] opcodes = script.opcodes;

        out:
        for(int swi = 0; swi < script.opcodes.length; swi++) {
            int op_code = opcodes[swi];
            if(op_code == RETURN) {
                /** Backtrack **/
                while (swi-- > 0) {
                    op_code = opcodes[swi];
                    char push = getPushChar(op_code);
                    System.out.println(op_code + " | " + push + " @ " + swi);
                    if(push == '?') break out;
                    if(push == 'I') num_ints++;
                    if(push == 'S') num_strings++;
                    stackDesc.append(push);
                }
            }
        }

        String desc = null;
        if(num_ints == 0 && num_strings == 0) desc = "V";
        else if(num_ints == 0 && num_strings == 1) desc = STRING_DESC;
        else if(num_ints == 1 && num_strings == 0) desc = "I";
        else desc = MULTI_RETURN_DESC;

        ReturnDesc desc0 = new ReturnDesc();
        desc0.desc = desc;
        desc0.stackDesc = stackDesc.toString();
        desc0.num_ints = num_ints;
        desc0.num_strings = num_strings;

        return desc0;
    }


    static char getPushChar(int op) {
        switch (op) { //TODO not to sure if this is a valid method (nesting allowed in this sdk?)
            case ILDC:
            case ILOAD:
            return 'I';

            case SLDC:
            case SLOAD:
            return 'S';
        }

        return '?';
    }


    static int cur_cont;

    public static String getDesc(Script script) {
        String arg_desc = getArgDesc(script);
        String ret_desc = StackHound.simulate(script).ret_stack;
        if(ret_desc.length() == 0) ret_desc = "V";
        System.out.println("RET:" + ret_desc);
        String ret = ret_desc.length() > 1 && !ret_desc.equals(STRING_DESC) ? "LMethod" + script.id + ";" : ret_desc;
        if(script.id == 228) {
            return "(I" + STRING_DESC + "I)I";
        }
        ret = ret.replace("S", STRING_DESC);
        arg_desc = arg_desc.replace("S",STRING_DESC);
        return "(" + arg_desc + ")" + ret;
    }


    public static void decompile(int id, File dest) throws IOException {
        ClassNode node = convert(Script.get(id));

        node.version = Opcodes.V1_6;
        node.superName = Type.getInternalName(Object.class);
        for(MethodNode mn : (List<MethodNode>) node.methods) {
            for(AbstractInsnNode ain : mn.instructions.toArray()) {
                if(ain instanceof LabelNode) {
                    System.out.println(((LabelNode) ain).getLabel());
                }
            }
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        FileOutputStream out = new FileOutputStream(dest);
        out.write(writer.toByteArray());
        out.flush();
        out.close();



    }

    public static ClassNode convert(Script script) {

        final int[] opcodes        = script.opcodes;
        final int[] int_pool       = script.int_operands;
        final String[] string_pool = script.string_operands;

        final String name = "Method" + script.id;
        ClassNode cn = new ClassNode();
        cn.name = name;

        StackHound.FrameInfo info =  StackHound.simulate(script);
        String arg_desc = getArgDesc(script);
        String ret_desc = info.ret_stack;
        String ret;

        int next_local = script.max_local_ints + script.max_local_strings;


        if(ret_desc.length() == 0) ret = "V"; // No Return
        else if(ret_desc.equals("S")) ret = STRING_DESC;
        else if(ret_desc.length() == 1) ret = ret_desc; //Single Return
        else ret = "L" + name + ";"; //Multiple return


        arg_desc = arg_desc.replace("S", STRING_DESC);


        String desc0 = "(" + arg_desc + ")" + ret;


        MethodNode mn = new MethodNode(0, name, desc0, null, null);
        cn.methods.add(mn);

        mn.access = Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC;

        String con_desc = "";

        MethodNode cons = new MethodNode(Opcodes.ACC_PUBLIC, "<init>", null, null, null);

        if(ret_desc.length() > 1) {
            int snum = 0;
            int inum = 0;
            int var_idx = 1;
            for(char field : ret_desc.toCharArray()) {

                boolean string = field == 'S';
                String field_name = string ? "string" + (snum++) : "int" + (inum++);
                String field_desc = string ? STRING_DESC : "I";
                FieldNode node = new FieldNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, field_name, field_desc, null, null);
                cn.fields.add(node);

                if(string) con_desc += STRING_DESC;
                else con_desc += 'I';

                cons.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
                cons.instructions.add(new VarInsnNode(string ? Opcodes.ALOAD : Opcodes.ILOAD, var_idx));
                cons.instructions.add(new FieldInsnNode(Opcodes.PUTFIELD, name, field_name, field_desc));

                var_idx++;
            }

            cons.desc = "(" + con_desc + ")V";



            cn.methods.add(cons);

        }



        InsnList stack = mn.instructions;

        HashMap<Integer, LabelNode> LABELS = new HashMap<Integer,LabelNode>();


        for (int swi = 0; swi < opcodes.length; swi++) {
            int word = opcodes[swi];
            int ildc = int_pool[swi];
            final int jop = convertJI(word, false);
            if (jop != -1) {
                final int jump = ildc;
                final int dest_idx = swi + jump + 1; //TODO negative jumps?
                LabelNode dest = LABELS.get(dest_idx);
                if (dest == null) {
                    MarkedLabel stamp = new MarkedLabel();
                    stamp.mark = dest_idx;
                    mn.visitLabel(stamp);
                    dest = new LabelNode(stamp);
                    LABELS.put(dest_idx, dest); // when the index is reached, the label will be inserted
                }
            }
        }

        System.out.println("LABELS: " + LABELS);

        int num_args = script.num_int_params + script.num_string_params;


        for(int swi = 0; swi < opcodes.length; swi++) {

            LabelNode jump_future = LABELS.get(swi);
            if(jump_future != null) { // Push the awaiting label
                System.out.println("ADD LABEL: " + ((MarkedLabel) jump_future.getLabel()).mark);
                stack.add(jump_future);
            }


            int word = opcodes[swi];
            final int ildc = int_pool[swi];
            final String sldc = string_pool[swi];

            cur_cont = ildc;

            System.out.println(swi + ":" + op2String(word) + "[" + word + "]" + " | " + ildc + " | " + sldc);
            /** Jump Instructions **/
        //    boolean bool_cmp = (swi - last_bool) == 1; //TODO boolean cmp simplify   != 0, != 1,
            final int jop  = convertJI(word, false);
            if(jop != -1) {
                final int jump = ildc;
                final int dest_idx = swi + jump + 1; //TODO negative jumps?
                LabelNode dest = LABELS.get(dest_idx);

                if(dest == null) {
                    throw new Error(); // when the index is reached, the label will be inserted
                }

                if(word == GOTO && ildc == 41) {
                    stack.add(new InsnNode(Opcodes.ICONST_M1));
                    stack.add(new InsnNode(Opcodes.ICONST_M1));
                    stack.add(new InsnNode(Opcodes.ICONST_M1));
                }


                JumpInsnNode jin = new JumpInsnNode(jop, dest);

                stack.add(jin);

                continue;
            }

            /** LDC instructions **/
            if(word == ILDC || word == SLDC) {
                /*if(word == ILDC) {
                    if (ildc >= -1 && ildc <= 5) {
                        stack.add(new InsnNode(3 + ildc));
                    } else {

                    }
                }*/

                Object cst = word == ILDC ? new Integer(ildc) : sldc;
                LdcInsnNode lin = new LdcInsnNode(cst);
                stack.add(lin);
                continue;
            }



            /** Direct Instructions **/
  //        if(word >= 4100) word -= 100;
            switch (word) {
                /** Local Variable Instruction **/
                case ILOAD: {
                    int var_idx = ildc;
                    if (var_idx < num_args) { // Local variable is an arugment, correct/validate position
                        char[] args = arg_desc.toCharArray();
                        int hits = 0;
                        int jump = 0;
                        for (int a = 0; a < args.length; a++) {
                            System.out.println(args[a]);
                            if (args[a] == 'I') {
                                if (hits == ildc) {
                                    var_idx = jump;
                                    break;
                                }
                                jump++;
                            } else if(args[a] == 'L') {
                                a += STRING_DESC.length() - 1;
                                jump++;
                            }
                        }
                    }
                    System.out.println("ILOAD:" + var_idx + "," + ildc + "," + num_args);
                    stack.add(new VarInsnNode(Opcodes.ILOAD, var_idx /*+ script.max_local_ints*/));
                    continue;
                }
                case ISTORE: {

                    int var_idx = ildc;
                    if (var_idx < num_args) { // Local variable is an arugment, correct/validate position
                        char[] args = arg_desc.toCharArray();
                        int hits = 0;
                        for (int a = 0; a < args.length; a++) {
                            if (args[a] == 'I') {
                                if (++hits == ildc) {
                                    var_idx = a;
                                    break;
                                }
                            } else if(args[a] == 'S') {
                                a += STRING_DESC.length() - 1;
                            }
                        }
                    }
                    System.out.println("0ISTORE:" + var_idx + "," + ildc + "," + num_args);
                    stack.add(new VarInsnNode(Opcodes.ISTORE, var_idx /*+ script.max_local_ints*/));
                    continue;
                }
                case SLOAD: {

                    int var_idx = ildc;
                    if (var_idx < num_args) { // Local variable is an arugment, correct/validate position
                        char[] args = arg_desc.replace(STRING_DESC,"S").toCharArray();
                        int hits = 0;
                        for (int a = 0; a < args.length; a++) {
                            if (args[a] == 'S') {
                                if (++hits == ildc) {
                                    var_idx = a;
                                    break;
                                }
                                hits++;
                            }
                        }
                    }

                    stack.add(new VarInsnNode(Opcodes.ALOAD, var_idx));
                    continue;
                }
                case SSTORE: {

                    int var_idx = ildc;
                    if (var_idx < num_args) { // Local variable is an arugment, correct/validate position
                        char[] args = arg_desc.replace(STRING_DESC,"S").toCharArray();
                        int hits = 0;
                        for (int a = 0; a < args.length; a++) {
                            if (args[a] == 'S') {
                                if (hits == ildc) {
                                    var_idx = a;
                                    break;
                                }
                                hits++;
                            }
                        }
                    }

                    stack.add(new VarInsnNode(Opcodes.ASTORE, var_idx));
                    continue;
                }
                case IPOP:
                case SPOP:
                    stack.add(new InsnNode(Opcodes.POP));
                    continue;
                case IADD:
                    stack.add(new InsnNode(Opcodes.IADD));
                    continue;
                case ISUB:
                    stack.add(new InsnNode(Opcodes.ISUB));
                    continue;
                case IMUL:
                    stack.add(new InsnNode(Opcodes.IMUL));
                    continue;
                case IDIV:
                    stack.add(new InsnNode(Opcodes.IDIV));
                    continue;
                case IREM:
                    stack.add(new InsnNode(Opcodes.IREM));
                    continue;
                case IAND:
                    stack.add(new InsnNode(Opcodes.IAND));
                    continue;
                case IOR:
                    stack.add(new InsnNode(Opcodes.IOR));
                    continue;
                case SETBIT:
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Logical", "setBit", "(II)I",false));
                    continue;
                case CLRBIT:
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Logical", "clrBit", "(II)I",false));
                    continue;
                case TESTBIT:
                    // bool = A & (1 << B) != 0 ? 1 : 0
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Logical", "testBit", "(II)Z",false));
                    continue;

            }

            if(word == RETURN) {


                if(ret_desc.length() == 0) {
                    stack.add(new InsnNode(Opcodes.RETURN));
                    continue;
                }

                if(ret_desc.equals("I")) {
                    stack.add(new InsnNode(Opcodes.IRETURN));
                    continue;
                }

                if(ret_desc.equals("S")) {
                    stack.add(new InsnNode(Opcodes.ARETURN));
                    continue;
                }

           /*     stack.add(new TypeInsnNode(Opcodes.NEW, name));
                stack.add(new InsnNode(Opcodes.DUP));*/
                stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, name, "create", "(" + con_desc + ")L" + name + ";"));

                stack.add(new InsnNode(Opcodes.ARETURN)); //String to object
                continue;


            }

            ref2 = word >= 2000 && word < 3100;
            if(ref2) word -= 1000;

            switch(word) {

                case GETVAR: {
                    stack.add(new LdcInsnNode(ildc));
                    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "getVar", "(I)I"
                    ));
                    continue;
                }

                case PUTVAR: {
                    stack.add(new LdcInsnNode(ildc));
                    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "putVar", "(II)V"
                    ));
                    continue;
                }

                case GETVARPB: {
                    stack.add(new LdcInsnNode(ildc));
                    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "getVarpbit", "(I)I"
                    ));
                    continue;
                }

                case PUTVARPB: {
                    stack.add(new LdcInsnNode(ildc));
                    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "putVarpbit", "(II)V" // (Value,VarpID)
                    ));
                    continue;
                }

                case SAPPX: { // combines multiple strings like: String combined  = "hi" + "lol" + "wot"
                    final int num_strings = ildc;
                    StringBuilder desc = new StringBuilder();
                    for (int i = 0; i < num_strings; i++) {
                        desc.append(STRING_DESC);
                    }
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "append", "(" + desc.toString() + ")" + STRING_DESC));
                    continue;
                }

                case INVOKE: {
                    final int script_id = ildc;
                    Script rs = Script.get(script_id);
                    String sdesc = getDesc(rs);
                    StackHound.FrameInfo frame = StackHound.simulate(rs);
                    if(script_id == 193) sdesc = "(I" + STRING_DESC + ")I";
                    else if(script_id == 90) sdesc = "(I" + STRING_DESC + STRING_DESC + "II" + STRING_DESC + ")I";
                    else if(script_id == 601) sdesc = "(I" + STRING_DESC + ")V";
                    else if(script_id == 621) sdesc = "(II" + STRING_DESC + "II)V";
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Method" + script_id, "Method" + script_id, sdesc));


                    if (sdesc.contains("Method")) {
                        int object_loc = next_local++;
                        stack.add(new VarInsnNode(Opcodes.ASTORE, object_loc));
                      /*  int nop = opcodes[swi + 1];
                        if (nop == RSOpcodes.IPOP || nop == RSOpcodes.SPOP) {
                            stack.add(new InsnNode(Opcodes.POP));
                        } else {*/

                            String rstack = frame.ret_stack;
                            int ifi = 0;
                            int sfi = 0;
                            for (int c = 0; c < rstack.length(); c++) {
                                //... Load the return back into the stack
                                if (rstack.charAt(c) == 'I') {
                                    stack.add(new VarInsnNode(Opcodes.ALOAD, object_loc));
                                    stack.add(new FieldInsnNode(Opcodes.GETFIELD, "Method" + script_id, "int" + (ifi++), "I"));
                                    System.out.println("LOAD INT");
                                } else if (rstack.charAt(c) == 'S') {
                                    stack.add(new VarInsnNode(Opcodes.ALOAD, object_loc));
                                    stack.add(new FieldInsnNode(Opcodes.GETFIELD, "Method" + script_id, "string" + (sfi++), STRING_DESC));
                                    System.out.println("LOAD String");
                                } else throw new Error("?@" + rstack.charAt(c));
                            }

                            // System.out.println("Store to object " + field.obj + "(" + (next_local + field.obj) + ")");
                       /* }*/
                        // swi += frame.ret_stack.length(); // Skip all field stores

                    }


                    continue;
                }

                case GETSTATICI: {
                    final int static_field = ildc;
                    //stack.add(new LdcInsnNode(static_field));
                    stack.add(new FieldInsnNode(Opcodes.GETSTATIC, "Statics", "StaticInt_" + static_field, "I"));
                  /*  stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "getStaticInt", "(I)I"
                    ));*/
                    continue;
                }

                case PUTSTATICI: {
                    final int static_field = ildc;
                    //stack.add(new LdcInsnNode(static_field));
                    stack.add(new FieldInsnNode(Opcodes.PUTSTATIC, "Statics", "StaticInt_" + static_field, "I"));
                   /* final int static_field = ildc;
                    stack.add(new LdcInsnNode(static_field));
                    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "putStaticInt", "(II)V" //(value,field_index);
                    ));*/
                    continue;
                }

                case GETSTATICS: {
                    final int static_field = ildc;
                    stack.add(new FieldInsnNode(Opcodes.GETSTATIC, "Statics", "StaticString_" + static_field, STRING_DESC));
                 /*   stack.add(new LdcInsnNode(static_field));
                    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "getStaticString", "(I)Ljava/lang/Stringl;"
                    ));*/
                    continue;
                }

                case PUTSTATICS: {
                    final int static_field = ildc;
                    stack.add(new FieldInsnNode(Opcodes.PUTSTATIC, "Statics", "StaticString_" + static_field, STRING_DESC));
                 /*   stack.add(new LdcInsnNode(static_field));
                    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "putStaticString", "(ILjava/lang/String;)V" //(value,field_index);
                    ));*/
                    continue;
                }

                case IACLR: {
                    final int static_field = ildc;
                    stack.add(new LdcInsnNode(static_field));
                    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "clearTable", "(II)V"
                    ));
                    continue;
                }

                case IALOAD: {
                    final int row = ildc;
                    stack.add(new LdcInsnNode(row));
                    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "getTableValue", "(II)I"
                    ));
                    continue;
                }

                case IASTORE: {
                    final int row = ildc;
                    stack.add(new LdcInsnNode(row));
                    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "putTableValue", "(III)V"
                    ));
                    continue;
                }

                //-------------------------------------------------------------------------------------------

                case NEWW: {
                    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "createWidget", "(III)" + WIDGET_DESC
                    ));
                    stack.add(putLocalWidget(int_pool[swi]));
                    continue;
                }

                case DELW: { // remove a local widget from its parent....
                    stack.add(getLocalWidget(int_pool[swi]));
                    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "remove", "(" + WIDGET_DESC + ")V"
                    ));

                    continue;
                }

                case DELAC: {
                    // widget.chaldean = null;
                    stack.add(getLocalWidget(int_pool[swi]));
                    stack.add(new InsnNode(Opcodes.ACONST_NULL));
                    stack.add(new FieldInsnNode(Opcodes.PUTFIELD, WIDGET_NAME, "children", "[" + WIDGET_DESC));
                    continue;
                }

                case GET_WIDGET: {
                    // if((local = getWidget(A,B)) != null) { ... } Nested...
                    if (opcodes[swi + 2] != RSOpcodes.IF_ICMPEQ) throw new Error(op2String(opcodes[swi + 2]));

                    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC, "Functions", "getWidget", "(II)" + WIDGET_DESC
                    ));
                    stack.add(new InsnNode(Opcodes.DUP));
                    stack.add(putLocalWidget(int_pool[swi]));


                    Label A0 = new Label();
                    Label B0 = new Label();
                    mn.visitLabel(A0);
                    mn.visitLabel(B0);
                    LabelNode A = new LabelNode(A0);
                    LabelNode B = new LabelNode(B0);
                    stack.add(new JumpInsnNode(Opcodes.IFNONNULL, A));
                    stack.add(new InsnNode(Opcodes.ICONST_1));
                    stack.add(new JumpInsnNode(Opcodes.GOTO, B));
                    stack.add(A);
                    stack.add(new InsnNode(Opcodes.ICONST_0));
                    stack.add(B);

                    continue;
                }

                case PUT_WPOS: {

                    loadW(stack);

                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Widgets", "setPosition", "(II" + WIDGET_DESC + ")V"));
                    continue;
                }

                case PUT_WDIM: {
                    loadW(stack);

                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Widgets", "setDimentions", "(II" + WIDGET_DESC + ")V"));

                    continue;
                }

                case PUT_WVIS: {
                    putWField(stack, "visible", "Z");
                    continue;
                }

                case PUT_WINSETS: {
                    putWField(stack, "visible", "Z");
                    continue;
                }

                case PUT_WTXTCLR: {
                    putWField(stack, "text_color", "I");
                    continue;
                }

                case PUT_1102: {
                    putWField(stack, "F1102", "I");
                    continue;
                }

                case PUT_WALPHA: {
                    putWField(stack, "F1102", "I");
                    continue;
                }

                case PUT_1104: {
                    putWField(stack, "F1104", "I");
                    continue;
                }

                case PUT_WTEXTURE: {
                    putWField(stack, "texture", "I");
                    continue;
                }

                case PUT_WSPRIROT: {
                    putWField(stack, "sprite_rot", "I");
                    continue;
                }

                case PUT_1107: {
                    putWField(stack, "F1107", "Z");
                    continue;
                }

                case PUT_WMODEL1: {
                    putWField(stack, "model_type", "I");
                    putWField(stack, "model_id", "I");
                    continue;
                }

                case PUT_WMODEL: {
                    putWField(stack, "MODEL1109_PRT_0", "I");
                    putWField(stack, "MODEL1109_PRT_1", "I");
                    putWField(stack, "MODEL1109_PRT_2", "I");
                    putWField(stack, "MODEL1109_PRT_3", "I");
                    putWField(stack, "MODEL1109_PRT_4", "I");
                    putWField(stack, "MODEL1109_PRT_5", "I");
                    continue;
                }

                case PUT_1110: {
                    putWField(stack, "F1110_PRT_0", "I");
                    putWField(stack, "F1110_PRT_1", "I", 0);
                    putWField(stack, "F1110_PRT_2", "I", 0);
                    continue;
                }

                case PUT_1111: {
                    putWField(stack, "F1111", "Z");
                    continue;
                }

                case PUT_WTXT: {
                    putWField(stack, "text", STRING_DESC);
                    continue;
                }

                case PUT_WFONT: {
                    putWField(stack, "font", "I");
                    continue;
                }

                case PUT_1114: {
                    putWField(stack, "F1114_PRT_0", "I");
                    putWField(stack, "F1114_PRT_1", "I");
                    putWField(stack, "F1114_PRT_2", "I");
                    continue;
                }

                case PUT_1115: {
                    putWField(stack, "F1115", "Z");
                    continue;
                }

                case PUT_WBORDERTHK: {
                    putWField(stack, "border_thickness", "I");
                    continue;
                }

                case PUT_WSHADOWCLR: {
                    putWField(stack, "shadow_color", "I");
                    continue;
                }

                case PUT_WVIRFLP: {
                    putWField(stack, "vertically_flipped", "Z");
                    continue;
                }

                case PUT_WHORFLP: {
                    putWField(stack, "horizontally_flipped", "Z");
                    continue;
                }

                case PUT_WSCROLDIM: {
                    loadW(stack);
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Widget", "setScrollbarDim", "(II" + WIDGET_DESC + ")V"));
                 /*   putWField(stack, "scrollbar_width", "I");
                    putWField(stack, "scrollbar_height", "I");*/
                    continue;
                }

                case PUT_PROCW: {
                    loadW(stack);
                    stack.add(new FieldInsnNode(Opcodes.PUTFIELD, "Statics", "processing_widget", WIDGET_DESC));
                    continue;
                }

                //----------------------------------------------------------------------------------

                case PUT_WITEM: {
                    putWField(stack, "item_id", "I");
                    putWField(stack, "item_num", "I");
                /*    stack.add(new MethodInsnNode(
                            Opcodes.INVOKESPECIAL, WIDGET_NAME, "putItem", "(II)V"
                    ))*/
                    ;
                    //   stack.add(new FieldInsnNode(Opcodes.PUTFIELD, "Statics", "processing_widget", WIDGET_DESC));
                    continue;
                }

                case PUT_MODEL2: {
                    putWField(stack, "model_type", "I", 2);
                    putWField(stack, "model_id", "I");

                    continue;
                }

                case PUT_MODEL3: {
                    putWField(stack, "model_type", "I", 3);
                    putWField(stack, "model_id", "I");
                    continue;
                }

                //--------------------------------------------------------------------

                case PUT_WACT: {
                    int num_actions = ildc;
                    StringBuilder desc = new StringBuilder();
                    for (int i = 0; i < num_actions; i++) {
                        desc.append(STRING_DESC);
                    }
                    loadW(stack);
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Widgets", "setAction", "(I" + STRING_DESC + WIDGET_DESC + ")V"));
                    //stack.add(new FieldInsnNode(Opcodes.GETFIELD,WIDGET_NAME,"actions", "[" + STRING_DESC));
                    //stack.add(new InsnNode(Opcodes.AASTORE));
                    continue;
                }

                case PUT_WPARENT: {
                    loadW(stack);
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getWidget", "(II)" + WIDGET_DESC));
                    stack.add(new FieldInsnNode(Opcodes.PUTFIELD, WIDGET_NAME, "parent", WIDGET_DESC));
                    continue;
                }

                case PUT_1302: {
                    putWField(stack, "F1302", "Z");
                    continue;
                }

                case PUT_1303: {
                    putWField(stack, "F1303", "I");
                    continue;
                }

                case PUT_1304: {
                    putWField(stack, "F1304", "I");
                    continue;
                }

                case PUT_1305: {
                    putWField(stack, "F1305", STRING_DESC);
                    continue;
                }

                case PUT_WTITL: {
                    putWField(stack, "tittle", STRING_DESC);
                    continue;
                }

                case DEL_WACTS: {

                    loadW(stack);
                    stack.add(new InsnNode(Opcodes.ACONST_NULL));
                    stack.add(new FieldInsnNode(Opcodes.PUTFIELD, WIDGET_NAME, "actions", "[" + STRING_DESC));

                    continue;
                }

                case GET_WX: {
                    getWField(stack, "x", "I");
                    continue;
                }

                case GET_WY: {
                    getWField(stack, "y", "I");
                    continue;
                }

                case GET_WW: {
                    getWField(stack, "width", "I");
                    continue;
                }

                case GET_WH: {
                    getWField(stack, "height", "I");
                    continue;
                }

                case IS_WVIS: {
                    getWField(stack, "visible", "Z");
                    continue;
                }

                case GET_WPAR: {
                    getWField(stack, "parentID", "I");
                    continue;
                }

                case GET_WINSETX: {
                    getWField(stack, "insetX", "I");
                    continue;
                }

                case GET_WINSETY: {
                    getWField(stack, "insetY", "I");
                    continue;
                }

                case GET_WTXT: {
                    getWField(stack, "text", STRING_DESC);
                    continue;
                }

                case GET_WSCROLW: {
                    getWField(stack, "scrollbar_width", "I");
                    continue;
                }

                case GET_WSCROLH: {
                    getWField(stack, "scrollbar_height", "I");
                    continue;
                }

                case GET_WZOOM: {
                    getWField(stack, "model_zoom", "I");
                    continue;
                }

                case GET_WROTX: {
                    getWField(stack, "rot_x", "I");
                    continue;
                }

                case GET_WROTY: {
                    getWField(stack, "rot_y", "I");
                    continue;
                }

                case GET_WROTZ: {
                    getWField(stack, "rot_z", "I");
                    continue;
                }

                case GET_WITEM: {
                    getWField(stack, "item_id", "I");
                    continue;
                }

                case GET_WIAMT: {
                    getWField(stack, "item_num", "I");
                    continue;
                }

                case GET_WIDX: {
                    getWField(stack, "index", "I");
                    continue;
                }

                case GET_WSPELTRGT: {
                    getWField(stack, "spell_target", "I");
                    continue;
                }

                case GET_WACTION: {
                    getWField(stack, "actions", "[" + STRING_DESC);
                    stack.add(new InsnNode(Opcodes.ALOAD));
                    continue;
                }

                case GET_WNAME: {
                    getWField(stack, "name", STRING_DESC);
                    continue;
                }


                case SEND_3100: { // 1 strings
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Packets", "Send3100", "(Ljava/lang/String;)V"));
                    continue;
                }

                case SET_ANIM: { // 2 ints  //TODO seemingly does not send any packet...?
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Packets", "SetLocalAnimation", "(II)V"));
                    continue;
                }

                case CLOSE_MAJOR: { // no args
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Packets", "Send3103", "()V"));
                    continue;
                }

                case SEND_3104: { // 1 string
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Packets", "Send3104", "(Ljava/lang/String;)V"));
                    continue;
                }

                case SEND_3105: { // 1 String
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Packets", "Send3105", "(Ljava/lang/String;)V"));
                    continue;
                }

                case SEND_3106: { // 1 String
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Packets", "Send3106", "(Ljava/lang/String;)V"));
                    continue;
                }

                case SEND_3107: { // 1 int  1 String
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Packets", "Send3107", "(ILjava/lang/String;)V"));
                    continue;
                }

                case SEND_3108: { // 3 ints
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Packets", "Send3108", "(III)V"));
                    continue;
                }

                case SEND_3109: { // 2 ints
                    stack.add(getLocalWidget(ildc));
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Packets", "Send3109", "(" + WIDGET_DESC + "II)V"));
                    continue;
                }

                case PUTSTATIC_3110: { // 1 int
                    //TODO    static_boolean = (ldc == 1)
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Statics", "PutStaticBool", "(I)V"));
                    continue;
                }

                case IS_3111: { // no args  //TODO int-boolean return
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "Is3111", "()I"));
                    continue;
                }

                case PUT_3112: { // 1 int  //TODO int-boolean setter
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "Put3112", "(I)V"));
                    continue;
                }

                case OPEN_URL: { // 1 string
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "openURL", "(Ljava/lang/String;)V"));
                    continue;
                }

                //-------------------------------------------------------------------------------------------

                case SET_AUDIO3200: { // 3 ints
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "SetAudio3200", "(III)V"));
                    continue;
                }

                case SET_AUDIO3201: { // 1 ints
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "SetAudio3201", "(I)V"));
                    continue;
                }

                case SET_AUDIO3202: { // 2 ints
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "SetAudio3202", "(II)V"));
                    continue;
                }

                //---------------------------------------------------------------------------------------------

                case GET_CCYCLE: { // no args
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getClientCycle", "()I"));
                    continue;
                }

                case GET_CITEMID: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getContainerItemAt", "(II)I"));
                    continue;
                }

                case GET_CITEMAMT: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getContainerItemCountAt", "(II)I"));
                    continue;
                }

                case GET_CAMT: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getContainerItemCount", "(II)I"));
                    continue;
                }

                case REF2VARID: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getVarpID", "(I)I"));
                    continue;
                }

                case GET_CLVL: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getCurrentLevel", "(I)I"));
                    continue;
                }

                case GET_RLVL: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getRealLevel", "(I)I"));
                    continue;
                }

                case GET_XP: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getSkillXP", "(I)I"));
                    continue;
                }

                case GET_LOC: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getLocationHash", "()I"));
                    continue;
                }

                case LOC2X: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getXForHash", "(I)I"));
                    continue;
                }

                case LOC2Y: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getYForHash", "(I)I"));
                    continue;
                }

                case LOC2Z: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getZForHash", "(I)I"));
                    continue;
                }

                case IS_MEMWORLD: { //TODO int-boolean return
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "isMembersWorld", "()I"));
                    continue;
                }

                case GET_CITEMID2: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getContainerItemAt2", "(II)I"));
                    continue;
                }

                case GET_CITEMAMT2: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getContainerItemCountAt2", "(II)I"));
                    continue;
                }

                case GET_CAMT2: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getContainerItemCount2", "(II)I"));
                    continue;
                }

                case GET_RIGHTS: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getRights", "()I"));
                    continue;
                }

                case GET_3317: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "get3317", "()I"));
                    continue;
                }

                case GET_WORLD: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getWorldID", "()I"));
                    continue;
                }

                case GET_ENERGY: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getRunEnergy", "()I"));
                    continue;
                }

                case GET_WEIGHT: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getWeight", "()I"));
                    continue;
                }

                case IS_MALE: { //TODO int-boolean return
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "isMale", "()I"));
                    continue;
                }

                case GET_WORLDINFO: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getWorldInfo", "()I"));
                    continue;
                }

                //------------------------------------------------------------------------------------------------------

                case GET_SCACHEVAL: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getUnknown3400", "(II)Ljava/lang/String;"));
                    continue;
                }

                case GET_GCACHEVAL: {
                    boolean is_string = int_pool[swi-3] == 's'; //TODO generic return type, no idea how to tell the return, can be an int or a string...???!?
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getCacheTableValue", "(IIII)" + (is_string ? STRING_DESC : "I"))); //TODO multi return..?
                    continue;
                }

                //------------------------------------------------------------------------------------------------------

                case GET_NUMFRIENDS: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getTotalFriends", "()I"));
                    continue;
                }

                case GET_FRIENDNAME: {
                    throw new Error();
                  /*  stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getFriendName", "(I)"));
                    continue;*/
                }

                case I2S: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(String.class), "valueOf", "(I)" + STRING_DESC));
                    continue;
                }

                case IS_MEMBERS:
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "isItemMembers", "(I)Z"));
                    continue;

                case APPS:


                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "append", "(" + STRING_DESC + STRING_DESC + ")" + STRING_DESC));

                  /*  stack.add(new TypeInsnNode(Opcodes.NEW, Type.getInternalName(StringBuilder.class)));
                    stack.add(new InsnNode(Opcodes.DUP));
                    stack.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, Type.getInternalName(StringBuilder.class), "<init>", "()V", false));
                    stack.add(new InsnNode(Opcodes.SWAP));
                    stack.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Type.getInternalName(StringBuilder.class), "append",
                            "(" + STRING_DESC + ")L" + Type.getInternalName(StringBuilder.class) + ";", false));
                    stack.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Type.getInternalName(StringBuilder.class), "append",
                            "(" + STRING_DESC + ")L" + Type.getInternalName(StringBuilder.class) + ";", false));
                    stack.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Type.getInternalName(StringBuilder.class), "toString",
                           "()" + STRING_DESC, false));*/
                    continue;

                case IDXOFS:
                    stack.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Type.getInternalName(String.class), "indexOf", "(" + STRING_DESC + "I)I"));
                    continue;

                case SLENGTH:
                    stack.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Type.getInternalName(String.class), "length", "()I"));
                    continue;

                case SUBS:
                    stack.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Type.getInternalName(String.class), "subString", "(II)" + STRING_DESC));
                    continue;

                case PROC_CMD:
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "procCMD", "(" + STRING_DESC + ")V"));
                    continue;

                case IDXOFC:
                    stack.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Type.getInternalName(String.class), "indexOf", "(C)I"));
                    continue;
                case GET_CLANSIZE:
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Clan", "totalMembers", "()I"));
                    continue;
                case LCS:

                    stack.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Type.getInternalName(String.class), "toLowerCase", "()" + STRING_DESC));
                    continue;
                case CMPS:

                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "compareString", "(" + STRING_DESC + STRING_DESC + ")I"));
                    continue;


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
                    String desc = script.string_operands[argDesc];

                    if (desc == null)
                        throw new Error();

                    StringBuilder pop = new StringBuilder(desc.length() + 2);

                    pop.append('S');

                    if (desc.length() > 0 && desc.charAt(desc.length() - 1) == 'Y') {
                        pop.append('I');
                        int num_triggers = int_pool[ref2 ? swi - 2 : swi - 1]; //TODO right entry?
                        if (num_triggers > 0) {
                            System.out.println("Pushing " + num_triggers + " triggers");
                            while (num_triggers-- > 0) {
                                pop.append('I');
                            }
                        }
                        desc = desc.substring(0, desc.length() - 1);

                    }

                    for (int n150 = desc.length(); n150 >= 1; --n150) {
                        if (desc.charAt(n150 - 1) == 's') {
                            pop.append('S');
                        } else {
                            pop.append('I');
                        }
                    }

                    pop.append('I'); //id of the script...

                    pop = pop.reverse();
                    String raw = pop.toString();
                    raw = raw.replace("S", STRING_DESC);


                    loadW(stack);

                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "WidgetListeners", op2String(word) + "Listener", "(" + raw + WIDGET_DESC + ")V"));

                    continue;
                }

                case U4108: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "U4108", "(" + STRING_DESC + "II)I"));
                    continue;
                }

                case GET_INAME: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getItemName", "(I)" + STRING_DESC));
                    continue;
                }

                case PUT_MOD1205: {
                    loadW(stack);
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "putMod1205", "(II" + WIDGET_DESC + ")V"));
                    continue;
                }

                case IS_ISTKBL: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "isItemStackable", "(I)Z"));
                    continue;
                }

                case SEND_5002: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Packets", "send5002", "(" + STRING_DESC + "II)V"));
                    continue;
                }

                case TLC: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "LitConfig", "convert2LitCfg", "(" + STRING_DESC + ")" + STRING_DESC));
                    continue;
                }

                case U4109: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Unknown", "M4109", "(" + STRING_DESC + "II)I"));
                    continue;
                }

                case LOCALNAME: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Players", "getLocalPlayerName", "()" + STRING_DESC));
                    continue;
                }

                case DEL_LC: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "LitConfig", "removeLitCfg", "(" + STRING_DESC + ")" + STRING_DESC));
                    continue;
                }

                case GETMSGW: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Chat", "getWorldMsg", "(I)" + MESSAGE_DESC));
                    int local = next_local++;
                    stack.add(new VarInsnNode(Opcodes.ASTORE, local));

                    stack.add(new VarInsnNode(Opcodes.ALOAD, local));
                    stack.add(new FieldInsnNode(Opcodes.GETFIELD, MESSAGE_NAME, "type", "I"));

                    stack.add(new VarInsnNode(Opcodes.ALOAD, local));
                    stack.add(new FieldInsnNode(Opcodes.GETFIELD, MESSAGE_NAME, "id", "I")); //TODO check

                    stack.add(new VarInsnNode(Opcodes.ALOAD, local));
                    stack.add(new FieldInsnNode(Opcodes.GETFIELD, MESSAGE_NAME, "sender", STRING_DESC));

                    stack.add(new VarInsnNode(Opcodes.ALOAD, local));
                    stack.add(new FieldInsnNode(Opcodes.GETFIELD, MESSAGE_NAME, "channel", STRING_DESC));


                    stack.add(new VarInsnNode(Opcodes.ALOAD, local));
                    stack.add(new FieldInsnNode(Opcodes.GETFIELD, MESSAGE_NAME, "message", STRING_DESC));

                    continue;
                }

                case HAS_PREVNAMEF: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Friends", "friendHasPreviousName", "(" + STRING_DESC + ")Z"));
                    continue;
                }

                case GET_LOCALCRANK: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Clan", "myRank", "()I"));
                    continue;
                }

                case GET_3616: {
                    stack.add(new FieldInsnNode(Opcodes.GETSTATIC, "Unknown", "UnknownInt3616", "I"));
                    continue;
                }

                case NEXT_MSG: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Message", "getNext", "(I)I"));
                    continue;
                }

                case SEND_3115: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Packets", "send3115", "(I)V"));
                    continue;
                }

                case IS_4113: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "is4113", "(I)Z"));
                    continue;
                }

                case IS_LWC: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Character.class), "isLowercase", "(I)Z"));
                    continue;
                }

                case APPC: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "Append", "(" + STRING_DESC + "C)V"));
                    continue;
                }

                case IS_DGT: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC,Type.getInternalName(Character.class), "isDigit", "(I)Z"));
                    continue;
                }

                case GET_CLANCHAN: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "get3625", "()" + STRING_DESC));
                    continue;
                }

                case GET_CMEMNAME: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Clan", "getMemberName", "(I)" + STRING_DESC));
                    continue;
                }

                case GET_CMEMWORLD: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Clan", "getMemberWorld", "(I)I"));
                    continue;
                }

                case GET_CMEMRANK: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Clan", "getMemberRank", "(I)I"));
                    continue;
                }

                case IS_IGNOREME: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Friends", "isIgnoreMe", "(I)Z"));
                    continue;
                }

                case HAS_PREVNAMEI: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Ignored", "hasPrevName", "(" + STRING_DESC + ")Z"));
                    continue;
                }

                case GET_NUMIGNORED: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Ignored", "getIgnoredCount", "()I"));
                    continue;
                }

                case ADD_IGNORED: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Ignored", "addIgnored", "(I)V"));
                    continue;
                }

                case GET_EXCHANGE_OFFER_ITEM_ID: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "GE", "getOfferItem", "(I)I"));
                    continue;
                }

                case GET_EXCHANGE_OFFER_ITEM_PRICE: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "GE", "getOfferPrice", "(I)I"));
                    continue;
                }

                case GET_EXCHANGE_OFFER_ITEM_QUANTITY: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "GE", "getOfferQuantity", "(I)I"));
                    continue;
                }

                case IS_EXCHANGE_OFFER_SLOT_USED: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "GE", "isSlotOccupied", "(I)Z"));
                    continue;
                }

                case IS_EXCHANGE_OFFER_PENDING: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "GE", "isOfferPending", "(I)Z"));
                    continue;
                }
                case IS_EXCHANGE_OFFER_PURCHASED: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "GE", "isOfferPurchased", "(I)Z"));
                    continue;
                }
                case IS_EXCHANGE_OFFER_PENIS: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "GE", "isOfferPenis", "(I)Z"));
                    continue;
                }
                case GET_EXCHANGE_OFFER_TRANSFERRED: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "GE", "getOfferTransferred", "(I)I"));
                    continue;
                }
                case IS_EXCHANGE_OFFER_FINISHED: {
                    stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "GE", "isOfferFinished", "(I)I"));
                    continue;
                }


















            }

            throw new Error("Unhandled Instruction: " + op2String(word) + "(" + word + ")");

        }

   /*     int k = 0;
     for(Integer node : LABELS.keySet()) {
         LabelNode ln = LABELS.get(node);
         System.out.println(ln.getLabel() + ":" + node + ":" + ln.getNext() + "," + ln.getPrevious());
      //   System.out.println(node+ ":" + node.getNext() + "," + node.getPrevious());
     }

        for(AbstractInsnNode ain : mn.instructions.toArray()) {
            if(ain instanceof LabelNode) {
                LabelNode ln = (LabelNode) ain;
                System.out.println(ln.getLabel() + ": Next[" + ln.getNext() + "] Pre:[" + ln.getPrevious() + "]");
            }
        }*/


        mn.maxLocals = 50;
        mn.maxStack = 50;

        CheckClassAdapter adapter = new CheckClassAdapter(null);
     //   adapter.visitCode();
        cn.superName = Type.getInternalName(Object.class);
        cn.accept(adapter);




   //     mn.instructions.resetLabels();
      //  System.out.println(Arrays.toString(mn.instructions.toArray()));
        return cn;

    }

    static void loadW(InsnList stack) {
        if(ref2) {
            // the previous would should be a ldc?
            stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "Functions", "getWidget", "(I)" + WIDGET_DESC));
            return;
        }
        stack.add(getLocalWidget(cur_cont));
    }

    static void putWField(InsnList stack, String name, String desc, Object val) {
        loadW(stack);
        stack.add(new LdcInsnNode(val));
        stack.add(new FieldInsnNode(Opcodes.PUTFIELD, WIDGET_NAME, name, desc));
    }

    static void putWField(InsnList stack, String name, String desc) {
        loadW(stack);
        stack.add(new InsnNode(Opcodes.SWAP));
        stack.add(new FieldInsnNode(Opcodes.PUTFIELD, WIDGET_NAME, name, desc));
    }

    static void getWField(InsnList stack, String name, String desc) {
        loadW(stack);
        stack.add(new FieldInsnNode(Opcodes.GETFIELD, WIDGET_NAME, name, desc));
    }

    static void invokeW(InsnList stack, String name, String desc) {
        loadW(stack);
        stack.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, WIDGET_NAME, name, desc));
    }

    static FieldInsnNode getLocalWidget(int i) {
        if(i == 0) return new FieldInsnNode(Opcodes.GETSTATIC, "Statics", "WidgetA", WIDGET_DESC);
        if(i == 1) return new FieldInsnNode(Opcodes.GETSTATIC, "Statics", "WidgetB", WIDGET_DESC);
        throw new Error(String.valueOf(i));
    }

    static FieldInsnNode putLocalWidget(int i) {
        if(i == 0) return new FieldInsnNode(Opcodes.PUTSTATIC, "Statics", "WidgetA", WIDGET_DESC);
        if(i == 1) return new FieldInsnNode(Opcodes.PUTSTATIC, "Statics", "WidgetB", WIDGET_DESC);
        throw new Error(String.valueOf(i));
    }

    static int convertJI(int op, boolean bool) { // Convert Jump Instruction
        switch (op) {
            case GOTO:
                return Opcodes.GOTO;
            case IF_ICMPNE:
                return bool ? Opcodes.IFNE : Opcodes.IF_ICMPNE;
            case IF_ICMPEQ:
                return bool ? Opcodes.IFEQ : Opcodes.IF_ICMPEQ;
            case IF_ICMPLT:
                return Opcodes.IF_ICMPLT;
            case IF_ICMPGT:
                return Opcodes.IF_ICMPLT;
            case IF_ICMPLE:
                return Opcodes.IF_ICMPLE;
            case IF_ICMPGE:
                return Opcodes.IF_ICMPGE;
        }
        return -1;
    }

    private static class MarkedLabel extends Label {
        public int mark;
    }
}
