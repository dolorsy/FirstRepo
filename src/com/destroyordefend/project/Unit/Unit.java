package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Tactic.Tactic;
import com.destroyordefend.project.Unit.Damage.Damage;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Unit.MovemntAble.MovementAble;
import com.destroyordefend.project.Unit.TacticAble.TacticAble;

public class Unit  implements  TacticAble , MovementAble {

    int id;
    int count;//???
    Movement movement;

    //Constructor
    public Unit(int id , int count , Movement movement){
        this.id = id;
        this.count = count;
        this.movement = movement;
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

    //Inner Class Unit Values
    public  class UnitValues{
        int speed;
        int shot_speed;
        int damage;
        int health;

        //Constructor UnitValues Class
        public UnitValues(int speed , int shot_speed , int damage , int health){
            this.speed = speed;
            this.shot_speed = shot_speed;
            this.damage = damage;
            this.health = health;
        }

    }

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
}
