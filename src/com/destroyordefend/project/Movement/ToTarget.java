package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Barrier;
import com.destroyordefend.project.Unit.Terrain;
import com.destroyordefend.project.Unit.Unit;

import java.util.Stack;

public class ToTarget implements Movement {
    private Barrier target;
    private final Stack<Barrier> targetStack = new Stack<>();

    public ToTarget(Unit target) {
        this.target = target;
    }

    @Override
    public Point GetNextPoint(Unit unit) {
        if (unit.getType().startsWith("air")) {//air unit
            return Movement.straightMove(unit.getPosition(), target.getPosition());
        } else {//ground unit
            if (unit.getPosition().equals(target.getPosition()) || !target.isAlive()) {
                //if unit arrived to its target or this target is destroyed;change it to the next target
                target = null;
                while (!targetStack.empty()) {
                    target = targetStack.pop();
                    if (target.isAlive())
                        break;
                }
                if (target == null)
                    target = Game.getGame().getBase();
            }
            //check if there is anything prevent this unit from move straightforwardly to its target
            Barrier barrier = Movement.getBarrierBetween(unit, target);
            //if barrier is null then instanceof will return false
            //if barrier is vally then the unit should go to the closest side of the vally before ,
            //then go to its target
            if (barrier instanceof Terrain && barrier.getType().equals("vally")) {
                targetStack.push(target);
                target = barrier;
            }
            if (target.getType().equals("vally")) {
                //determine the closest corner
                Point[] corners = new Point[]{
                        new Point(barrier.getDown() + 1, barrier.getLeft() - 1),
                        new Point(barrier.getDown() + 1, barrier.getRight() + 1),
                        new Point(barrier.getUp() - 1, barrier.getLeft() - 1),
                        new Point(barrier.getUp() - 1, barrier.getRight() + 1)};
                int min = 0;
                double curDist = unit.getPosition().distance(corners[0]);
                for (int i = 0; i < corners.length; i++) {
                    double curAns = (unit.getPosition().distance(corners[i]));
                    if (curAns < curDist) {
                        min = i;
                        curDist = curAns;
                    }
                }
                return Movement.straightMove(unit.getPosition(), corners[min]);
            } else {
                return Movement.straightMove(unit.getPosition(), target.getPosition());
            }
        }
    }
}

