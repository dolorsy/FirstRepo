package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Unit;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import static com.destroyordefend.project.Core.Game.game;

public class AircraftMovement implements Movement {
    private PriorityQueue<Point> track = new PriorityQueue<>();

    public AircraftMovement(Unit airport){
        track.add(airport.getPosition());
        track.add(game.getBase().getPosition());
    }
    @Override // todo: you should use this to get next point then in unit.move() set the next point
    //todo: according to the terrain
    public Point GetNextPoint(Unit unit) {
        return null;
    }

    @Override
    public boolean SetNextPoint(Unit unit) {
        if(unit.getPosition().equals(track.peek())){
            Point temp = track.poll();
            track.add(temp);
        }
        unit.setPosition(Movement.straightMove(unit.getPosition(), track.peek()));
        return false;
    }
}
