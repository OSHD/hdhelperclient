package com.hdhelper.agent;

import java.awt.*;

/**
 * Created by Jamie on 10/24/2015.
 */
public class BrainStorm {

    /**
     *
     *
     * class SpriteProxy extends [RSSprite] { //Package Private
     *
     *
     *     @Override
     *     public int[] getPixels() {
     *
     *         return peer.getPixels();
     *
     *     }
     *
     * }
     *
     *
     *
     * public static RSGraphics2D getEngineGraphics() {
     *
     *
     *
     *     The GameEngine should notify this object of any important sets
     *     of the properties.
     *
     *     EX: When the engine sets the width, this object should be notified,
     *     and be set. This would be using this object would not be int sync.
     *     Also when properties of this objects are changed by the user, it should
     *     be forward to the client.
     *
     *
     *
     *      // The game will reference to our singleton EngineGraphics object
     *      to get specs (width/pixels/height/clip)
     *     public final class EngineGraphics extends RSGraphics2D {
     *
     *         @Override
     *         public synchronized void setPixels(...);
     *         @Override
     *         public synchronized void setWidth(...);
     *         @Overide
     *         public synchronized void setHeight(...);
     *         @Overide
     *         public synchronized int getWidth(...);
     *
     *     }
     *
     *     RSGraphics2D g = Engine.getGraphics();
     *
     *
     *
     * }
     *
     *
     *
     *
     *
     *
     *
     *
     */

    Graphics g;

}
