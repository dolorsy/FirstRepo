package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Unit;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import static com.destroyordefend.project.Core.Game.game;

public class AircraftMovement implements Movement {
    PriorityQueue<Point> track = new PriorityQueue<>();


    public AircraftMovement(Unit airport){
        track.add(airport.getPosition());
        track.add(game.getBase().getPosition());
    }
    @Override
    public Point GetNextPoint(Unit unit) {
        return null;
    }

    @Override
    public boolean SetNextPoint(Unit unit) {
        if(unit.getPosition().equals(track.peek())){
            Point temp = track.poll();
            track.add(temp);
        }
        unit.getPosition().setPoint(makeAnewPoint(unit,track.peek()));
        return false;
    }
}
