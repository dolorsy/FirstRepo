package com.destroyordefend.project.utility;

public class Arena {
    static Byte[][] arena ;

    public static void initArena(){
        //Todo: Should be called when thread updated
        arena = new Byte[10000][10000];
    }


    boolean setUnitPlace(Point center,int radius){
        int i = radius/2;
        int startX, startY,endX,endY;
        startX = center.getX() - i;
        startY = center.getY() - i;
        endX = center.getX() + i;
        endY = center.getY() + i;

        for(int j = startX;startX<=endX; j++){
            for(int k = startY;startY<=endY; k++){
                if(arena[j][k] == 1){

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
