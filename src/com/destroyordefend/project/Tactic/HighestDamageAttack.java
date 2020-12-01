package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Tactic.Comparators.DamageComparator;
import com.destroyordefend.project.Unit.Unit;

import java.util.List;
import java.util.TreeSet;

public class HighestDamageAttack implements Tactic {

    @Override
    public void SortMap(Unit unit) {

        Tactic.updateRange(unit);

        List<String> types = unit.getSortMap();

        DamageComparator damageComparator = new DamageComparator();

        TreeSet<Unit> temp = new TreeSet<Unit>(damageComparator);

        temp = unit.getTreeSetUnit();

        TreeSet<Unit> filtered = new TreeSet<Unit>(damageComparator);
        for (String type : types) {
            for (Unit u : temp) {
                if (u.getType().equals(type))
                    filtered.add(u);
            }
        }

        unit.setTreeSetUnit(filtered);
    }
}
