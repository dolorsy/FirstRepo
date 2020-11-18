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
        return this.id;
    }

    public int getRadius() {
        return this.radius;
    }

    public int getRange() {
        return this.range;
    }

    public String getType() {
        return this.type;
    }

    public Movement getMovement() {
        return this.movement;
    }

    public TreeSet<Unit> getTreeSetUnit() {
        return this.treeSetUnit;
    }


    public String getRole() {
        return role;
    }

    public UnitValues getValues() {
        return values;
    }

    //Get Position Unit
    public Point getPosition(){
        return this.point;
    }

    //Method TacticAble Class
    @Override
    public TacticAble AcceptTactic(Tactic tactic) {
        return null;
    }

    //Method MovementAble Class
    @Override
    public MovementAble AcceptMovement(Movement movement) {
        return null;
    }

    /*========================================== UnitValues Class =======================================*/

    //Inner Class Unit Values
public static class UnitValues {

    //Object for Singleton
    private static UnitValues unitValues = null;

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

    //Singleton
   private static UnitValues getInstance(){
    if(unitValues == null)
    {
        unitValues = new UnitValues();
    }
    return unitValues;
   }

    //Set
    public void setSpeed(int speed){

        this.speed = speed;
    }

    public void setShot_speed(int shot_speed){

        this.shot_speed = shot_speed;
    }

    public void setDamage(int damage){

        this.damage = damage;
    }

    public void setHealth(int health) {

        this.health = health;
    }

    //Get
    public int getSpeed(){

        return this.speed;
    }

    public int getShot_speed() {
        return this.shot_speed;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getHealth() {
        return this.health;
    }
}

/*========================================== Damaging Class ============================================*/

    //Inner Class Damaging
public class Damaging implements Damage {
    //Method
    @Override
    public void DoDamage() {
    }

    @Override
    public Damage AcceptDamage() {
            return null;
        }
}
/*======================================================================================================*/

}//Finish Class Unit
