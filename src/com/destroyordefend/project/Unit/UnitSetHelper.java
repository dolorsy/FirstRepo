package com.destroyordefend.project.Unit;

import java.util.HashMap;

public interface UnitSetHelper {
    HashMap<String,Unit> leftAndRight = new HashMap<>();
     void setLeftUnit(Unit unit);
     void setRightUnit(Unit unit);
     Unit getLeftUnit();
     Unit getRightUnit();
     void updateLeftAndRight();
     boolean needSwapWithLeft();
     boolean needSwapWithRight();
     void swapWithLeft();
     void swapWithRight();



}
