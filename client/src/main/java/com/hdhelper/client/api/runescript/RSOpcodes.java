package com.hdhelper.client.api.runescript;

/**
 * The Instruction set of the RuneScript Virtual Machine
 *
 * @author Brainfree, Dog
 */

/**
 *
 * Version 1 for Build 62
 *
 * SS = String Stack
 * IS = Integer Stack
 *
 * SSE = String Stack Element
 * ISE = Integer Stack Element
 *
 * NOTE: Ops between [2000,3000] are are ops (op-1000) and
 * it just refers to a defined id, and not a local variable
 * 
 */
public interface RSOpcodes {


    // The client notably modulates op_code ranges, and it seems to be
    // show that each module has a specific classification, and is
    // given unique global values to its type


    /** Standard stack manipulation group **/  // Ranges from [0,100]

    int ILDC = 0; // Push a integer value from the constant pool onto the stack
    int GETVAR = 1; // Loads a setting at a defined index. where the setting
    // is identified by a index reference from the constant pool.
    int PUTVAR = 2; // Sets a setting at a defined index, where the setting
    // ^ (I)V
    // is identified by a index reference from the constant pool.
    int SLDC = 3; // Push a String value from the string constant pool onto the string stack
    int GOTO = 6; // Branch to defined instruction
    int IF_ICMPNE = 7; // If two ints are not equal, branch to defined instruction
    int IF_ICMPEQ = 8; // If two ints are equal, branch to defined instruction
    int IF_ICMPLT = 9; // If the top stack item is less than succeeding int value, //TODO Double check (might have them backwards).
    // branch to defined instruction
    int IF_ICMPGT = 10; // If the top stack item is greater than succeeding int value,
    // branch to defined instruction

    int RETURN = 21; // Exits the frame, continuing from the caller function (if it was invoked)

    int GETVARPB = 25; // Loads a varbit setting at a defined index, where the varbit
    // id is identified by a index reference from the constant pool.
    int PUTVARPB = 27; // Puts a varbit setting at a defined index, where the varbit
    // id is identified by a index reference from the constant pool.
    // ^ (I)V
    int IF_ICMPLE = 31; // If the top stack item is less than or equal to the succeeding int value,
    // branch to defined instruction
    int IF_ICMPGE = 32; // If the top stack item is greater than or equal to the succeeding int value,
    // branch to defined instruction

    int ILOAD = 33; // Loads a local int variable from a defined index within the constant pool
    int ISTORE = 34; // Stores a int value into local variable at a defined index within the constant pool

    int SLOAD = 35;  // Loads a local string variable from a defined index within the constant pool
    int SSTORE = 36;  // Stores a string value into a local variable at a defined index within the constant pool

    int SAPPX = 37; // Appends the top X strings from the string stack into a single string element.
    int IPOP = 38; // Pops a top stack element from the int stack
    int SPOP = 39; // Pops a top stack element from the string stack

    int INVOKE = 40; // Invokes a another client script

    int GETSTATICI = 42;  // Loads a static int value
    int PUTSTATICI = 43;  // Stores a static int value
    
    int IACLR = 44; // Clears/'Nullifies' the int array stack with 'null/empty' element values,
    // and must be performed to a index of a row greater than a value desired for loading.

    int IALOAD = 45; // Loads a element from the array stack. Note the array element
    // trying to be accessed must have been cleared/labeled to at least that index
    // to be accessed...

    int IASTORE = 46; // Stores a integer values into the array stack.


    int GETSTATICS = 47; // Loads a static string value
    int PUTSTATICS = 48; // Stores a static string value


    //---------------------------------------------------------------------------------------

    /** Widget Load Commands **/ // [100,1000)

    int NEWW = 100; // 'Grand-Widget' Creates a new widget as a child of another. Updates the refered value.
    // ^ 3 Args: ParentID, WidgetType, WidgetDest (which index wihin the parent to put it)
        // Note:   mkWidget(A,B,C)  ,<- GRAND
    int DELW = 101; // Deletes/Nullifies the refeed widget from its parent
    // ^ 0 Args

