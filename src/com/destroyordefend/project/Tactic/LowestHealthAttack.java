package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Tactic.Comparators.HealthComparator;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.Core.Point;

import java.util.Map;
import java.util.TreeSet;

public class LowestHealthAttack implements Tactic {

    @Override
    public void SortMap(Unit unit) {

        HealthComparator healthComparator = new HealthComparator();
        TreeSet<Unit> temp = new TreeSet<>(healthComparator);
        temp = unit.getTreeSetUnit();
        unit.setTreeSetUnit(temp);

    }
}
