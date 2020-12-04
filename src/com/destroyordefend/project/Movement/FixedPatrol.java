package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Unit;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * the movement will be up right left down
 */
public class FixedPatrol implements Movement {
    //Todo: need to apply new Movement
    LinkedList<Point> track ;
     int stepSize = 4;
     int currentTarget = 1;
    public FixedPatrol(int stepSize){
     this.stepSize = stepSize;
    }


    private  void initQeueu(Point point){
        track = new LinkedList<>();
        track.add(new Point(point.getX(),point.getY()));
        track.add(new Point(point.getX(),point.getY() + stepSize));
        track.add(new Point(point.getX() + stepSize,point.getY() + stepSize));
        track.add(new Point(point.getX()+stepSize,point.getY()));

    }
    @Override
    public Point GetNextPoint(Unit unit) {
        if(track == null)
            initQeueu(unit.getPosition());
        if(unit.getPosition().equals(track.get(currentTarget))){
            if(currentTarget == 3)
                currentTarget =0;
            else
                currentTarget++;

            Point temp = track.get(currentTarget);
            System.out.println(temp);

        }
        Point p =Movement.straightMove(unit.getPosition(),track.get(currentTarget));
        return p;
    }


    private interface next {
        Point getNext(Point p);
    }
}
