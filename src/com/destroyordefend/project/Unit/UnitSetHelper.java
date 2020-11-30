package com.destroyordefend.project.Unit;

import java.util.HashMap;

public interface UnitSetHelper {
    HashMap<String,Unit> leftAndRight = new HashMap<>();
    public void setLeftUnit(Unit unit);
    public void setRightUnit(Unit unit);
    public Unit getLeftUnit();
    public Unit getRightUnit();
    public  void updateLeftAndRight();
    public boolean needSwapWithLeft();
    public boolean needSwapWithRight();
    public void swapWithLeft();
    public void swapWithRight();


}
