package com.destroyordefend.project.Movement;


import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Barrier;
import com.destroyordefend.project.Unit.Unit;

import java.util.Stack;

import static com.destroyordefend.project.Core.Game.game;

public interface Movement {

    Point GetNextPoint (Unit unit);
    boolean SetNextPoint(Unit unit);
    static Barrier canSetUnitPlace(Point point, Unit unit){
        Unit temp = new Unit(unit);
        temp.setPoint(point);

        if(interferenceBetween(temp,temp.getLeftUnit()) )
            return temp.getLeftUnit();
        else if(interferenceBetween(temp,temp.getLeftUnit()))
            return temp.getLeftUnit();
        else{
            //Todo: need to change
            for(Barrier u : game.getTerrains()){
                if(!(interferenceBetween(u,temp)) && !u.getPosition().equals(temp.getPosition())){
                    return u;
                }
            }
            return null;
        }


    }

    static boolean interferenceBetween(Barrier u1,Barrier u2){
        return !(u1.getUp() < u2.getDown() || u1.getDown() > u2.getUp() || u1.getRight() < u2.getLeft() || u1.getLeft() > u2.getRight());

    }
    static Point straightMove(Point src,Point dis){
        int currentX = src.getX();
        int currentY = src.getY();
        int targetX = dis.getX();
        int targetY = dis.getY();
        if(currentX != targetX){
            currentX+= currentX<targetX?1:-1;
        }
        if(currentY != targetY){
            currentY += currentY<targetY?1:-1;
        }
        return new Point(currentX,currentY);
    }

     static Barrier getBarrierBetween(Unit src,Point dis){
        Point p = src.getPosition();
        Barrier barrier = null;
        while(!p.equals(dis)){
            p = straightMove(p,dis);
            barrier = canSetUnitPlace(p,src);
            if(barrier != null)
                break;
        }
        return barrier;
    }

    default Point makeAnewPoint(Unit unit, Point t){

        Point p = unit.getPosition();

        int x = p.getX();
        int y = p.getY();
        if(t.getX()>x)
            x++;
        else if(t.getX() <x)
            x--;
        if(t.getY() > y)
            y++;
        else if(t.getY() < y)
            y--;

        return new Point(x,y);

    }
}
