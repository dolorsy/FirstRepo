package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Point;

public class FixedPosition implements Movement {

    @Override
    public Point GetNextPoint(Point concurrent) {
        return concurrent;
    }
}
