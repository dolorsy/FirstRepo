package com.destroyordefend.project.Unit.MovemntAble;

import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Core.Point;

public interface MovementAble {
    MovementAble AcceptMovement(Movement movement , Point point);
}