    int DELAC = 102; //Delets all sub-components of a widget.
    // ^ 1 Args: widgetID

    // -------------  Blocks into 200+ range

    int GET_WIDGET = 200; // Loads a widget onto the pointer, and pushes if it was valie onto the stack
    // ^ 2 args: ParentID, Child Index   ->  pushes true/false if its valid/non-null (parent !+ null && child index != -1)
    // NOTE: These functions pull a 3-depth widget 'grand widget' -> getWidget(A,B,C) where AB is hashed (param) and C is the inex (param)

    //----------------------------------------------------------------------------------------------



    /**  Configures major appearance values **/ // [1000,1100)

    int PUT_WPOS = 1000; // Sets the position of the referred widget
    int PUT_WDIM = 1001; // Sets the dimensions of the referred widget
    int PUT_WVIS = 1003; // Sets the visibility of the referred widget


    //-------------------------------------------------------------------------------------------


    /** Configures minor appearance values **/   // [1100,1200)

    int PUT_WINSETS = 1100; // Sets the horizontal and vertical insets
    int PUT_WTXTCLR = 1101; // Sets the text color of the referred widget
    int PUT_1102 = 1102; //TODO
    int PUT_WALPHA = 1103; // Sets the alpha value for the widgets text
    int PUT_1104 = 1104; //TODO
    int PUT_WTEXTURE = 1105; // Sets the texture id
    int PUT_WSPRIROT = 1106; // Sets the widgets sprite rotation angle
    int PUT_1107 = 1107; //TODO
    int PUT_WMODEL1 = 1108; // sets the widgets model id, and model type to 1
    int PUT_WMODEL = 1109; // Sets the rotation and misc model arguments of the widget //TODO more detail 6 args
    int PUT_1110 = 1110; //TODO
    int PUT_1111 = 1111; //TODO
    int PUT_WTXT = 1112; // sets the text of a widget
    int PUT_WFONT = 1113; // sets the font id of widget
    int PUT_1114 = 1114; //TODO
    int PUT_1115 = 1115; //TODO
    int PUT_WBORDERTHK = 1116; // sets the border thickness of a widget
    int PUT_WSHADOWCLR = 1117; // sets the shadow color of a widget
    int PUT_WVIRFLP = 1118; // sets a widgets vertically flipped flag
    int PUT_WHORFLP = 1119; // sets a widgets horizontally flipped flag
    int PUT_WSCROLDIM = 1120; // sets the dimensions of a widgets scroll bar
    int PUT_PROCW = 1121; // Sets the referred widget as the 'processing widget' and notifies the server

    //--------------------------------------------------------------------------------------------------

    /** **/ // [1200,1300)

    int PUT_WITEM = 1200; // Sets the item id and quantity of a widget. Also resets the model configurations
    int PUT_MODEL2 = 1201; // Sets the model id of a widget, and the type to 2 (possibly item)
    int PUT_MODEL3 = 1202; // Sets the model id of a widget, and the type to 2 (possibly item)
    int PUT_MOD1205 = 1205; //TODO
    int PUT_MOD1212 = 1212; //TODO

    //--------------------------------------------------------------------------------------------------

    /** Widget Setters **/ // [1300,1400)

    int PUT_WACT = 1300; // Sets the action of a widget at a specific index
    int PUT_WPARENT = 1301; // Sets the parent of a widget
    int PUT_1302 = 1302; //TODO
    int PUT_1303 = 1303;// TODO seemingly drag minimal distance
    int PUT_1304 = 1304;
    int PUT_1305 = 1305;
    int PUT_WTITL = 1306; // sets the title of a widget
    int DEL_WACTS = 1307; // deleted/nullifies the actions of a widget

    //--------------------------------------------------------------------------------------------------

    /** Mouse Event Script Setters **/ // [1400,1500)

    // Sets the script that is to be ran when a specific mouse event is dispatched onto a widget
    // Note: After the stack word is executed, the widgets 'enable_events' flag is set to true
    // Note: The arguments for the event script are loaded by a each character of a string which
    // points to which stack to pull the object value. 'Y' == Integer Stack , 's' == String Stack
    // TODO  look into this more...
    //TODO finish these... and check (some might be wrong)

