package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Tactic.Tactic;
import com.destroyordefend.project.Unit.Damage.Damage;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Unit.MovemntAble.MovementAble;
import com.destroyordefend.project.Unit.TacticAble.TacticAble;
import com.destroyordefend.project.Core.Point;

/*===========================================Unit Class============================================*/

public class Unit  implements  TacticAble , MovementAble {

    int id;
    int radius;
    String type;//type unit
    Movement movement;



    //Constructor Empty
    public Unit(){}

    //Constructor Values
    public Unit(int id , String type , int radius, Movement movement){
        this.id = id;
        this.type = type;
        this.radius = radius;
        this.movement = movement;
    }

    //Constructor copy
    public Unit(Unit unit){
        this.id = unit.id;
        this.type = unit.type;
        this.radius = unit.radius;
        this.movement = unit.movement;
    }

    //Set
    public void setId(int id) {
        this.id = id;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    //Get

    public int getId() {
        return this.id;
    }

    public int getRadius() {
        return this.radius;
    }

    public String getType() {
        return this.type;
    }

    public Movement getMovement() {
        return this.movement;
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
    public int speed;
    public int shot_speed;
    public int damage;
    public int health;

    //constructor Empty
    public UnitValues(){}

    //Constructor UnitValues Class
    public UnitValues(int speed , int shot_speed , int damage , int health){
        this.speed = speed;
        this.shot_speed = shot_speed;
        this.damage = damage;
        this.health = health;
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
