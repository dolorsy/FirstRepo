package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Core.Player;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Core.PointComparator;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Tactic.Plan;
import com.destroyordefend.project.Tactic.Tactic;
import com.destroyordefend.project.utility.IdGenerator;
import com.destroyordefend.project.utility.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import static com.destroyordefend.project.Core.Game.game;
import static com.destroyordefend.project.Main.p;

public class Unit implements TacticAble, MovementAble, Barrier, UnitSetHelper {

    private  int id;
    private Movement movement;
    private TreeSet<Unit> treeSetUnit = new TreeSet<>(new PointComparator());
    private Point point = new Point();
    private Player.TeamRole role;
    private UnitValues values;
    private Tactic tactic;
    private Damaging damaging;
    private HashMap<String, Unit> leftAndRight = new HashMap<>();
    private Plan plan;
    private HashMap<Plan,Integer> plans = new HashMap<Plan, Integer>() ;


    public Unit() {
       id = IdGenerator.generate(this);
    }

    public void setId(int id) {
        this.id = id;
    }

    //Copy Constructor
    public Unit(Unit unit) {
        this();
        this.treeSetUnit.addAll(unit.treeSetUnit);
        this.movement = unit.movement;
        this.point = new Point(unit.point);
        this.role = unit.getRole();
        this.values = new UnitValues(unit.values);
    }

    public Unit(UnitValues unitValues) {
        this();
        this.values = new UnitValues(unitValues);
    }

    public ArrayList<String> getSortMap() {

        return values.sortMap;
    }

    public String getType(){
        return values.Type;
    }
    public Damaging getDamaging() {
        if (damaging == null)
            damaging = new Damaging();
        return damaging;
    }

    public Tactic getTactic() {
        return tactic;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id + "\n" +
                ", movement=" + movement + "\n" +
                ", treeSetUnit=" + treeSetUnit + "\n" +
                ", point=" + point + "\n" +
                ", role=" + role + "\n" +
                ", values=" + values + "\n" +
                ", tactic=" + tactic + "\n" +
                ", damaging=" + damaging + "\n" +
                ", SortMap=" + values.sortMap + "\n" +
                '}';
    }

    public void setTreeSetUnit(TreeSet<Unit> treeSetUnit) {
        this.treeSetUnit = treeSetUnit;

    }

    public void setDamage(int damage){
        this.values.damage = damage;
    }

    public void setRole(Player.TeamRole role) {
        this.role = role;
    }

    public void setPosition(Point point) {
        this.point.setPoint(point);
    }

    public void setValues(UnitValues values) {
        this.values = new UnitValues(values);
    }

    public Movement getMovement() {
        return movement;
    }

    public void Move() {

        if(this.plans.size() != 0 ) {
            if (this.plan.isInPlace(this)){
                this.plans.put(plan,this.plans.get(plan)-1);
                return;
            }

        }

        for(int i =0 ;i<values.currentSpeed;i++) {
            Point p = this.movement.GetNextPoint(this);
            if(p.equals(getPosition())){
                continue;
            }
            boolean f = Movement.setNext(this,p);

           if(f){
               values.currentSpeed = values.speed / 2;

           }else{
               values.currentSpeed = values.speed ;

           }


           //Todo::The Following Code will used if we have many speed factors
  /*
            Barrier factor = Movement.canSetUnitPlace(p, this);
            if(factor != null)
            System.out.println("Terrain :" + factor.getName());
            if (factor != null && factor.getClass().getName().equals(Terrain.class.getName())) {
                Terrain terrain = (Terrain) factor;
                if (terrain.getSpeedFactory() != 0) {
                    //TODO: For loop like Current speed to push invokable method in UpdateMapAsyncTask
                    values.currentSpeed = values.speed / terrain.getSpeedFactory();
                }
            }else{
                values.currentSpeed = values.speed ;
                continue;
            }*/
            this.setPosition(p);
            this.updateLeftAndRight();
        }
        Log.move(this);

    }

    public TreeSet<Unit> getTreeSetUnit() {
        return treeSetUnit;
    }

