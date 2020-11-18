package com.destroyordefend.project.Tactic.Comparators;

import com.destroyordefend.project.Unit.Unit;

import java.util.Comparator;

public class DamageComparator implements Comparator<Unit> {

    @Override
    public int compare(Unit o1, Unit o2) {

        Unit.UnitValues va1 = o1.getValues();
        Unit.UnitValues va2 = o2.getValues();

        if (va1.getDamage() == va2.getDamage())
            return 1;
        return va2.getDamage() - va1.getDamage();
    }
}
