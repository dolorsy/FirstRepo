package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Tactic.Tactic;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Core.Point;

import java.util.TreeSet;

/*===========================================Unit Class============================================*/

public class Unit  implements  TacticAble , MovementAble {

    int id;
    int radius;
    int range;
    final int attackSpeed = 4;
    String type;//type unit
    Movement movement;
    TreeSet<Unit> treeSetUnit;
    Point point;
    String role;
    UnitValues values;

    //Constructor 1
    public Unit(int id, int radius, int range, String type,int speed ,int shot_speed,int damage ,int health) {
        this.id = id;
        this.radius = radius;
        this.range = range;
        this.type = type;
        values =new UnitValues (speed,shot_speed,damage,health);
    }
    //Constructor 2
    public Unit (int id, int radius, int range, String type, UnitValues values, TreeSet<Unit> treeSetUnit) {
        this.id = id;
        this.radius = radius;
        this.range = range;
        this.type = type;
        this.values = values;
        this.treeSetUnit = treeSetUnit;
    }

    //Copy Constructor
    public Unit(Unit unit){
        this.id = unit.id;
        this.radius = unit.radius;
        this.range = unit.range;
        this.type = unit.type;
        this.movement = unit.movement;
        this.treeSetUnit = unit.treeSetUnit;
        this.point = unit.point;
        this.role = unit.getRole();
        this.values = unit.values;
    }

    //Set
    public void setId(int id) {
        this.id = id;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public void setTreeSetUnit(TreeSet<Unit> treeSetUnit) {
        this.treeSetUnit = treeSetUnit;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setValues(UnitValues values) {
        this.values = values;
    }

    //Get
    public int getId() {
        return id;
    }

    public int getRadius() {
        return radius;
    }

    public int getRange() {
        return range;
    }

    public String getType() {
        return type;
    }

    public Movement getMovement() {
        return movement;
    }

    public TreeSet<Unit> getTreeSetUnit() {
        return treeSetUnit;
    }


    public String getRole() {
        return role;
    }

    public UnitValues getValues() {
        return values;
    }

    //Get Position Unit
    public Point getPosition(){
        return point;
    }

    //Method TacticAble Class
    @Override
    public TacticAble AcceptTactic(Tactic tactic) {
        return this;
    }

    //Method MovementAble Class
    @Override
    public MovementAble AcceptMovement(Movement movement) {
        return this;
    }

    public int getDamage(){
        return this.values.damage;
    }
    public void setDamage(int damage){

        this.values.damage = damage;
    }

    public int getHealth() {
        return this.values.health;
    }


    public void setSpeed(int speed){

        this.values.speed = speed;
    }

    public void setShot_speed(int shot_speed){

        this.values.shot_speed = shot_speed;
    }



    public void setHealth(int health) {

        this.values.health = health;
    }

    //Get
    public int getSpeed() {

        return this.values.speed;
    }

    public int getShot_speed() {
        return this.values.shot_speed;
    }

    class UnitValues {

        //Object for Singleton

        int speed;
        int shot_speed;
        int damage;
        int health;

        //constructor Empty
        private UnitValues(){}

        //Constructor UnitValues Class
        public UnitValues(int speed, int shot_speed, int damage, int health){
            this.speed = speed;
            this.shot_speed = shot_speed;
            this.damage = damage;
            this.health = health;
        }


    }


    public  class Damaging implements Damage {
        int canShot = 0 ;

        public int getCanShot() {
            return canShot;
        }

        //Method

        @Override
        public int DoDamage() {
            if(canShot == 0){
                canShot = 4;
                return values.damage;

            }else {
                decrease();
                return 0;
            }
        }

        @Override
        public void AcceptDamage(int damage) {

            if( (values.health - damage ) <= 0 ){
                values.health = 0;
            }else {
                values.health -= damage;
            }
        }

        @Override
        public void decrease() {
            canShot -= 1;
        }

    }

}

    //Inner Class Unit Values



