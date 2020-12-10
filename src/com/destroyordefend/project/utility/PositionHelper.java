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
        edit(u,remove);
        Point current = u.getPosition();
        u.setPosition(newPoint);
        Unit result = edit(u, search);
        u.setPosition(current);
        edit(u,add);
        return result;
    }

    public void setUnitPlace(Unit unit, Point newPoint) {
        removeUnitAllocatedPosition(unit);
        unit.setPosition(newPoint);
        edit(unit, add);
    }

    private void removeUnitAllocatedPosition(Unit unit) {
        edit(unit, remove);
    }

    private Unit edit(Unit unit, final int swich) {
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
                    if(allPoints.get(temp)!=null)
                        throw new RuntimeException("shared place"
                                +allPoints.get(temp).getName()+" "+allPoints.get(temp).getPosition()+unit.getId()+" "+unit.getPosition());
                    allPoints.put(temp, unit);
                    //System.out.println("added done" + unit.getId());
                } else if (swich == remove) {
                    allPoints.remove(temp);
                } else {
                    out = allPoints.get(temp);
                    //System.out.println(temp+"this is b " + out);
                    if (out != null && out.getId() != unit.getId() && out.is(unit.getName())) {
                        System.out.println("hello break"+out.getId()+" "+unit.getId());
                        return out;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "PositionHelper{" +
                "allPoints=" + allPoints +
                '}';
    }
}