    int PUTES_MOUSE_PRESSED = 1400;
    int PUTES_MOUSE_DRAGGED_OVER = 1401; //TODO for drag and drop or general?
    int PUTES_MOUSE_RELEASED = 1402;
    int PUTES_MOUSE_ENTERED = 1403;
    int PUTES_MOUSE_EXITED = 1404;
    int PUTES_1405 = 1405;
    int PUTES_1406 = 1406;
    int PUTES_1407 = 1407;
    int PUTES_1408 = 1408;
    int PUTES_1409 = 1409;
    int PUTES_1410 = 1410;
    int PUTES_MOUSE_DRAGGED = 1411;
    int PUTES_MOUSE_MOVED = 1412;
    int PUTES_1413 = 1413;
    int PUTES_1414 = 1414;
    int PUTES_1415 = 1415;
    int PUTES_1416 = 1416;
    int PUTES_1417 = 1417;
    int PUTES_1418 = 1418;
    int PUTES_1419 = 1419;
    int PUTES_1420 = 1420;
    int PUTES_1421 = 1421;
    int PUTES_1422 = 1422;
    int PUTES_1423 = 1423;
    int PUTES_1424 = 1424;

    //-----------------------------------------------------------------------

    /** Widget getters **/ // [1400, 1500)

    int GET_WX = 1500; // gets the x position of a widget
    int GET_WY = 1501; // gets the y position of a widget
    int GET_WW = 1502; // gets the width of a widget
    int GET_WH = 1503; // gets the width of a widget
    int IS_WVIS = 1504; // gets the visibly flag of a widget
    int GET_WPAR = 1505; // gets the parent id of a widget

    //-----------------------------------------------------------------------

    /** **/ // [1600,1700)

    int GET_WINSETX = 1600; // gets position value of a widgets horizontal scrollbar
    int GET_WINSETY = 1601;  // gets position value of a widgets horizontal scrollbar
    int GET_WTXT = 1602; // gets the text value of a widget
    int GET_WSCROLW = 1603; // gets the width of a widgets scroll bar
    int GET_WSCROLH = 1604; // gets the height of a widgets scroll bar
    int GET_WZOOM = 1605; // gets the zoom scale of a widgets model
    int GET_WROTX = 1606; // gets a widgets model rotation within the X-plane
    int GET_WROTY = 1607; // gets a widgets model rotation within the Y-plane
    int GET_WROTZ = 1608; // gets a widgets model rotation within the Z-plane

    //----------------------------------------------------------------------------------------

    /** Table Commands? **/ // [1700,1800)

    int GET_WITEM = 1700; // gets the item id of a widget
    int GET_WIAMT = 1701; // gets the quantity of the item within a widget.
    int GET_WIDX = 1702; // gets a widgets index

    //-----------------------------------------------------------------------------------------

    /** **/ // [1800,1900)

    int GET_WSPELTRGT = 1800; // gets the accepted spell targets of a widget
    int GET_WACTION   = 1801; // gets a action of a widget at a specific index
    int GET_WNAME = 1802; // gets the name of a widget


    //------------------------------------------------------------------------------------------

    /** Packet Sending **/ // [3100,3200)

    int SEND_3100 = 3100; //TODO sends packets to the server...
    int SET_ANIM = 3101; // sets the local players animation
    int CLOSE_MAJOR = 3103;
    int SEND_3104 = 3104;
    int SEND_3105 = 3105;
    int SEND_3106 = 3106;
    int SEND_3107 = 3107;
    int SEND_3108 = 3108;
    int SEND_3109 = 3109;
    int PUTSTATIC_3110 = 3110; // Seemingly puts local boolean (as int) into static frame
    int IS_3111 = 3111; // something with buffers
    int PUT_3112 = 3112;// something with buffers
    int OPEN_URL = 3113; // opens a url //TODO look into this more...
    int SEND_3115 = 3115; //TODO

    //-----------------------------------------------------------------------------------------

    /** Audio Commands **/ // [3200,3300)

