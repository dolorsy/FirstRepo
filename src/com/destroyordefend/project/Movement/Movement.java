package com.destroyordefend.project.Movement;


import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Barrier;
import com.destroyordefend.project.Unit.Terrain;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.PositionHelper;
import org.json.simple.JSONObject;

import java.util.Stack;

public interface Movement {

    static Movement getSuitableMovment(JSONObject movement) {
        switch (String.valueOf(movement.get("movement"))) {
            case "FixedPatrol":
                return new FixedPatrol(Integer.parseInt((String) movement.get("stepSize")));
            case "ToTarget":
                return new ToTarget(Game.getGame().getBase());
            case "AircraftMovement":
                return new AircraftMovement(new Point(Integer.parseInt((String) movement.get("airportX")), Integer.parseInt((String) movement.get("airportY"))));
            default:
                return new FixedPosition();

        }
    }

    void addTarget(Point p, Unit u);

    Point GetNextPoint(Unit unit);

    public Stack<Point> getTruck();


    static Barrier canSetUnitPlace(Point point, Unit unit) {
        Point cur = unit.getPosition();
        unit.setPosition(point);
        for (Terrain t :Game.getGame().getTerrains()){
            if (t.isSharedWith(unit)){
                unit.setPosition(cur);
                return t;
            }

        }
        return PositionHelper.getInstance().canSetAt(unit, point);
    }

    static Point straightMove(Point src, Point dis) {
        int currentX = src.getX();
        int currentY = src.getY();
        int targetX = dis.getX();
        int targetY = dis.getY();
        if (currentX != targetX) {
            currentX += currentX < targetX ? 1 : -1;
        }
        if (currentY != targetY) {
            currentY += currentY < targetY ? 1 : -1;
        }
        return new Point(currentX, currentY);
    }

    default boolean SetNextPoint(Unit unit) {
        return true;
    }

    static boolean setNext(Unit unit, Point n) {

        // Point n = Movement.straightMove(unit.getPosition(),track.peek());
        Barrier barrier = Movement.canSetUnitPlace(n, unit);
        if (barrier != null) {
            if (barrier.getName().equals("river")) {
                PositionHelper.getInstance().setUnitPlace(unit, n);
                return true;
            }
            Point[] corners = {barrier.getDownLeftCorner(), barrier.getDownRightCorner(), barrier.getUpRightCorner(), barrier.getUpLeftCorner()};
            int min = 0;
            int nextp = 1;
            double curDist = unit.getPosition().distance(corners[0]);
            for (int i = 0; i < 4; i++) {
                double curAns = (unit.getPosition().distance(corners[i]));
                if (curAns < curDist) {
                    min = i;
                    if (corners[i].getX() > unit.getRight())
                        nextp = 3;
                    else if (corners[i].getX() < unit.getLeft())
                        nextp = 4;
                    else if (corners[i].getY() < unit.getDown())
                        nextp = 2;
                    else
                        nextp = 1;

                    curDist = curAns;
                }
            }
            switch (min) {
                case 0: {
                    corners[min].setX(corners[min].getX() - unit.getRadius());
                    corners[min].setY(corners[min].getY() - unit.getRadius());
                    break;
                }
                case 1: {
                    corners[min].setX(corners[min].getX() + unit.getRadius());
                    corners[min].setY(corners[min].getY() - unit.getRadius());
                    break;
                }
                case 2: {
                    corners[min].setX(corners[min].getX() + unit.getRadius());
                    corners[min].setY(corners[min].getY() + unit.getRadius());
                    break;
                }
                case 3: {
                    corners[min].setX(corners[min].getX() - unit.getRadius());
                    corners[min].setY(corners[min].getY() + unit.getRadius());
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + min);
            }

            switch (nextp) {
                case 0: {
                    corners[nextp].setX(corners[nextp].getX() - unit.getRadius());
                    corners[nextp].setY(corners[nextp].getY() - unit.getRadius());
                    break;
                }
                case 1: {
                    corners[nextp].setX(corners[nextp].getX() + unit.getRadius());
                    corners[nextp].setY(corners[nextp].getY() - unit.getRadius());
                    break;
                }
                case 2: {
                    corners[nextp].setX(corners[nextp].getX() + unit.getRadius());
                    corners[nextp].setY(corners[nextp].getY() + unit.getRadius());
                    break;
                }
                case 3: {
                    corners[nextp].setX(corners[nextp].getX() - unit.getRadius());
                    corners[nextp].setY(corners[nextp].getY() + unit.getRadius());
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + min);
            }

            unit.getMovement().getTruck().push(corners[nextp]);
            unit.getMovement().getTruck().push(corners[min]);
        }
        //Todo: Should Update n here? n = makeAnewPoint(unit); ???? no it should be in unit.move();
        PositionHelper.getInstance().setUnitPlace(unit, n);
        return false;
    }

    Point getTarget();

    @Override
    String toString();
}
