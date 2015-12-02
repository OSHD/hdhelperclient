package com.hdhelper.client.api.action.tree;


import com.hdhelper.agent.BasicAction;
import com.hdhelper.agent.services.RSClient;
import com.hdhelper.client.Main;
import com.hdhelper.client.api.action.ActionFilter;
import com.hdhelper.client.api.action.ActionTypes;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.hdhelper.client.api.action.ActionTypes.*;


/**
 * @author Brainfree
 * @created 7/9/2014
 */

/**
 * An Action is an implicit modular object in which identifies
 * and/or notifies nearly all interaction that occur within the client.
 *
 * Action is the underlying module that defines the action.
 *
 * It should be noted that Actions do not directly exists within the client,
 * but instead are an artificial adapter. Actions usually contain detail
 * information about the event, in which
 * it'll use to upload to the server upon occurrence, but those that are
 * purely notifiers, simply have no use for the trailered arguments. For
 * clarification a 'notifying event' does not directly notify, but should
 * be thought of as a empty event.
 *
 * Actions provide a much more disciplined and accurate summary of what's
 * being targeted, and the results of interaction would be, compared to plain
 * text strings. Not all interaction involves actions, such as the navigating
 * the MiniMap, or drag-and-drop. Some actions are solly notifiers, such as:
 * {WALK_HERE, USE, CANCEL}
 *
 *
 * Actions Filter are blind to unused abutments
 *
 * Most actions store information about what the action
 // interacted with, but not all.
 */
//
//
public abstract class Action implements ActionFilter { // An action has the ability to identify itself

    public final int opcode;

    public final int arg0;
    public final int arg1;
    public final int arg2;

    final RSClient client;

    public static final Map<Integer,String> OP2NAME;

    static {

        Field[] opz = ActionTypes.class.getDeclaredFields();
        HashMap<Integer,String> map0
                = new HashMap<Integer, String>(opz.length);
        for (Field op0 : opz) {
            op0.setAccessible(true);
            try {
                final int op_code = op0.getInt(null);
                final String name = op0.getName();
                map0.put(op_code, name);
            } catch (IllegalAccessException e) {
            }
        }

        OP2NAME = Collections.unmodifiableMap(map0);

    }

    public Action(
            int opcode,
            int arg0,
            int arg1,
            int arg2
    ) {
        this.opcode = pruneOpcode(opcode);
        this.arg0 = arg0;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.client = Main.client;
    }

    public Action(Action src) {
        this(src.opcode,src.arg0,src.arg1,src.arg2);
    }

    public static final int ARG0 = 1 << 1;
    public static final int ARG1 = 1 << 2;
    public static final int ARG2 = 1 << 3;

    public static final int SIG_ALL = ARG0 | ARG1 | ARG2;

    // Determines the used argument spaces.
    // Helpful in determining the structure of an action.
    public abstract int getSignificantArgs();

    //Not all actions require pruning
    public static int pruneOpcode(int op) {
        return op >= 2000 ? op - 2000 : op;
    }

    ///////////////////////////////////////////////////////////////////////////////

    /** Common No-Arg actions **/

    public boolean isCancel() {
        return opcode == CANCEL;
    }

    public boolean isWalkHere() {
        return opcode == WALK_HERE;
    }

    public boolean isUseItem() {
        return opcode == USE_ITEM;
    }

    ///////////////////////////////////////////////////////////////////////////////

    public final int getOpcode() {
        return opcode;
    }

    public final int getArg0() {
        return arg0;
    }

    public final int getArg1() {
        return arg1;
    }

    public final int getArg2() {
        return arg2;
    }

    /**
     * Validates the arguments of this action. Since actions
     * may be user produced, validation is recommended to ensure
     * expected functionality. Validation also allows for an ability
     * to verify-theorized-client-produced actions augments, in the
     * case that any of the actions are updated(client-side) in the future.
     * The verified properties are equal to what was captured when the
     * event was created, only validating significant arguments.
     *
     * @return True if the value of the arguments, if are to serve any
     * purpose for this action, are within the range of expected values.
     * False otherwise.
     */
    public boolean isValid() { //TODO Implement to the rest
        return true;
    }

    /**
     * Returns the defined name of an opcode. This function looks up
     * the paired name from the hashmap {@link Action#OP2NAME}.
     * If the returned value is null, then the action is unknown
     * or not defined.
     *
     * @param opcode The id of the action to lookup
     * @return The defined name of the opcode. Null if
     * the opcode is unknown or undefined.
     */
    public static String nameOf(int opcode) {
        return OP2NAME.get(pruneOpcode(opcode));
    }

    public static Action valueOf(BasicAction ba) {
        return valueOf(
                ba.getOpcode(),
                ba.getArg0(),
                ba.getArg1(),
                ba.getArg2()
        );
    }

