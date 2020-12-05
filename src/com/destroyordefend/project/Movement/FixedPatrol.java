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
    Stack<Point> track ;
    LinkedList<Point> FixedTrack;
     int stepSize = 4;
     int currentTarget = 1;
    public FixedPatrol(int stepSize){

     this.stepSize = stepSize;
    }


    private  void initQeueu(Point point){
        track = new Stack<>();
        FixedTrack = new LinkedList<>();
        FixedTrack.add(new Point(point.getX(),point.getY()));
        track.push(FixedTrack.get(0));
        FixedTrack.add(new Point(point.getX(),point.getY() + stepSize));track.push(FixedTrack.get(0));
        track.push(FixedTrack.get(1));
        FixedTrack.add(new Point(point.getX() + stepSize,point.getY() + stepSize));
        track.push(FixedTrack.get(2));
        FixedTrack.add(new Point(point.getX()+stepSize,point.getY()));
        track.push(FixedTrack.get(3));
    }
    @Override
    public Point GetNextPoint(Unit unit) {
        if(track == null)
            initQeueu(unit.getPosition());
        if(track.peek().equals(unit.getPosition()))
            track.pop();
        if(track.size() == 0)
            initQeueu(unit.getPosition());

        Point p =Movement.straightMove(unit.getPosition(),track.peek());
        return p;
    }

    @Override
    public Stack<Point> getTruck() {
        return track;
    }

    @Override
    public String toString() {
        return "FixedPatrol{" +
                ", stepSize=" + stepSize +
                '}';
    }
}
