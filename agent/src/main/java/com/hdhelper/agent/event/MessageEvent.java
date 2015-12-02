package com.hdhelper.agent.event;

import com.hdhelper.agent.services.RSMessage;

public class MessageEvent extends RSEvent {


    /**
     * The first number in the range of ids used for message events.
     */
    public static final int  MESSAGE_FIRST               = 1001;

    /**
     * The last number in the range of ids used for message events.
     */
    public static final int MESSAGE_LAST                 = 1001;

    /**
     * This event id indicates that a message has been received by the client.
     */
    public static final int MESSAGE_RECEIVED             = MESSAGE_FIRST;

    RSMessage msg;

    public MessageEvent(RSMessage msg, int type, int cycle) {
        super(null, type, cycle);
        this.msg = msg;
    }



    public RSMessage getMessage() {
        return msg;
    }

    public MessageType getType() {
        return MessageType.get(getMessage().getType());
    }

    public enum MessageType {

        /**
         * A severe game message
         */
        SEVERE(0),
        /**
         * A message sent by a player
         */
        PLAYER(2),
        /**
         * A private message
         */
        PRIVATE_MESSAGE(3),
        /**
         * A trade received message
         */
        TRADE_RECEIVED(4),
        /**
         * ??
         */
        PRIVATE_INFO(5),
        /**
         * A private message sent by you
         */
        PRIVATE_SENT(6),
        /**
         * A clanchat message
         */
        CLAN_CHAT(9),
        /**
         * A server message
         */
        SERVER(11),
        /**
         * A message which is a trade request sent by you
         */
        TRADE_SENT(12),
        /**
         * A message which is returned from examining an Npc
         */
        EXAMINE_NPC(28),
        /**
         * A message which is returned from examining an Object
         */
        EXAMINE_OBJECT(29),
        /**
         * A message returned from any other action
         */
        ACTION(109);

        private final int typeId;

        MessageType(final int typeId) {
            this.typeId = typeId;
        }

        public static MessageType get(final int type) {
            for (final MessageType mt : MessageType.values()) {
                if (mt.getId() == type) return mt;
            }
            return null;
        }

        public final int getId() {
            return typeId;
        }

    }

}
