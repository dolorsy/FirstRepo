package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.Point;

public class ToTarget implements Movement {
    private final Unit target;

    public ToTarget(Unit target) {
        this.target = target;
    }

    @Override
    public Point GetNextPoint(Point concurrent) {
        return null;
    }
}
