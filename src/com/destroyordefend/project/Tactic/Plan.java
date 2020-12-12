package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Unit.Unit;

import java.util.*;

public class Plan {
    final private ArrayList<Command> commands;
    final private Map<Integer, Integer> unitPC;
    private final Map<Integer, Integer> startSeconds;

    public Plan() {
        commands = new ArrayList<>();
        unitPC = new Hashtable<>();
        startSeconds = new Hashtable<>();
    }

    public void addUnits(Unit... units) {
        for (Unit i : units) {
            unitPC.put(i.getId(), 0);
        }
    }

    public void addCommands(Command... commands) {
        Collections.addAll(this.commands, commands);
    }

    public boolean applyTo(Planable unit) {
        int index = unitPC.get(unit.getId());
        Command command = commands.get(index);
        command.applyTo(unit);
        return command instanceof Wait;
    }

    public void getNextCommandTo(Planable unit) {
        int id = unit.getId();
        if (!unitPC.containsKey(id))
            throw new RuntimeException("this id: " + id + " is not at this plan");
        int pc = unitPC.get(id);
        try {
            unitPC.replace(id, pc + 1);
            if(unitPC.get(id)>= commands.size())
                throw new IndexOutOfBoundsException();
        }catch (IndexOutOfBoundsException e){
            unit.acceptPlan(null);
        }

    }

    public boolean isWait(Planable unit) {
        int index = unit.getId();
        if (!unitPC.containsKey(index))
            throw new RuntimeException("unit "+unit.getId()+" is not in this plan ");
       Command command = commands.get(unitPC.get(index));
       return (command instanceof Wait);
    }

    public int getWaitingSecondsTo(Planable planable){
        int cur = Game.getGame().getGameTimer().getCurrentSecond();
        int start = cur;
        try {
            start = startSeconds.get(planable.getId());
        }catch (Exception e){}
        return cur-start+1;
    }
    public interface Command {
        void applyTo(Planable unit);
    }

    public class Wait implements Command {
        int waitingTime;

        public Wait(int waitingTime) {
            if (waitingTime <= 0)
                throw new RuntimeException("invalid waiting time" + waitingTime);
            System.out.println("hello waiting time"+ waitingTime);
            this.waitingTime = waitingTime;
        }

        @Override
        public void applyTo(Planable planable) {
            int curTime = Game.getGame().getGameTimer().getCurrentSecond();
            int index = planable.getId();
            if (!startSeconds.containsKey(index))
                startSeconds.put(index,curTime);
            if (curTime - startSeconds.get(index) == waitingTime-1) {
                startSeconds.remove(index);
                getNextCommandTo(planable);
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
        public void applyTo(Planable planable) {
            Stack<Point> planableTargets = planable.getMovement().getTruck();
            if (planableTargets.contains(target) && isSet)
                return;
            if (isSet) {
                getNextCommandTo(planable);
            } else {
                Movement.addTarget(target, planable);
                isSet = true;
            }
        }
    }
    /*

     public void addToPlan(planable planable){
            commands.add(planable);
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
                planable planable = commands.get(j);
                planable.addTarget(new Point(centerPoint.getX() + planable.getRadius()*2*allVectors[j].getX(),
                        centerPoint.getY()+ planable.getRadius()*2*allVectors[j].getY()));
                planablesPoints.put(planable.getId(),planable.getMovement().getTarget());
            }
            currentCenter = new Point(currentCenter.getX()+100*allVectors[i+1].getX(),currentCenter.getY() + 100*allVectors[i+1].getY());
            }

            //Todo:: Here we should Decide the best Point around CenterPoint to place the planable There
            //Todo:: make sure the planable tactic is ToTarget (The Target is this Point)
        }

        public boolean isInPlace(planable planable){
            return planable.getPosition().equals(planablesPoints.get(planable.getId()));
        }

        public Integer getTime() {
        return waitingTime;
        }*/


}
