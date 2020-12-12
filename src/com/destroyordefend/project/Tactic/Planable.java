package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Unit.MovementAble;

public interface Planable extends MovementAble {
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
}
