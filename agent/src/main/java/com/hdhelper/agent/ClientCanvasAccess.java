package com.hdhelper.agent;

import java.awt.*;

public interface ClientCanvasAccess {
   void setBitmap(ClientCanvas target, int[] raster, Image representative);
   void setDelegate(ClientCanvas target, Component c);
}