    public Player.TeamRole getRole() {
        return role;
    }

    public UnitValues getValues() {
        return values;
    }

    @Override
    public Point getPosition() {
        return point;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getRadius() {
        return values.radius;
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

    @Override
    public void addTarget(Point point) {
        this.movement.addTarget(point,this);
    }

    @Override
    public Unit AcceptPlan(Plan plan) {
        this.plans.put(plan,plan.getTime());
        this.plan = plan;
        return this;
    }

    @Override
    public boolean isAlive() {
        return values.health > 0;
    }

    public void setSpeed(int speed) {
        this.values.speed = speed;
    }

    public Unit giveValues(UnitValues unitValues) {
        this.values = new UnitValues(unitValues);
        return this;
    }

    public void setHealth(int health) {
        if(values == null)
            values = new UnitValues();
        this.values.health = health;
    }

    public double getShot_speed() {
        return this.values.shot_speed;
    }

    //Get
    public int getSpeed() {
        return this.values.speed;
    }

    public int getCurrentSpeed() {
        return this.values.currentSpeed;
    }

    public void setShot_speed(int shot_speed) {
        this.values.shot_speed = shot_speed;
    }

    void onDestroy() {
        game.DeleteUnit(this);
        game.UpdateState();
    }

    @Override
    public Unit getNeighbourUnit(String side){return leftAndRight.get(side);}
    @Override
    public void setNeighbourUnit(String side,Unit unit) {
        leftAndRight.put(side, unit);
    }

    @Override
    public void updateLeftAndRight() {
        //Todo: need to check;
        if (needSwapWithLeft()) {
            swapWithLeft();
        } else if (needSwapWithRight()) {
            swapWithRight();
        }
    }

    @Override
    public boolean needSwapWithLeft() {
        //Todo: need to check;

        return getNeighbourUnit("left") != null &&(( getLeft() == getNeighbourUnit("left").getRight() && getDown() < getNeighbourUnit("left").getUp()) ||
                this.getLeft() < getNeighbourUnit("left").getRight());
    }

    @Override
    public boolean needSwapWithRight() {
        //Todo: need to check;

        return getNeighbourUnit("right") != null &&( (getRight() == getNeighbourUnit("right").getLeft() && getUp() < getNeighbourUnit("right").getDown()) ||
                getRight() > getNeighbourUnit("right").getLeft());
    }

    @Override
    public void swapWithLeft() {
        //Todo: need to check;

        Unit temp = getNeighbourUnit("left");
        setNeighbourUnit("left",getNeighbourUnit("left").getNeighbourUnit("left"));
        temp.setNeighbourUnit("right",getNeighbourUnit("right"));
        setNeighbourUnit("right",temp);
        temp.setNeighbourUnit("left",this);
    }

    @Override
    public void swapWithRight() {
        //Todo: need to check;
        Unit temp = getNeighbourUnit("right");
        setNeighbourUnit("right",getNeighbourUnit("right").getNeighbourUnit("right"));
        temp.setNeighbourUnit("left",getNeighbourUnit("left"));
        setNeighbourUnit("left",temp);
        temp.setNeighbourUnit("right",this);
    }

    public void UpdateRange() {
        Tactic.updateRange(this);
        this.tactic.SortMap(this);
        p("Update Range id: " + id + "new Range " + treeSetUnit);
        //Todo::Make sure the call by referance
    }
    @Override
    public String getName() {
        return values.name;
    }

    public int getRange() {
        return values.range;
    }

    public int getDamage() {
        return values.damage;
    }

    public void setName(String main_base) {
        this.values.name = main_base;
    }

    public static class UnitValues {
        String name;
        int health;
        double armor;
        int damage;
        int range;
        double shot_speed;
        int radius;
        int speed;
        ArrayList<String> sortMap;
        int price;
        String Type;
        int currentSpeed;//11

        public UnitValues(String name, String Type,int health, double armor, int damage, int range, double shot_speed, int radius, int speed, ArrayList<String> SortMap, int price) {
            this.name = name;
            this.Type = Type;
            this.health = health;
            this.armor = armor;
            this.damage = damage;
            this.range = range;
            this.shot_speed = shot_speed;
            this.radius = radius;
            this.speed = speed;
            this.sortMap = SortMap;
            this.price = price;
            currentSpeed = speed;
        }

        //Todo: for Debuging
        public void setType(String type) {
            Type = type;
        }

        public  UnitValues(){

        }
        public UnitValues(UnitValues unitValues) {
            this(
                    unitValues.name,
                    unitValues.Type,
                    unitValues.health,
                    unitValues.armor,
                    unitValues.damage,
                    unitValues.range,
                    unitValues.shot_speed,
                    unitValues.radius,
                    unitValues.speed,
                    unitValues.sortMap,
                    unitValues.price);
        }

        public UnitValues(JSONObject unit) {
            name = (String) unit.get("name");
            Type = (String) unit.get("type");

            health = Integer.parseInt((String) unit.get("health"));
            armor = Double.parseDouble((String) unit.get("armor"));
            damage =Integer.parseInt ((String) unit.get("damage"));
            range = Integer.parseInt((String) unit.get("range"));
            shot_speed = Double.parseDouble((String) unit.get("shot_speed"));
            radius = Integer.parseInt((String) unit.get("radius"));
            speed = Integer.parseInt((String) unit.get("speed"));
            price = Integer.parseInt((String) unit.get("price"));
            currentSpeed = speed;
            sortMap = new ArrayList<>();
            JSONArray sMap = (JSONArray) unit.get("SortMap");
            for (Object c : sMap) {
                String s = c.toString();
                sortMap.add(s);
            }

        }
        public boolean is(String name){
            return this.name.equalsIgnoreCase(name);
        }

        public String getName() {
            return name;
        }

        public void setName(String name){
            this.name = name;
        }
        public int getPrice() {
            return price;
        }

        public int getHealth() {
            return health;
        }

        public double getArmor() {
            return armor;
        }

        public int getDamage() {
            return damage;
        }

        public int getRange() {
            return range;
        }

        public double getShot_speed() {
            return shot_speed;
        }

        public int getRadius() {
            return radius;
        }

        public int getSpeed() {
            return speed;
        }

        public ArrayList<String> getSortMap() {
            return sortMap;
        }

        public String getType() {
            return Type;
        }

        public int getCurrentSpeed() {
            return currentSpeed;
        }

        @Override
        public String toString() {
            return "UnitValues{" +
                    "name='" + name + '\'' +
                    ", health=" + health +
                    ", armor=" + armor +
                    ", damage=" + damage +
                    ", range=" + range +
                    ", shot_speed=" + shot_speed +
                    ", radius=" + radius +
                    ", speed=" + speed +
                    ", sortMap=" + sortMap +
                    ", price=" + price +
                    ", currentSpeed=" + currentSpeed +
                    '}';
        }
    }

    public class Damaging implements Damage {
        double accumulator = 0;

        public boolean CanShot() {
            if(accumulator>=1.0)
                return true;
            else{
                accumulator+=1/getShot_speed();
                return false;
            }

        }

        @Override
        public int getDamage() {
            return values.damage;
        }
        @Override
        public void DoDamage() {
            if(treeSetUnit.size() ==0)
                return;
            //Todo: here a big mistake
            Unit.this.getTreeSetUnit().first().getDamaging().AcceptDamage(this.getDamage());
            Log.doDamage(Unit.this,treeSetUnit.first());
            accumulator-=1.0;
        }
        @Override
        public void AcceptDamage(int damage) {
            int valueresulte = values.health - (int) (damage* getValues().armor);
            if ((valueresulte) <= 0) {

                values.health = 0;
                Log.onDestroy(Unit.this);
                onDestroy();
            } else {
                values.health -= valueresulte;
                Log.acceptDamage(Unit.this);
            }
            p("Accept Damage id: " + id + " new Health: " + values.health);
        }
        @Override
        public void decrease() {
            accumulator -= 1.0;
        }
    }
}