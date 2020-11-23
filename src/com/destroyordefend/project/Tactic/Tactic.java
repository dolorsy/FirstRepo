package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.Core.Point;

import java.util.Map;
import java.util.TreeSet;

import static com.destroyordefend.project.Core.Game.game;

public interface Tactic {
    static void updateRange(Unit t){
        TreeSet<Unit> unitTreeSet = new TreeSet<>(game.getAllUnits());
        unitTreeSet.removeIf(
                unit -> unit.getPosition().getX()-unit.getRadius() > t.getPosition().getX()+t.getRange() ||
                        unit.getPosition().getX()+unit.getRadius() < t.getPosition().getX()-t.getRange()
        );
        unitTreeSet.removeIf(
                unit -> unit.getPosition().getY()-unit.getRadius() > t.getPosition().getY()+t.getRange() ||
                        unit.getPosition().getY()+unit.getRadius() < t.getPosition().getY()-t.getRange()
        );
        t.setTreeSetUnit(unitTreeSet);
    }
    void  SortMap (Unit unit);
}
