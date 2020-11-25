package com.destroyordefend.project.Core;


import com.destroyordefend.project.Unit.Unit;

import java.util.Comparator;

public class Terrain {


    Point point;
    int speedFactory;
    String type;

    public Point getPosition() {
        return point;
    }

    public int getSpeedFactory() {
        return speedFactory;
    }

    public String getType() {
        return type;
    }

    public Terrain(Point point, int speedFactory, String type) {
        this.point = point;
        this.speedFactory = speedFactory;
        this.type = type;
    }

    public static class TerrainComparator implements Comparator<Terrain> {
        @Override
        public int compare(Terrain o1, Terrain o2) {

            if (o1.getPosition().getX() > o2.getPosition().getX())
                return 1;

            else if (o1.getPosition().getX() == o2.getPosition().getX()) {
                // if (o1.getPosition().getY() > o2.getPosition().getY())
                return o1.getPosition().getY() - o2.getPosition().getY();
            }

            if (o1.getPosition().getX() == o2.getPosition().getX() && o1.getPosition().getY() == o2.getPosition().getY())
                return 0;


            return -1;

        }
    }



}
