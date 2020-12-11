package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Core.PointComparator;
import com.destroyordefend.project.Tactic.Comparators.AriDefenceComparator;
import com.destroyordefend.project.Unit.Unit;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class RandomAttack implements Tactic {

    @Override
    public void SortMap( Unit unit) {
        System.out.println(">>>>>>>>>>>>>>>>>>>\n\n");
        System.out.println("name: " + unit.getName()+ " ss: " + unit.getTreeSetUnit().size());

        Tactic.updateRange(unit);
        ArrayList<String> types  ;
        types = unit.getSortMap();

        TreeSet<Unit> filtered = new TreeSet<>((v1,v2)->1);
        for (String type : types) {
            System.out.println(type);
            for (Unit u : unit.getTreeSetUnit()) {
                System.out.println(u.getType());
                if (u.getType().equals(type)) {
                    System.out.println("added in: " + u.getName()  + " " + u.getId());
                    filtered.add(u);
                }
            }
        }
        System.out.println("Not Filtered id:  " + unit.getId() + "  " + unit.getTreeSetUnit().size());
        System.out.println("Filtered " + filtered.size()+ " " + unit.getId());
        TreeSet<Unit> temp = new TreeSet<>(new PointComparator());
        temp.addAll(filtered);
        unit.setTreeSetUnit(filtered);
        System.out.println(">>>>>>>>>>>>>>>>>>>\n\n");

    }

    @Override
    public String toString() {
        return "RandomAttack";
    }
}
