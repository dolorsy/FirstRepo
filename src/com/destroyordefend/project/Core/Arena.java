package com.destroyordefend.project.Core;

import com.destroyordefend.project.Unit.Unit;

import java.util.TreeSet;

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
    }

    public TreeSet<Unit> UpdateRange(Unit unit){


        TreeSet<Unit> newSetUpdate = new TreeSet<>();


        for(Unit unitInRange : Game.getAllUnits()){
            if(isInRange(unit,unitInRange)){
                newSetUpdate.add(unitInRange);

            }
        }
        return newSetUpdate;

    }
    private boolean isInRange(Unit unit,Unit unitInRange){

        //Todo:Need To Check Math in a pepper
        int startX,startY,endX,endY;
        int startTX,startTY,endTX,endTY;
        startX = unit.getPosition().getX() - unit.getRange() - unit.getRadius();
        startY = unit.getPosition().getY() - unit.getRange() - unit.getRadius();
        endX = unit.getPosition().getX() + unit.getRadius() + unit.getRange();
        endY = unit.getPosition().getY() + unit.getRadius() +  unit.getRange();
        startTX = unitInRange.getPosition().getX() - unitInRange.getRadius();
        startTY = unitInRange.getPosition().getY() - unitInRange.getRadius();
        endTX = unitInRange.getPosition().getX() + unitInRange.getRadius();
        endTY = unitInRange.getPosition().getY() + unitInRange.getRadius();


        //Todo: We Can erase Role Condition To add all Units in Range Weather in the same Role Or Not
        return ((startX < startTX && endX > startTX) || (startX < endTX && endX > endTX))
                && ((startY < startTY && endY > startTY) || (startY < endTY && endY > endTY)) && (unit.getRole() != unitInRange.getRole());
    }


}