    int SET_AUDIO3200 = 3200; // TODO some sort of audio configurations....
    int SET_AUDIO3201 = 3201;
    int SET_AUDIO3202 = 3202;

    //-------------------------------------------------------------------------------------------

    /** Environment Commands? **/ // [3300,3400)

    int GET_CCYCLE = 3300; // gets the clients current cycle
    int GET_CITEMID = 3301; // gets the item (id) within a 'item container'
    int GET_CITEMAMT = 3302; // gets the amount of a item within a 'item container'
    int GET_CAMT = 3303; // gets the amount of items (id-specified) within a 'item container'
    int REF2VARID = 3304; // gets the varp id defined within a 'cfg object' (within the cache) from a defined id //TODO research more...
    int GET_CLVL = 3305; // gets thc current level of a skill
    int GET_RLVL = 3306; // gets the real level of a skill
    int GET_XP = 3307; // gets the amount of xp of a skill
    int GET_LOC = 3308; // gets the global location hash code of the local player
    int LOC2X = 3309; // extracts the x position within a location hash
    int LOC2Y = 3310; // extracts the y position within a location hash
    int LOC2Z = 3311; // extracts the z position within a location hash
    int IS_MEMWORLD = 3312; // determines if the current world is members

    //TODO these add 32768 to the provided value... are these only items?? Are 'item containers' a more generic collection..?
    int GET_CITEMID2 = 3313; // gets the item (id) within a 'item container'
    int GET_CITEMAMT2 = 3314; // gets the amount of a item within a 'item container'
    int GET_CAMT2 = 3315; // gets the amount of items within a 'item container'


    int GET_RIGHTS = 3316; // tells the 'rights' of the local player (none/admin/moderator)
    int GET_3317 = 3317; //TODO
    int GET_WORLD = 3318; // gets the id of the this world
    int GET_ENERGY = 3321; // gets the run energy of the local player
    int GET_WEIGHT = 3322; // gets the weight of the local player
    int IS_MALE = 3323; //TODO check...
    int GET_WORLDINFO = 3324; // gets the current server flags

    //---------------------------------------------------------------------------------
    /**  **/  // [3400,3500)
    int GET_SCACHEVAL = 3400; //String return cache value
    int GET_GCACHEVAL = 3408; //Generic return type which looks up a entry within a table serialized
    // within the cache with a given key value. Can return int or string.

    //NOTE: No 3500 block

    //----------------------------------------------------------------------------------
    /** Friend and Clan  **/  // [3600,3700)

    int GET_NUMFRIENDS = 3600; // gets the total numbers of friends
    int GET_FRIENDNAME = 3601; // gets a friends current and previous name //TODO Object return
    int GET_FRIENDWORLD = 3602; // gets the world a friend is currently on
    int GET_FRIENDIDX  = 3603; // gets the player id of a friend //TODO check, not to sure about the field
    int SEND_3604 = 3604; //TODO some packet...
    int ADD_FRIEND = 3605; // adds a friend
    int REMOVE_FRIEND = 3606; // removes a friend
    int ADD_IGNORED = 3607; // adds a ignored player
    int REMOVE_IGNORED = 3608; // removes a ignored player
    int HAS_PREVNAMEF = 3609; // tells if a player friend has a previous name... //TODO double check
    int GET_CLANOWNER = 3611; // the clan your currently in
    int GET_CLANSIZE = 3612;  // the number of members in the clan your in
    int GET_CMEMNAME = 3613; // gets the name of a clan member
    int GET_CMEMWORLD = 3614; // gets the world of a clan member
    int GET_CMEMRANK = 3615; // gets the rank of a clan chat member
    int GET_3616 = 3616; // TODO single field probs clan related...
    int SEND_3617 = 3617; //TODO some packet...
    int GET_LOCALCRANK = 3618; // gets the rank of the local player within for the join clan chat
    int SEND_3619 = 3619; //TODO packet, join clan chat..?
    int SEND_3620 = 3620; //TODO packet, leave clan chat..?
    int GET_NUMIGNORED = 3621; // gets the number of ignored players
    int GET_IGNOREDNAME = 3622; // gets the current and previous name of a ignored player /TODO Object return
    int HAS_PREVNAMEI = 3623; // tells if a ignored player has a previous name... //TODO double check
    int IS_IGNOREME = 3624; // tells if a ignored player is the local player...
    int GET_CLANCHAN = 3625; // gets the name clan chat channel you're currently in, or previous were in.

