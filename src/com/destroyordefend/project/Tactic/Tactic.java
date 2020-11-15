package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.Point;

import java.util.Map;

public interface Tactic {

    void SortMap (Map<Unit, Point> OnArena);
}
