package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.Core.Point;

import java.util.Map;

public interface Tactic {

    void SortMap (Map<Unit, Point> OnArena);
}
