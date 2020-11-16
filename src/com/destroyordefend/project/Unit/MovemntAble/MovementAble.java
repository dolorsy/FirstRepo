package com.destroyordefend.project.Unit.MovemntAble;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Movement.Movement;

public interface MovementAble {
    Point point = null;
    MovementAble AcceptMovement(Movement movement);
}
