package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Unit.Barrier;
import com.destroyordefend.project.Unit.Terrain;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.Core.Point;

import java.util.Stack;

public class ToTarget implements Movement {
    private Barrier target;
    private Stack<Barrier>targetStack = new Stack<>();
    public ToTarget(Unit target) {
        this.target = target;
    }
    @Override
    public Point GetNextPoint(Unit unit) {
        if(unit.getType().startsWith("air")){
            return Movement.straightMove(unit.getPosition(),target.getPosition());
        }else{
            if(unit.getPosition().equals(target.getPosition()) || !target.isAlive()){
                target = null;
                while(!targetStack.empty()){
                    target = targetStack.pop();
                    if(target.isAlive())
                        break;
                }
                if(target == null)
                    target = Game.getGame().getBase();
            }
            Barrier barrier = Movement.getBarrierBetween(unit,target);
            if(barrier != null && barrier instanceof Terrain){
                return barrier.getDown();
                //todo : this return should be the correct side not only down and there is a problem in getSide
                //should be fixed
            }
        }
    }
}
