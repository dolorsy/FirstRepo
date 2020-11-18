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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //Constructor Empty
    public Unit(){}

    //Constructor Values
    public Unit(int id , int radius,int range,String type, Movement movement,TreeSet<Unit> treeSetUnit,Point point,String role){
        this.id = id;
        this.radius = radius;
        this.range = range;
        this.type = type;
        this.movement = movement;
        this.treeSetUnit = treeSetUnit;
        this.point = point;
        this.role = role;
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

    public void setPoint(Point point) {
        this.point = point;
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
public class UnitValues {

    //Object for Singleton
    private UnitValues unitValues = null;

    public int speed;
    public int shot_speed;
    public int damage;
    public int health;

    //constructor Empty
    private UnitValues(){}

    //Constructor UnitValues Class
    private UnitValues(int speed , int shot_speed , int damage , int health){
        this.speed = speed;
        this.shot_speed = shot_speed;
        this.damage = damage;
        this.health = health;
    }

    //Singleton
   public UnitValues getInstance(){
    if(unitValues == null)
        unitValues = new UnitValues();
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
