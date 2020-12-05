package com.destroyordefend.project.utility;

import com.destroyordefend.project.Core.Point;

public abstract class ConsoleZoomingFilter {
    static  Point ZoomCenter = new Point(100000,10000);
    static int ZoomedArenaWidth = 100000;
    protected static  boolean inRange(Point p){return false;};
    public static  void initZoom(int x,int y,int zoomedArenaWidth){};

}
