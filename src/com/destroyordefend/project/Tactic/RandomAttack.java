package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Core.PointComparator;
import com.destroyordefend.project.Tactic.Comparators.AriDefenceComparator;
import com.destroyordefend.project.Unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class RandomAttack implements Tactic {

    @Override
    public void SortMap( Unit unit) {
        Tactic.updateRange(unit);
        ArrayList<String> types ;
        types = unit.getSortMap();
        System.out.println(types);
        TreeSet<Unit> temp = unit.getTreeSetUnit();
        TreeSet<Unit> filtered = new TreeSet<>(new PointComparator());
        for (String type : types) {
            for (Unit u : temp) {
                System.out.println(u.getType() + "  "  + type);
                if (u.getType().equals(type))
                    filtered.add(u);
            }
        }
        unit.setTreeSetUnit(filtered);
        System.out.println("in Range id : " + unit.getId() + unit.getTreeSetUnit());

    }
}
