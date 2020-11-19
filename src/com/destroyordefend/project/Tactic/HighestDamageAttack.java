package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Tactic.Comparators.DamageComparator;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.Core.Point;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeSet;

public class HighestDamageAttack implements Tactic{

    @Override
    public void SortMap(Unit unit ) {

        DamageComparator damageComparator = new DamageComparator();
        TreeSet<Unit> temp = new TreeSet<Unit>(damageComparator);
        temp = unit.getTreeSetUnit();
        unit.setTreeSetUnit(temp);



    }
}
