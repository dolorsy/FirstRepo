package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Core.Player;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Core.PointComparator;
import com.destroyordefend.project.Movement.FixedPosition;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Tactic.Plan;
import com.destroyordefend.project.Tactic.Tactic;
import com.destroyordefend.project.utility.IdGenerator;
import com.destroyordefend.project.utility.Log;
import com.destroyordefend.project.utility.UpdateMapAsyncTask;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import static com.destroyordefend.project.Core.Game.getGame;
import static com.destroyordefend.project.Main.p;

public class Unit implements TacticAble, MovementAble, Barrier {

    private  int id;
    private Movement movement;
    private TreeSet<Unit> treeSetUnit = new TreeSet<>(new PointComparator());
    private Point point = new Point();
    private Player.TeamRole role;
    private UnitValues values;
    private Tactic tactic;
    private Damaging damaging;
    private Plan plan;
    private HashMap<Plan,Integer> plans = new HashMap<Plan, Integer>() ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;
        Unit unit = (Unit) o;
        return getId() == unit.getId() && getMovement().equals(unit.getMovement()) && getTreeSetUnit().equals(unit.getTreeSetUnit()) && point.equals(unit.point) && getRole() == unit.getRole() && getValues().equals(unit.getValues()) && getTactic().equals(unit.getTactic()) && getDamaging().equals(unit.getDamaging()) && plan.equals(unit.plan) && plans.equals(unit.plans);
    }

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
        this.tactic =unit.tactic;
    }

    public Unit(UnitValues unitValues) {
        this();
        this.values = new UnitValues(unitValues);
    }

    public ArrayList<String> getSortMap() {

        return values.sortMap;
    }

    public String getType(){
        return values.type;
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
        System.out.println(getId() + " " + treeSetUnit.size());
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


    public void Move(){
        System.out.println(getName()  + " Here " + getMovement().getClass().getName().equals(FixedPosition.class.getName()));
      //  if(getMovement().getClass().getName().equals(FixedPosition.class.getName()))
            this.tactic.SortMap(this);
/*
        if(this.plans.size() != 0 ) {
            if (this.plan.isInPlace(this)){
                this.plans.put(plan,this.plans.get(plan)-1);
                return;
            }

        }
*/
    if(plan != null){
        try {
            boolean wait = plan.applyTo(this);
            if (wait) {
                System.out.println("waiting");
                return;
            }
        }catch (Exception e){
            plan = null;
        }
    }
        for(int i =0 ;i<values.currentSpeed;i++) {
            Runnable method = () -> movement.StartMove(Unit.this);
            UpdateMapAsyncTask.addMethod(method);

        }

    }
    public void StartMove(Unit unit) {
        unit.tactic.SortMap(unit);
        if (getTreeSetUnit().size() != 0) {
            System.out.println("Size: " + getTreeSetUnit().size());
            System.out.println("\n\n\n");
            return;
        }

            Point p = unit.movement.GetNextPoint(unit);
            if(p.equals(getPosition())){
                return;
            }
            boolean f = Movement.setNext(unit,p);

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
          //  PositionHelper.getInstance().setUnitPlace(this,p);
            //this.setPosition(p);
         //   this.updateLeftAndRight();

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
        Movement.addTarget(point,this);
    }

    @Override
    public Unit acceptPlan(Plan plan) {
        this.plan = plan;
        if(plan!= null)
            plan.addUnits(this);
        /*
        this.plans.put(plan,plan.getTime());
        plan.addToPlan(this);
        this.plan = plan;
         */



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
        getGame().DeleteUnit(this);
        getGame().UpdateState();
    }


    public void UpdateRange() {
        Tactic.updateRange(this);//should removed because this is invoked at sortMap
        this.tactic.SortMap(this);
        p("Update Range id: " + id + "new Range " + treeSetUnit);
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
        String type;
        int currentSpeed=0;//11

        public UnitValues(String name, String Type,int health, double armor, int damage, int range, double shot_speed, int radius, int speed, ArrayList<String> SortMap, int price) {
            this.name = name;
            this.type = Type;
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
            this.type = type;
        }

        public  UnitValues(){

        }

        public void setCurrentSpeed(int currentSpeed) {
            this.currentSpeed = currentSpeed;
        }

        public UnitValues(UnitValues unitValues) {
            this(
                    unitValues.name,
                    unitValues.type,
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
            type = (String) unit.get("type");

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
            return type;
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
        double accumulator = getShot_speed();

        public int CanShot() {
            System.out.println("Acc " + accumulator);
            if(accumulator>=1.0)
                return ((int) accumulator);
            else{
                accumulator+=1.0/getShot_speed();
                return 0;
            }

        }

        @Override
        public int getDamage() {
            return values.damage;
        }
        @Override
        public void DoDamage() {
            System.out.println("Damage " + getName() + " " + getTreeSetUnit().size() );
            if(getTreeSetUnit().size() ==0)
                return;
            //Todo: here a big mistake
            System.out.println("After " + getId() + "  "  + getName());
            Unit.this.getTreeSetUnit().first().getDamaging().AcceptDamage(this.getDamage());
            Log.doDamage(Unit.this,Unit.this.getTreeSetUnit().first());
            System.out.println("Ac" + accumulator);
            accumulator-=1.0;
            System.out.println("Ac" + accumulator);
        }
        @Override
        public void AcceptDamage(int damage) {

            int valueresulte = values.health - (int) (getValues().armor ==0 ?damage:damage* getValues().armor);
            if ((valueresulte) <= 0) {

                values.health = 0;
                Log.onDestroy(Unit.this);
                onDestroy();
            } else {
                values.health = valueresulte;
                Log.acceptDamage(Unit.this);
            }
        }
        @Override
        public void decrease() {
            accumulator -= 1.0;
        }
    }
}