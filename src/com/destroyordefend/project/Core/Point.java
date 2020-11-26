package com.destroyordefend.project.Core;

import java.util.Objects;

public class Point {
    int x,y;
   public Point(){

   }
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String asString(){
       return String.valueOf('(' + String.valueOf(x) + ',' + String.valueOf(y) + ')');
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return getX() == point.getX() &&
                getY() == point.getY();
    }


}
