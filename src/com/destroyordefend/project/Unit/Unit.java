package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Core.PointComparator;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Tactic.Tactic;

import java.util.List;
import java.util.TreeSet;

import static com.destroyordefend.project.Core.Game.game;
import static com.destroyordefend.project.Main.p;


public class Unit  implements  TacticAble , MovementAble , Barrier,UnitSetHelper{

    int id;
    String playerId;
    final int attackSpeed = 4;
    String type;
    String name;
    String role;
    double armor;
    int range;
    int price;
    int radius;
    List<String> SortMap;
    Movement movement;
    TreeSet<Unit> treeSetUnit;
    Point point;
    UnitValues values;
    Tactic tactic;
    Damaging damaging;


    public String getPlayerId() {
        return playerId;
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

    public void setName(String name) {
        this.name = name;
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
    //Constructor Jsson Unit
    public Unit( String type,String name,int health,double armor,int damage,int range,double shot_speed,int radius,int speed,List<String> SortMap,int price){
        this.type = type;
        this.name = name;
        this.armor = armor;
        this.range = range;
        this.radius = radius;
        this.SortMap = SortMap;
        this.price = price;
        values =new UnitValues (speed, shot_speed,damage,health);
    }

    //Copy Constructor
    public Unit(Unit unit){
        this.treeSetUnit = new TreeSet<>(new PointComparator());
        this.id = unit.id;
        this.radius = unit.radius;
        this.range = unit.range;
        this.type = unit.type;
        this.name = unit.name;
        this.price = unit.price;
        this.SortMap = unit.SortMap;
        this.armor = unit.armor;
        this.movement = unit.movement;
        this.treeSetUnit = unit.treeSetUnit;
        this.point = unit.point;
        this.role = unit.getRole();
        this.values = unit.values;
    }

    public void print(){
        System.out.println(
                "id: " + id
                +"\nrad: "  + radius
                +"\nrang: " + range
                +"\ntype: " + type
                +"\nMovement: " + movement.toString()
                +"\ninRange: " + treeSetUnit.toString()
                +"\npoint: " + point.asString()
                +"\nRole: " + role
                +"\nvaleus: " + values.asString());
    }
    //Set
    public void setId(int id) {
        this.id = id;
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

        this.point.setX(point.getX());
        this.point.setY(point.getY());
    }

    public void setValues(UnitValues values) {
        this.values = values;
    }

    //Get
    public int geUnitId() {
        return id;
    }

    public List<String> getSortMap() {
        return SortMap;
    }

    public int getPrice() {
        return price;
    }

    public double getArmor() {
        return armor;
    }

    public String getName() {
        return name;
    }


    public int getRange() {
        return range;
    }

    public String getType(){
        return type;
    }

    public int getRadius() {
        return radius;
    }

    public void Move(){
        p("Move id: "+getId() + " x,y " );

        boolean river = movement.SetNextPoint(this);

        p("Move id: "+getId() + " x,y " + getPosition().asString());

        this.updateLeftAndRight();
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
    @Override
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

    @Override
    public boolean isAlive() {
        return getHealth()>0;
    }

    public void setSpeed(int speed){

        this.values.speed = speed;
    }

    public void setShot_speed(int shot_speed){

        this.values.shot_speed = shot_speed;
    }


    public Unit giveValues(UnitValues unitValues){
        this.values = unitValues;
        return this;
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

    public double getShot_speed() {
        return this.values.shot_speed;
    }

    void onDestroy(){
        game.DeleteUnit(this);
        game.UpdateState();


    }

    @Override
    public void setLeftUnit(Unit unit) {
        leftAndRight.put("left",unit);
    }

    @Override
    public void setRightUnit(Unit unit) {
        leftAndRight.put("right",unit);
    }

    @Override
    public Unit getLeftUnit() {
        return leftAndRight.get("left");
    }

    @Override
    public Unit getRightUnit() {
        return leftAndRight.get("right");
    }

    @Override
    public void updateLeftAndRight(){
        //Todo: need to check;

        if(needSwapWithLeft()){
            swapWithLeft();
        }else if(needSwapWithRight()){
            swapWithRight();
        }

    }
    @Override
    public boolean needSwapWithLeft(){
        //Todo: need to check;

        return (getLeft()==getLeftUnit().getRight() && getDown()<getLeftUnit().getUp()) ||
                this.getLeft() < getLeftUnit().getRight();
    }
    @Override
    public boolean needSwapWithRight(){
        //Todo: need to check;

        return  (getRight()==getRightUnit().getLeft() && getUp()<getRightUnit().getDown())||
                getRight() > getRightUnit().getLeft();
    }

    @Override
    public void swapWithLeft() {
        //Todo: need to check;

        Unit temp = getLeftUnit();
        setLeftUnit(getLeftUnit().getLeftUnit());
        temp.setRightUnit(getRightUnit());
        setRightUnit(temp);
        temp.setLeftUnit(this);
    }

    @Override
    public void swapWithRight() {
        //Todo: need to check;

        Unit temp = getRightUnit();
        setRightUnit(getRightUnit().getRightUnit());
        temp.setLeftUnit(getLeftUnit());
        setLeftUnit(temp);
        temp.setRightUnit(this);
    }


    public class UnitValues {
        int health;
        int damage;
        double shot_speed;
        int speed;
        int currentSpeed;

        public UnitValues(){

        }

        //Constructor UnitValues Class
        public UnitValues(int speed, double shot_speed, int damage, int health){
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


