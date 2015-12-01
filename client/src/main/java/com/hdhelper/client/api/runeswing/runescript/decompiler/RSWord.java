package com.hdhelper.client.api.runeswing.runescript.decompiler;

import com.hdhelper.client.api.runeswing.runescript.RSOpcodes;

public enum RSWord {
    //                                               I S    I S
    //                                               POP   PUSH
    ILDC(RSOpcodes.ILDC,RSWord.PILDC,                0,0,   1,0  ), // ()I
    GETVAR(RSOpcodes.GETVAR,RSWord.PILDC,            0,0,   1,0  ), // ()I
    PUTVAR(RSOpcodes.PUTVAR,RSWord.PILDC,            1,0,   0,0  ), // (I)V
    SLDC(RSOpcodes.SLDC,RSWord.PSLDC,                0,0,   0,1  ), // ()S
    GOTO(RSOpcodes.GOTO,RSWord.PILDC,                0,0,   0,0  ), // ()V
    IF_ICMPNE(RSOpcodes.IF_ICMPNE,RSWord.PILDC,      2,0,   0,0  ), // (II)V
    IF_ICMPEQ(RSOpcodes.IF_ICMPEQ,RSWord.PILDC,      2,0,   0,0  ),  // (II)V
    IF_ICMPLT(RSOpcodes.IF_ICMPLT,RSWord.PILDC,      2,0,   0,0  ),  // (II)V
    IF_ICMPGT(RSOpcodes.IF_ICMPGT,RSWord.PILDC,      2,0,   0,0  ),  // (II)V
    RETURN(RSOpcodes.RETURN,0,                       0,0,   0,0  );  // ()V
 /*   GETVARPB(RSOpcodes.GETVARPB,)*/

    /** The words paired int ldc is used **/
    public static final int PILDC    = 1 << 1;
    /** The words paired string ldc is used **/
    public static final int PSLDC    = 1 << 2;
    /** The words has a dynamic-non final-int pop **/
    public static final int DIPOP   = 1 << 3;
    /** The words has a dynamic-non final-string pop **/
    public static final int DSPOP   = 1 << 4;
    /** The words has a dynamic-non final-int push **/
    public static final int DIPUSH  = 1 << 5;
    /** The words has a dynamic-non final-string push **/
    public static final int DSPUSH  = 1 << 6;
    /** The words pushes a boolean onto the stack **/ // push -> bool ? 1 : 0
    public static final int BOOLEAN_RET = 1 << 7;

    RSWord(int opcode, int flags,
           int ipop,  int spop,
           int ipush, int spush) {
       // System.out.println(flags);

    }



    public static final void main(String[] args) {
        int k = new int[] { 6, 6, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, -2, 0, 0, 0, 0, -1, 0, 0, -2, 2, 0, 4, 0, 0, 14, 2, 0, 0, 0, 0, 0, -2, 0, 0, 0, 0, 6, 0, 0, 0, -1, 0, 0, 1, 0, 6, 0, 3, -2, 2, 0, 0, 6, 0, 0, 0, 0, -2, 0, 0, 0, 4, 0, 0, 0, 0, 0, -2, -2, 0, 2, 0, 4, 5, 0, 0, 0, 0, 20, -2, 4, 0, 0, 0, 5, 0, 0, 0, 0, 10, -1, 5, 6, 6, -2, 2, 0, 0, -2, 0, 0, 0, 4, 0, 0, 0, 0, 2, 0, 0, 0, 12, 0, 0, 0, 0, 0, -2, 0, 0, 0, 0, -2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 10, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 3, -2, 0, 0, 0, 6, 6, 0, 0, 0, 4, -2, 2, 0, 28, 0, 0, 5, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 7, 0, 0, 0, -2, 0, 15, 0, 0, 8, 2, 4, 0, 0, 0, 0, 0, 0, 3, 0, -2, 0, 0, 0, 8, 0, 0 }[229];
        System.out.println(k);
    }

    public int getPush(int swi, int[] opcodes, int[] ildc, String[] sldc) {
        return 0;
    }

    public int getPop(int swi, int[] opcodes, int[] ildc, String[] sldc) {
        return 0;
    }
}
