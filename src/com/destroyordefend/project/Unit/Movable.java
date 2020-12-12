package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Movement.Movement;


public interface Movable {
    Movable acceptMovement(Movement movement);
    Movement getMovement();
    void addTarget(Point point);
}