    //NOTE: no 3700,3800 blocks
    //------------------------------------------------------------------------------------------------------------
    // [3900,4000)
    /** Trading Post / Grand Exchange **/





    //------------------------------------------------------------------------------------------------------------


    //
    /** Logical Instructions **/  // [4100,4200]

    int IADD = 4000; // Adds two ints  A + B
    int ISUB = 4001; // Subtracts two ints A - B
    int IMUL = 4002; // Multiplies two ints A * B
    int IDIV = 4003; // Divides two ints A / B if B != 0

    int IRNDF = 4004;
    // ^ Generates a random integer value by multiplied Math.random() with the top stack element.
    // This ensures that the generated value is less than the magnitude of provided value,
    // by flooring/rounding down
    int IRNDC = 4005;
    // ^ Generates a random integer value by multiplied Math.random() with the top stack element.
    // This ensures that the generated value is less than or equal the magnitude of provided value,
    // by rounding up.
    // NOTE: Being the means of rounding up involves adding 1 to the existing value,
    // it would suggest that the instruction is meant for values >= 0

    int U4006 = 4006; //TODO not to sure exactly what these are
    int U4007 = 4007;

    int SETBIT = 4008; // Set a bit true/flagged at a specific index ( Sets the bit to one )
    int CLRBIT = 4009; // Clears a bit at a specific index ( Sets the bit to zero )
    int TESTBIT = 4010; // Pushes the value of a bit at a specific index onto the stack, 0 == false, 1 == true

    int IREM = 4011; // Pushes the modulus value of two ints onto the stack    A % B
    int POW  = 4012; // Pushes the raised value of a integer onto the stack    A ^ B
    int NROOT = 4013; // Pushes the nth root of a integer into the stack by performing a factorial power. A ^ (1/B)

    int IAND = 4014; // Perform a bitwise and on two ints A & B
    int IOR  = 4015; // Perform a bitwise or on two ints  A | B

    // ------------------------------------------------------------------------

    /** String Logic **/ // [4100,4200)

    int APPI = 4100;  // Appends/Concats a integer onto a string
    int APPS = 4101;  // Appends/Concets a string onto a string
    int APPIR = 4102; // Appends/Concats a integer string value with a radix of 10 onto a string
    int LCS = 4103; // Converts a string into its lower-case form.

    int TIME2DATE = 4104; // Pushes the provided time (in days..?) value as [DAY-MONTH-YEAR] onto the stack //TODO what unit?
    int ST_GEN = 4105;
    // Ternary condition with string value branches, where the condition is a boolean comparison
    // of IFEQ with the the boolean value GENDER of the local player (ifMale ? A : B).
    // Like all Ternary based instructions, both possible branches are poped before the comparison, and
    // the succeeding value is pushed back into the SS (Top == true_branch). In this case,
    // if the local player was a male, then the top SSE will be pushed back into the SS.

    int I2S = 4106; // Pushed the string value of a integer onto the stack
    int CMPS = 4107; // Compares two strings and pushes the comparison (-1,0,1) into the stack.

    int U4108 = 4108;//TODO   maybe text effects or dimentions?
    int U4109 = 4109;

    int ST_C1 = 4110; //
    // Ternary condition with string value branches, where the condition is a integer comparison
    // of IFNEQ (!= 1) with the integer valur of the top ISE (V != 1 ? A : B). Like all Ternary based instructions,
    // both possible branches are poped before the comparison, and
    // the succeeding value is pushed back into the stack (Top == true_branch). In this case,
    // if the top ISE is != 1, then the top SSE will be pushed back into the SS, otherwise
    // the predeceasing SSE is pushed back into the SS.

    int TLC = 4111; // Converts literal comparisons into tags within a string //TODO research more, is there a special parser?
                      //  '<'  ->  <lt>    '>'  ->  <gt>

