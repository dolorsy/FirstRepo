package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Unit.Unit;

import java.util.*;

public class Plan {
    final private ArrayList<Command> commands;
    final private Map<Integer, Integer> unitPC;
    private final Map<Integer, Integer> waitingTimes;

    public Plan() {
        commands = new ArrayList<>();
        unitPC = new Hashtable<>();
        waitingTimes = new Hashtable<>();
    }

    public void addUnits(Unit... units) {
        for (Unit i : units) {
            unitPC.put(i.getId(), 0);
        }
    }

    public void addCommands(Command... commands) {
        Collections.addAll(this.commands, commands);
    }

    public boolean applyTo(Unit unit) {
        int index = unitPC.get(unit.getId());
        Command command = commands.get(index);
        command.applyTo(unit);
        return command instanceof Wait;
    }

    public void getNextCommandTo(Unit unit) {
        int id = unit.getId();
        if (!unitPC.containsKey(id))
            throw new RuntimeException("this id: " + id + " is not at this plan");
        int pc = unitPC.get(id);
        if (pc >= commands.size())//remove plan
            unit.acceptPlan(null);
        else
            unitPC.replace(id, pc + 1);

    }

    public interface Command {
        void applyTo(Unit unit);
    }

    public class Wait implements Command {
        int waitingTime;

        public Wait(int waitingTime) {
            if (waitingTime <= 0)
                throw new RuntimeException("invalid waiting time" + waitingTime);
            this.waitingTime = waitingTime;
        }

        @Override
        public void applyTo(Unit unit) {
            int index = unit.getId();
            int waitingTime;
            if (waitingTimes.containsKey(index)) {
                waitingTime = waitingTimes.get(index) - 1;
            }
            else
                waitingTimes.put(index,waitingTime = this.waitingTime);
            waitingTimes.replace(index, waitingTime - 1);
            if (waitingTimes.get(index) == 0) {
                waitingTimes.remove(index);
                getNextCommandTo(unit);
            }
        }
    }

    public class ChangeTarget implements Command {
        Point target;
        boolean isSet = false;

        public ChangeTarget(Point point) {
            this.target = point;
        }

        @Override
        public void applyTo(Unit unit) {
            Stack<Point> unitTargets = unit.getMovement().getTruck();
            if (unitTargets.contains(target) && isSet)
                return;
            if(isSet){
                getNextCommandTo(unit);
            }else{
                Movement.addTarget(target,unit);
                isSet = true;
            }
        }
    }
    /*

     public void addToPlan(Unit unit){
            commands.add(unit);
        }
        public void startPlane(){
            applyPlan();
        }

        public void applyPlan(){
            Point[] allVectors = {new Point(0,0),new Point(0,1),new Point(1,0),new Point(1,1),
                    new Point(0,-1),new Point(-1,0),new Point(-1,-1),new Point(-1,1),new Point(1,-1),};

            Point currentCenter = centerPoint;
            for(int i = 0; i<= commands.size()/8; i++){
            for (int j = 0;j<9;j++){
                Unit unit = commands.get(j);
                unit.addTarget(new Point(centerPoint.getX() + unit.getRadius()*2*allVectors[j].getX(),
                        centerPoint.getY()+ unit.getRadius()*2*allVectors[j].getY()));
                unitsPoints.put(unit.getId(),unit.getMovement().getTarget());
            }
            currentCenter = new Point(currentCenter.getX()+100*allVectors[i+1].getX(),currentCenter.getY() + 100*allVectors[i+1].getY());
            }

            //Todo:: Here we should Decide the best Point around CenterPoint to place the Unit There
            //Todo:: make sure the unit tactic is ToTarget (The Target is this Point)
        }

        public boolean isInPlace(Unit unit){
            return unit.getPosition().equals(unitsPoints.get(unit.getId()));
        }

        public Integer getTime() {
        return waitingTime;
        }*/


}
