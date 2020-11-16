package com.destroyordefend.project.Core;

public class Arena {
    static Byte[][] arena ;
    static int x = 10000,y =10000;

    public static void initArena(){
        //Todo: Should be called when thread updated
        arena = new Byte[x][y];
    }


    boolean setUnitPlace(Point center,int radius){
        int i = radius/2;
        //Todo: We should edit the following code if we change from Center To Corner
        int startX, startY,endX,endY;
        startX = center.getX() - i;
        startY = center.getY() - i;
        endX = center.getX() + i;
        endY = center.getY() + i;

        for(int j = startX;startX<=endX; j++){
            for(int k = startY;startY<=endY; k++){
                if(arena[j][k] == 1 || k > y || j > x){

                    return false;
                }
            }
        }
        for(int j = startX;startX<=endX; j++){
            for(int k = startY;startY<=endY; k++){
                arena[j][k] = 1;
            }
        }


        return true;
    };


}
