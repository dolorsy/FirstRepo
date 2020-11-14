package com.destroyordefend.project.Movement;

import com.destroyordefend.project.utility.Point;

public class FixedPosition implements Movement {

    @Override
    public Point GetNextPoint(Point concurrent) {
        return concurrent;
    }
}