    public static Action valueOf(
            int opcode,
            final int arg0,
            final int arg1,
            final int arg2
    ) {

        /** Prune opcode **/
        opcode = pruneOpcode(opcode);

        switch ( opcode ) {

            case CANCEL:
                return new CancelAction();
            case WALK_HERE:
                return new WalkHereAction();

            ///////////////////////////////////////////////////////////////////////////

            case OBJECT_ACTION_0:
            case OBJECT_ACTION_1:
            case OBJECT_ACTION_2:
            case OBJECT_ACTION_3:
            case OBJECT_ACTION_4:
                return new ObjectAction(opcode,arg0,arg1,arg2);

            ///////////////////////////////////////////////////////////////////////////

            case GROUND_ITEM_ACTION_0:
            case GROUND_ITEM_ACTION_1:
            case GROUND_ITEM_ACTION_2:
            case GROUND_ITEM_ACTION_3:
            case GROUND_ITEM_ACTION_4:
                return new GroundItemAction(opcode,arg0,arg1,arg2);

            ///////////////////////////////////////////////////////////////////////////

            case NPC_ACTION_0:
            case NPC_ACTION_1:
            case NPC_ACTION_2:
            case NPC_ACTION_3:
            case NPC_ACTION_4:
                return new NpcAction(opcode,arg0);

            ///////////////////////////////////////////////////////////////////////////

            case PLAYER_ACTION_0:
            case PLAYER_ACTION_1:
            case PLAYER_ACTION_2:
            case PLAYER_ACTION_3:
            case PLAYER_ACTION_4:
            case PLAYER_ACTION_5:
            case PLAYER_ACTION_6:
            case PLAYER_ACTION_7:
                return new PlayerAction(opcode,arg0);

            ///////////////////////////////////////////////////////////////////////////

            case BUTTON_INPUT:
            case BUTTON_SPELL:
            case BUTTON_CLOSE:
            case BUTTON_VARFLIP:
            case BUTTON_DIALOG:
                return ButtonAction.valueOf(opcode,arg2);

            case ITEM_ACTION_0:
            case ITEM_ACTION_1:
            case ITEM_ACTION_2:
            case ITEM_ACTION_3:
            case ITEM_ACTION_4:
                return new TableItemAction(opcode,arg0,arg1,arg2);


            case TABLE_ACTION_0:
            case TABLE_ACTION_1:
            case TABLE_ACTION_2:
            case TABLE_ACTION_3:
            case TABLE_ACTION_4:
                return new TableAction(opcode,arg0,arg1,arg2);

            ///////////////////////////////////////////////////////////////////////////

            case WIDGET_ACTION:
            case WIDGET_ACTION_2:
                return new WidgetAction(opcode,arg0,arg1,arg2);

            ///////////////////////////////////////////////////////////////////////////

            case EXAMINE_OBJECT:
            case EXAMINE_NPC:
            case EXAMINE_GROUND_ITEM:
                return new ExamineEntityAction(opcode,arg0,arg1,arg2);

            case EXAMINE_ITEM:
                return new ExamineItemAction(arg0,arg1,arg2);

            ///////////////////////////////////////////////////////////////////////////

            case ITEM_ON_OBJECT:
            case ITEM_ON_NPC:
            case ITEM_ON_PLAYER:
            case ITEM_ON_GROUND_ITEM:
                return new ItemOnEntityAction(opcode,arg0,arg1,arg2);

            case ITEM_ON_ITEM:
                return new ItemOnItemAction(arg0,arg1,arg2);

            ///////////////////////////////////////////////////////////////////////////

            case SPELL_ON_OBJECT:
            case SPELL_ON_NPC:
            case SPELL_ON_PLAYER:
            case SPELL_ON_GROUND_ITEM:
                return new SpellOnEntityAction(opcode,arg0,arg1,arg2);

            case SPELL_ON_ITEM:
                return new SpellOnItemAction(arg0,arg1,arg2);

            case SPELL_ON_WIDGET:
                return new SpellOnWidgetAction(arg1,arg2);

            case USE_ITEM:
                return new UseItemAction(arg0,arg1,arg2);

            ///////////////////////////////////////////////////////////////////////////


            default: {
                final String formatted = format(opcode, arg0, arg1, arg2);
                System.err.println("WARNING: Unknown action: " + formatted); //TODO debug logger
                return new UnknownAction(opcode,arg0,arg1,arg2);
            }

        }

    }

    public static String format(
            final int opcode,
            final int arg0,
            final int arg1,
            final int arg2
    ) {
        return "Action<" + nameOf(opcode) + ">(id=" + opcode + ",args=[ " + arg0 + " | " + arg1 + " | " + arg2 + " ])";
    }

    public boolean accepts(ActionFilter filter) {
        return filter.accepts(opcode,arg0,arg1,arg2);
    }

    @Override
    public boolean accepts(int opcode, int arg0, int arg1, int arg2) {
        if( this.opcode != opcode ) return false;
        final int sig = getSignificantArgs();
        if( ((sig & ARG0) == ARG0) && this.arg0 != arg0 ) return false;
        if( ((sig & ARG1) == ARG1) && this.arg1 != arg1 ) return false;
        if( ((sig & ARG2) == ARG2) && this.arg2 != arg2 ) return false;
        return true;
    }

    @Override
    public String toString() {
        return format( opcode, arg0, arg1, arg2 );
    }

}

class UnknownAction extends Action {
    UnknownAction(int opcode, int arg0, int arg1, int arg2) {
        super( opcode, arg0, arg1, arg2 );
    }

    @Override
    public int getSignificantArgs() {
        return SIG_ALL;
    }
}
