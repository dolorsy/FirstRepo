package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Movement.Movement;


public interface MovementAble {
    MovementAble AcceptMovement(Movement movement);
    Movement getMovement();
    void addTarget(Point point);
}
