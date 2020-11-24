package com.destroyordefend.project.Core;

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

}
