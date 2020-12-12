package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Unit.Movable;

public interface Planable extends Movable {
    Plan getPlan();
    int getId();
    Planable acceptPlan(Plan plan);

    default void applyPlane(){
        getPlan().applyTo(this);
    }

    default boolean isWait(){
        if(getPlan() == null)
            return false;
        return getPlan().isWait(this);
    }

    default int waitingSeconds(){
        if(!isWait())
            return 0;
        return getPlan().getWaitingSecondsTo(this);
    }
}
