package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Core.PointComparator;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Tactic.Tactic;

import java.util.List;
import java.util.TreeSet;

import static com.destroyordefend.project.Core.Game.game;
import static com.destroyordefend.project.Main.p;


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
    Tactic tactic;
    Damaging damaging;
    String playerId;
    List<String> SortMap;

    public String getPlayerId() {
        return playerId;
    }

    public void setSortMap(List<String> sortMap) {
        SortMap = sortMap;
    }

    public List<String> getSortMap() {
        return SortMap;
    }

    public Damaging getDamaging() {
        if(damaging ==null)
            damaging = new Damaging();
        return damaging;
    }

    public int getId() {
        return id;
    }

    public Tactic getTactic() {
        return tactic;
    }



     Unit(){
    treeSetUnit = new TreeSet<>(new PointComparator());
    }
    //Constructor 1
    public Unit(int id, int radius, int range, String type,int speed ,int shot_speed,int damage ,int health) {
        this.id = id;
        this.radius = radius;
        this.range = range;
        this.type = type;
        values =new UnitValues (speed,shot_speed,damage,health);
        this.point = new Point(0,0);

    }
    //Constructor 2
    public Unit (int id, int radius, int range, String type, UnitValues values, TreeSet<Unit> treeSetUnit) {
        this.id = id;
        this.radius = radius;
        this.range = range;
        this.type = type;
        this.values = values;
        this.treeSetUnit = treeSetUnit;
        this.point = new Point(0,0);
    }

    //Copy Constructor
    public Unit(Unit unit){
        this.treeSetUnit = new TreeSet<>(new PointComparator());
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
    public void print(){
        System.out.println(
                "id: " + id
                +"rad: "  + radius
                 + "rang: " + range
                +"type: " + type
                +"Movement: " + movement.toString()
                +"inRange: " + treeSetUnit.toString()
                +"point: " + point.asString()
                +"Role: " + role
                +"valeus: " + values.asString());

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
    public int geUnitId() {
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

    public void Move(){


        Point p = this.movement.GetNextPoint(getPosition());
        p("Move id: "+getId() + " x,y " + p.asString());
        int factor = Movement.SetUnitPlace(p,this);
        if (factor != 0) {
            //TODO: For loop like Current speed to push invokable method in UpdateMapAsyncTask
            values.currentSpeed = values.speed/factor;
            this.setPoint(p);
        }

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
    public Unit AcceptTactic(Tactic tactic) {
        p("Accept Tactic");
        this.tactic = tactic;
        return this;
    }

    //Method MovementAble Class
    @Override
    public Unit AcceptMovement(Movement movement) {
        this.movement = movement;
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
    public int getCurrentSpeed(){
        return  this.values.currentSpeed;
    }

    public int getShot_speed() {
        return this.values.shot_speed;
    }

    void onDestroy(){

        game.UpdateState();


    }

    public int getLeft(){
        return point.getX() - this.radius;
    }
    public int getRight(){
        return point.getX() + this.radius;
    }
    public int getUp(){
        return point.getY() + this.radius;
    }
    public int getDown(){
        return point.getY() - this.radius;
    }

    class UnitValues {

        //Object for Singleton

        int speed;
        int shot_speed;
        int damage;
        int health;
        int currentSpeed;
        //constructor Empty
        private UnitValues(){}

        //Constructor UnitValues Class
        public UnitValues(int speed, int shot_speed, int damage, int health){
            this.speed = speed;
            this.shot_speed = shot_speed;
            this.damage = damage;
            this.health = health;
            this.currentSpeed = speed;
        }

        public String asString(){
            return String.valueOf(
                    "Speed: " + speed
                    + "health" + health);

        }

    }
    public void UpdateRange(){
        p("Update Range id: " +getId());
        Tactic.updateRange(this);
        this.tactic.SortMap(this);
        //Todo::Make sure the call by referance
    }


    public  class Damaging implements Damage {
        int canShot = 0 ;

        public int getCanShot() {
            return canShot;
        }


        @Override
        public void DoDamage() {
            treeSetUnit.first().getDamaging().AcceptDamage(this.getDamage());

        }

        @Override
        public int getDamage() {
            if(canShot == 0){
                p("Do damage id: " + getId());
                canShot = 4;
                return values.damage;

            }else {
                decrease();
                return 0;
            }        }

        @Override
        public void AcceptDamage(int damage) {

            if( (values.health - damage ) <= 0 ){
                values.health = 0;
            }else {
                values.health -= damage;
            }
            p("Accept Damage id: " + getId() + "new Helth: " +values.health );
        }

        @Override
        public void decrease() {
            canShot -= 1;
        }

    }

}

    //Inner Class Unit Values


