package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Unit;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import static com.destroyordefend.project.Core.Game.game;

public class AircraftMovement implements Movement {
    private Stack<Point> track = new Stack<>();
    final Point airport;



    public AircraftMovement(Unit airport){
        track.add(airport.getPosition());
        track.add(game.getBase().getPosition());
        this.airport = airport.getPosition();
    }

    public AircraftMovement() {
        //Todo:: A big Mistake
        airport = new Point(50,50);
    }

    public void updateTrack(){
        track.add(airport);
        track.add(game.getBase().getPosition());
    }

    @Override
    public Point GetNextPoint(Unit unit) {
        if(track.peek().equals(unit.getPosition()))
            track.pop();
        if( track.empty())
            updateTrack();
        Point p = Movement.straightMove(unit.getPosition(),track.peek());
        return p;
    }

    @Override
    public Stack<Point> getTruck() {
        return track;
    }

    @Override
    public boolean SetNextPoint(Unit unit) {

        return false;
    }

    @Override
    public String toString() {
        return "AircraftMovement{" +
                "airport=" + airport +
                '}';
    }
}
