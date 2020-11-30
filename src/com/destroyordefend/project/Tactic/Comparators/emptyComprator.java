package com.destroyordefend.project.Tactic.Comparators;

import com.destroyordefend.project.Unit.Unit;

import java.util.Comparator;

public class emptyComprator implements Comparator<Unit> {

    @Override
    public int compare(Unit o1, Unit o2) {

        return 1;
    }
}