    int APPC = 4112; // Appends/concats a character (integer based from IS) onto a string.

    int IS_4113 = 4113; //TODO not to sure what it checks, but if makes sure a specified id is valid
    int IS_LWC = 4114; // Checks if a character (integer-based from top ISE) is lower-case,
    int IS_ALP = 4115; // Checks if a character (integer-based from top ISE) is alphabetic.
    int IS_DGT = 4116; // Checks if a character (integer-based from top ISE) is a digit

    int SLENGTH = 4117;// Gets the length of a string
    int SUBS = 4118;   // Extracts the substring from string
    int DEL_LC = 4119; // Removes all literal comparisons from a string
    int IDXOFC = 4120; // Pushes the index of defined character (integer-based from top ISE) within a string
    int IDXOFS = 4121; // Pushes the index of a defined string from a defined index onto the IS

    //-----------------------------------------------------------------------------------------------------

    /** Item Logic **/  // [4200, 4300)

    int GET_INAME = 4200; // gets the name of an item with a specified id
    int GET_ITACT = 4201; // gets a table action of an item
    int GET_IGACT = 4202; // gets a ground action of an item
    int GET_IVAL  = 4203; // gets the value of an item
    int IS_ISTKBL = 4204; // checks if a item is stackable
    int NOTE2ITEM = 4205; // Swaps out the noted-item id to the non-noted id of the item, if exists
    int ITEM2NOTE = 4206; // Swaps out the non-noted item id to the noted id of the item, if exists
    int IS_MEMBERS = 4207; // Checks if a item can only be used by members

    //TODO finish
    //------------------------------------------------------------------------------------------------------

    // [3900,4000)
    /** Grand Exchange **/

    int IS_EXCHANGE_OFFER_FINISHED = 3903; //can be cancelled or fully purchased (status & 7) == 5
    int GET_EXCHANGE_OFFER_ITEM_ID = 3904;
    int GET_EXCHANGE_OFFER_ITEM_PRICE = 3905;
    int GET_EXCHANGE_OFFER_ITEM_QUANTITY = 3906;
    int GET_EXCHANGE_OFFER_TRANSFERRED = 3907;

    int IS_EXCHANGE_OFFER_SLOT_USED = 3910;  //whether the offer slot has an item in it
    int IS_EXCHANGE_OFFER_PENDING = 3911;    //(status & 7) == 2
    int IS_EXCHANGE_OFFER_PURCHASED = 3912;  //whether the item is fully purchased or not
    int IS_EXCHANGE_OFFER_PENIS = 3913; //(status & 7) = 1 - this is when the offer just gets submitted

    /** **/ // [5000,5100)

    //TODO
    int GET_5000 = 5000; // TODO same field as 5001
    int SEND_5001 = 5001; // TODO
    int SEND_5002 = 5002; // TODO
    int GETMSGM = 5003; // Loads a message onto the stack (as integer and string components) from a specific MessageContainer by ID
    int GETMSGW = 5004; // Loads a message onto the stack (as integer and string components) from the world MessageContainer by ID
    int GET_5005 = 5005; //TODO
    int SEND_5008 = 5008; // TODO
    int SEND_5009 = 5009; //TODO Some packet
    int LOCALNAME = 5015; // Gets the name of the local player. "" if null player or name
    int GET_5016 = 5016; // Gets a unknown static int field
    int NUM_MSGS = 5017; // Gets the total number of MessageNodes within a MessageContainer specified by its id (index within global map)
    int PREV_MSG = 5018; // Gets the previous MessageNode id from the world-chat MessageContainer hash table. (-1 if none or EOF)
    int NEXT_MSG = 5019; // Gets the next MessageNode id from the world-chat MessageContainers table. (-1 if none of EOF) // TODO what container..?
    int PROC_CMD = 5020; // Handles a client command. ::[toogleroof,fpson,fpsoff,gc,clientdrop,errortest]
    int PUT_5021 = 5021; //TODO
    int GET_5022 = 5022; // TODO same field as 5021

    //------------------------------------------------------------------------------------------------------

    /** **/ // [5500,5600)
    int PUT_5504 = 5504; //TODO








}
