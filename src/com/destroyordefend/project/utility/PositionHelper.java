package com.destroyordefend.project.utility;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Unit;

import java.util.HashMap;
import java.util.Map;

public class PositionHelper {
    private final static int add = 1000, remove = 2000, search = 3000;

    private PositionHelper() {
    }

    private static PositionHelper ins;
    private final Map<Point, Unit> allPoints = new HashMap<>();

    public static PositionHelper getInstance() {
        if (ins == null) {
            synchronized (PositionHelper.class) {
                if (ins == null)
                    ins = new PositionHelper();
            }
        }
        return ins;
    }

    public Unit canSetAt(Unit u, Point newPoint) {
        Point current = u.getPosition();
        u.setPosition(newPoint);
        Unit unit = eidt(u, search);
        u.setPosition(current);
        return unit;
    }

    public void setUnitPlace(Unit unit, Point newPoint) {
       // System.out.println("called set unit place");
        Unit i = canSetAt(unit, newPoint);

        if (i != null) {
            if(! i.equals(unit)) {
                System.out.println(allPoints.size());
                throw new RuntimeException("set with shared point " + i.getId() + i.getName()+" " + i.getPosition() + " " + unit.getId() + unit.getName()+" " + unit.getPosition());
            }
        }
        removeUnitAllocatedPosition(unit);
        unit.setPosition(newPoint);
        eidt(unit, add);
    }

    private void removeUnitAllocatedPosition(Unit unit) {
        eidt(unit, remove);
    }


    private Unit eidt(Unit unit, final int swich) {
        Unit out = null;
        int left = unit.getLeft();
        int right = unit.getRight();
        int up = unit.getUp();
        int down = unit.getDown();

      //  if (left <= 0 && right <= 0)
       //     throw new RuntimeException("Setting out of Arena");

        for (int x = left; x <= right; x++) {
            for (int y = up; y <= down; y++) {
                Point temp = new Point(x,y);
                if (swich == add) {
                    allPoints.put(temp, unit);
                    //System.out.println("added done" + unit.getId());
                } else if (swich == remove) {
                    allPoints.put(temp, null);
                } else {
                    out = allPoints.get(temp);
                    //System.out.println(temp+"this is b " + out);
                    if (out != null) {
                        //System.out.println("hello break");
                        break;
                    }
                }
            }
        }
        return out;
    }

    @Override
    public String toString() {
        return "PositionHelper{" +
                "allPoints=" + allPoints +
                '}';
    }
}
