package com.hdhelper.client.api.action.tree;

import com.hdhelper.agent.services.RSWidget;
import com.hdhelper.client.api.action.ActionTypes;
import com.hdhelper.client.api.runeswing.Widget;

import static com.hdhelper.client.api.action.ActionTypes.*;

public abstract class ButtonAction extends Action {

    public ButtonAction(int opcode, int WUID) {
        super(opcode,0,0,WUID);
    }

    public final int getSignificantArgs() {
        return ARG2;
    }

    // the WUID is known to be the arg2
    public static ButtonAction valueOf(int opcode, int WUID) {
        switch (opcode) {
            case BUTTON_INPUT:
                return new InputButtonAction(WUID);
            case BUTTON_SPELL:
                return new SpellButtonAction(WUID);
            case BUTTON_CLOSE:
                return new CloseButtonAction(WUID);
            case BUTTON_VARFLIP:
                return new VarFlipButtonAction(WUID);
            case BUTTON_DIALOG:
                return new DialogButtonAction(WUID);
        }
        return null;
    }

    public static int getButtonForOpcode(final int opcode) {
        switch (Action.pruneOpcode(opcode)) {
            case ActionTypes.BUTTON_INPUT:
                return Widget.BUTTON_INPUT;
            case ActionTypes.BUTTON_SPELL:
                return Widget.BUTTON_SPELL;
            case ActionTypes.BUTTON_CLOSE:
                return Widget.BUTTON_CLOSE;
            case ActionTypes.BUTTON_VARFLIP:
                return Widget.BUTTON_VARFLIP;
            case ActionTypes.BUTTON_VARSET:
                return Widget.BUTTON_VARSET;
            case ActionTypes.BUTTON_DIALOG:
                return Widget.BUTTON_DIALOG;
        }
        return -1;
    }

    public int getButtonUID() {
        return arg2;
    }

    public int getButtonParent() {
        return Widget.getParentIndex(getButtonUID());
    }

    public int getButtonChild() {
        return Widget.getChildIndex(getButtonUID());
    }

    public RSWidget get0() {
        final int UID = getButtonUID();
        final int parent0 = Widget.getParentIndex(UID);
        final int child0  = Widget.getChildIndex(UID);
        return client.getWidgets()[parent0][child0];
    }

    @Override
    public final boolean accepts(int opcode, int arg0, int arg1, int arg2) {
        // Only arg2 (WUID) is significant
        return this.opcode == opcode && this.arg2 == arg2;
    }

    @Override
    public String toString() {
        int p = getButtonParent();
        int c = getButtonChild();
        return p + "#" + c + ":" + get0();
    }

}
