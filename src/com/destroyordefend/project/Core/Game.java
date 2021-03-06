package com.destroyordefend.project.Core;

import com.destroyordefend.project.Movement.FixedPatrol;
import com.destroyordefend.project.Movement.FixedPosition;
import com.destroyordefend.project.Movement.ToTarget;
import com.destroyordefend.project.Tactic.LowestHealthAttack;
import com.destroyordefend.project.Tactic.Plan;
import com.destroyordefend.project.Tactic.RandomAttack;
import com.destroyordefend.project.Unit.Terrain;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.GameTimer;
import com.destroyordefend.project.utility.LiveData;
import com.destroyordefend.project.utility.PositionHelper;

import java.util.Iterator;
import java.util.TreeSet;

import static com.destroyordefend.project.Main.p;

enum States {
    NotRunning,
    Running,
    Paused,
    AttackerWin,
    DefenderWin,
}

public class Game implements GameManger {
    LiveData<States> GameState = new LiveData<>(States.NotRunning);
    private static Game game;
    private Unit base;
    private TreeSet<Unit> allUnits = new TreeSet<>((v1, v2) -> 1);
    private TreeSet<Terrain> terrains = new TreeSet<>(new PointComparator());
    private Team attackers;
    private Team defenders;
    int attackerNumber, defenderNumber;
    GameTimer gameTimer;

    private Game() {
        attackers = new Team();
        defenders = new Team();

        //Todo:: what about that
        base = new Unit();
        base.setValues(Shop.getInstance().getBaseValues());
        base.setRole(Player.TeamRole.Defender);
        base.setId(1);
        base.setPosition(new Point(2000, 2000));
        base.acceptMovement(new FixedPosition());
        if (PositionHelper.getInstance().canSetAt(base, base.getPosition()) != null)
            throw new RuntimeException("base cannot set");
        PositionHelper.getInstance().setUnitPlace(base, base.getPosition());
        base.setTreeSetUnit(new TreeSet<>(new PointComparator()));
    }

    public static Game getGame() {
        if (game == null)
            game = new Game();
        return game;
    }

    public void StartAnewGame() {


//Todo:: terrain need to add terrains
        // Terrain t = new Terrain(new Point(1020, 900), 0, 50,"vally");
        //terrains.add(t);
        gameTimer = new GameTimer(200);
        GameState.addObserver(gameTimer);
        // CreateTeamsStage();

        autoInitGame();
        UpdateUnits();

/*

        for (Unit unit: allUnits){
            ٍٍSystem.out.println("curr id : " + unit.getId() + " " + unit.getPosition());
//            System.out.println("left : " + unit.getNeighbourUnit("left").getId() +  unit.getNeighbourUnit("left").getPosition());
            System.out.println("right : " + unit.getNeighbourUnit("right").getId() +  unit.getNeighbourUnit("right").getPosition());
        }*/
        /*for(Unit u: allUnits){
            System.out.println(u);
        }*/


        this.StartBattle();

    }

    public void addPlayer(Player p) {
        (p.getRole().equals(Player.TeamRole.Attacker) ?
                attackers :
                defenders)
                .addPlayer(p);
    }

    public TreeSet<Terrain> getTerrains() {
        return terrains;
    }

    public Unit getBase() {
        return base;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    private void StartBattle() {
        setGameState(States.Running);
        gameTimer.start();
        p("Start Battle");
    }

    public boolean oneMore() {
        return attackerNumber + defenderNumber - 1 ==
                attackers.getTeamPlayers().size() +
                        defenders.getTeamPlayers().size();
    }

    public boolean fullAttackers() {
        return attackerNumber == attackers.getTeamPlayers().size();
    }

    public boolean fullDefender() {
        return defenders.getTeamPlayers().size() == defenderNumber;
    }

    public void UpdateUnits() {
        //this method to Update AllUnits
        allUnits = new TreeSet<>(new PointComparator());

        for (Player player : attackers.getTeamPlayers()) {
            //System.out.println("sss" + player.getArmy().first().getPosition());
            allUnits.addAll(player.getArmy());
        }
        for (Player player : defenders.getTeamPlayers()) {
            allUnits.addAll(player.getArmy());
        }
        //p("sizeee:" + allUnits.size());
        setNavigationForUnit();
    }

    public void setNavigationForUnit() {
        /*Unit left, right, curr;
        curr = allUnits.first();
        left = null;
        right = allUnits.iterator().next();
        //Todo:need to check
        for (int i = 1; i < allUnits.size(); i++) {
            curr.setRightUnit(right);
            right = allUnits.iterator().next();
            left = curr;
            curr = allUnits.iterator().next();
            curr.setLeftUnit(left);
        }
        //TOdo :need to check
        Unit left , right = null, cur;
        if (allUnits.size() == 0)
            return;
        Iterator<Unit> unitIterator = allUnits.iterator();
        left = null;
        cur = unitIterator.next();


        do {
            if (unitIterator.hasNext())
                right = unitIterator.next();
            cur.setNeighbourUnit("left", left);
            cur.setNeighbourUnit("right", right);

            left = cur;
            cur = right;
//            System.out.println("curr id : " + cur.getId() + " " + cur.getPosition());
//            System.out.println("left : " + cur.getNeighbourUnit("left").getId() +  cur.getNeighbourUnit("left").getPosition());
//            System.out.println("right : " + cur.getNeighbourUnit("right").getId() +  cur.getNeighbourUnit("right").getPosition());
//

            //  right = unitIterator.next();
        } while (unitIterator.hasNext());
        cur.setNeighbourUnit("left", left);
        cur.setNeighbourUnit("right", null);

         */
        for (Unit u : allUnits) {
            System.out.println(u);
            if (PositionHelper.getInstance().canSetAt(u, u.getPosition()) != null)
                throw new RuntimeException(u.getName() + " " + u.getPosition() + " cannot set");
            PositionHelper.getInstance().setUnitPlace(u, u.getPosition());
        }
    }

    public void setGameState(States gameState) {
        GameState.setData(gameState);
    }

    public void setGameState(String state) {
        GameState.setData(States.valueOf(state));
    }

    public LiveData<States> getGameState() {
        return GameState;
    }

    public TreeSet<Unit> getAllUnits() {
        return this.allUnits;
    }

    public void setPlayersNumbers(int attackerNumber, int defenderNumber) {
        if (this.attackerNumber > 0 || this.defenderNumber > 0)
            return;
        if (attackerNumber <= 0 || defenderNumber <= 0)
            System.exit(10);
        this.attackerNumber = attackerNumber;
        this.defenderNumber = defenderNumber;
    }

    public void CreateTeamsStage() {
        /*
        String path = "src\\com\\destroyordefend\\project\\Teams.json";
        TreeSet<Unit> al = new TreeSet<>();
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(path));
            JSONArray jsonArray = (JSONArray) obj.get("Players");
            for (Object jsonObject : jsonArray) {
                JSONObject player = (JSONObject) jsonObject;
                Player p = new Player(Integer.parseInt(player.get("Points").toString())
                        , Player.TeamRole.valueOf((String) player.get("role"))
                        , (String) player.get("id"));
                System.out.println(p.getRole().name());
                JSONArray Army = (JSONArray) player.get("army");
                System.out.println("Army : " + Army.toJSONString());
                for (Object a : Army) {
                    JSONObject jsonObject1 = (JSONObject) a;
                    System.out.println((String) jsonObject1.get("name"));
                    Unit unit = new Unit(new Unit.UnitValues(Shop.getInstance()
                            .getUnitByName((String) jsonObject1.get("name"))));

                    unit.setPosition(new Point(Integer.parseInt((String) jsonObject1.get("positionX")),
                            Integer.parseInt((String) jsonObject1.get("positionY"))));
                    unit.AcceptTactic(Tactic.getSuitableTactic((String) jsonObject1.get("tactic")));
                    System.out.println(jsonObject1.toJSONString());
                    System.out.println("test" + jsonObject1.get("movement"));
                    unit.AcceptMovement(Movement.getSuitableMovment(jsonObject1));
                    unit.setRole(p.getRole());
                    p.addArmy(unit);

                }
                addPlayer(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

    }

    PlayerIterator temp = null;

    public void UpdateState() {
        if (!attackers.isAlive()) {
            GameState.setData(States.DefenderWin);
            setGameState(States.DefenderWin);
        } else if (!base.isAlive()) {
            GameState.setData(States.AttackerWin);
            setGameState(States.AttackerWin);
        } else if (gameTimer.onEnd()) {
            GameState.setData(States.DefenderWin);
            setGameState(States.DefenderWin);
        }
        System.out.println(GameState.getData());
        //  System.exit(0);

    }

    public void DeleteUnit(Unit unit) {
        p("Removed id " + unit.getId());
        p(unit.getRole().name());
        (unit.getRole().equals(Player.TeamRole.Attacker) ?
                attackers :
                defenders).removeUnit(unit);

        //System.out.println("Sized " + allUnits.size());
        allUnits.removeIf(unit1 -> unit.getId() == unit1.getId());
        //System.out.println("Sized " + allUnits.size());
    }
//test 1
   /* private void autoInitGame() {
        Player Defender = new Player();
        Defender.setRole(Player.TeamRole.Defender);
        Player Attacker = new Player();
        Attacker.setRole(Player.TeamRole.Attacker);

        attackers.addPlayer(Attacker);
        defenders.addPlayer(Defender);
        Defender.addArmy(base);

        Unit unit1 = new Unit();
        unit1.setValues(Shop.getInstance().getUnitByName("Prism Tower"));
        unit1.setPosition(new Point(2100, 2100));
        unit1.acceptMovement(new FixedPosition());
        unit1.acceptTactic(new RandomAttack());
        unit1.setRole(Player.TeamRole.Defender);
        Defender.addArmy(unit1);


        Unit unitAttakar = new Unit();
        unitAttakar.setValues(Shop.getInstance().getUnitByName("Mirage tank"));
        unitAttakar.setPosition(new Point(2100, 4000));
        unitAttakar.acceptMovement(new ToTarget(base));
        unitAttakar.acceptTactic(new RandomAttack());
        unitAttakar.setRole(Player.TeamRole.Attacker);
        Defender.addArmy(unitAttakar);

    }*/
    //test 2
/*private void autoInitGame() {
    Player Defender = new Player();
    Defender.setRole(Player.TeamRole.Defender);
    Player Attacker = new Player();
    Attacker.setRole(Player.TeamRole.Attacker);

    attackers.addPlayer(Attacker);
    defenders.addPlayer(Defender);
    Defender.addArmy(base);

    Unit unit1 = new Unit();
    unit1.setValues(Shop.getInstance().getUnitByName("Grand Cannon"));
    unit1.setPosition(new Point(1600, 2100));
    unit1.acceptMovement(new FixedPosition());
    unit1.acceptTactic(new RandomAttack());
    unit1.setRole(Player.TeamRole.Defender);
    Defender.addArmy(unit1);


    Unit unit2 = new Unit();
    unit2.setValues(Shop.getInstance().getUnitByName("TeslaTank"));
    unit2.setPosition(new Point(2600, 3000));
    unit2.acceptMovement(new FixedPatrol(200));
    unit2.acceptTactic(new RandomAttack());
    unit2.setRole(Player.TeamRole.Defender);
    Defender.addArmy(unit2);

    Unit unit3 = new Unit();
    unit3.setValues(Shop.getInstance().getUnitByName("Prism Tower"));
    unit3.setPosition(new Point(2000, 1000));
    unit3.acceptMovement(new FixedPosition());
    unit3.acceptTactic(new RandomAttack());
    unit3.setRole(Player.TeamRole.Defender);
    Defender.addArmy(unit3);

    //attaker

    Unit unit4 = new Unit();
    unit4.setValues(Shop.getInstance().getUnitByName("Grizzly Tank"));
    unit4.setPosition(new Point(3000, 2000));
    unit4.acceptMovement(new ToTarget(base));
    unit4.acceptTactic(new RandomAttack());
    unit4.setRole(Player.TeamRole.Attacker);
    Attacker.addArmy(unit4);

    Unit unit5 = new Unit();
    unit5.setValues(Shop.getInstance().getUnitByName("TeslaTank"));
    unit5.setPosition(new Point(3000, 2500));
    unit5.acceptMovement(new ToTarget(base));
    unit5.acceptTactic(new LowestHealthAttack());
    unit5.setRole(Player.TeamRole.Attacker);
    Attacker.addArmy(unit5);



}*/
private void autoInitGame() {
    Player Defender = new Player();
    Defender.setRole(Player.TeamRole.Defender);
    Player Attacker = new Player();
    Attacker.setRole(Player.TeamRole.Attacker);

    attackers.addPlayer(Attacker);
    defenders.addPlayer(Defender);
    Defender.addArmy(base);

    Unit unit1 = new Unit();
    unit1.setValues(Shop.getInstance().getUnitByName("Grand Cannon"));
    unit1.setPosition(new Point(2400, 1800));
    unit1.acceptMovement(new FixedPosition());
    unit1.acceptTactic(new RandomAttack());
    unit1.setRole(Player.TeamRole.Defender);
    Defender.addArmy(unit1);


    Unit unit2 = new Unit();
    unit2.setValues(Shop.getInstance().getUnitByName("Tank destroyer"));
    unit2.setPosition(new Point(1500, 1500));
    unit2.acceptMovement(new FixedPatrol(1000));
    unit2.acceptTactic(new RandomAttack());
    unit2.setRole(Player.TeamRole.Defender);
    Defender.addArmy(unit2);

    Unit unit3 = new Unit();
    unit3.setValues(Shop.getInstance().getUnitByName("Patriot Missile"));
    unit3.setPosition(new Point(1700, 2200));
    unit3.acceptMovement(new FixedPosition());
    unit3.acceptTactic(new RandomAttack());
    unit3.setRole(Player.TeamRole.Defender);
    Defender.addArmy(unit3);

    //attaker

    Unit unit4 = new Unit();
    unit4.setValues(Shop.getInstance().getUnitByName( "Mirage tank"));
    unit4.setPosition(new Point(3000, 3000));
    unit4.acceptMovement(new ToTarget(base));
    unit4.acceptTactic(new RandomAttack());
    unit4.setRole(Player.TeamRole.Attacker);
    Plan p = new Plan();
    p.addCommands(p.new Wait(3),
            p.new ChangeTarget(new Point(1700, 2200)),
            p.new Wait(3));
    unit4.acceptPlan(p);
    Attacker.addArmy(unit4);


    Unit unit5 = new Unit();
    unit5.setValues(Shop.getInstance().getUnitByName("Prism tank"));
    unit5.setPosition(new Point(1000, 1000));
    unit5.acceptMovement(new ToTarget(base));
    unit5.acceptTactic(new LowestHealthAttack());
    unit5.setRole(Player.TeamRole.Attacker);
    Attacker.addArmy(unit5);



}
    public PlayerIterator playerIterator() {
        if (temp == null)
            return temp = new PlayerIterator();
        return temp;
    }

    public States getAstate(String state) {
        return States.valueOf(state);
    }

    public String getGameStateName() {

        return GameState.getData().name();
    }

    @Override
    public void pause() {
        try {
            synchronized (gameTimer) {
                gameTimer.wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resume() {
        synchronized (gameTimer) {
            gameTimer.notify();

        }

    }

    class PlayerIterator implements Iterator<Player> {

        Iterator<Player> attack = attackers.getTeamPlayers().iterator(),
                defend = defenders.getTeamPlayers().iterator();
        Player.TeamRole role = Player.TeamRole.Attacker;

        @Override
        public boolean hasNext() {
            return defend.hasNext() || attack.hasNext();
        }

        @Override
        public Player next() {
            if (role == Player.TeamRole.Attacker) {
                role = Player.TeamRole.Defender;
                if (!defend.hasNext())
                    defend = defenders.getTeamPlayers().iterator();
                return defend.next();
            } else {
                role = Player.TeamRole.Attacker;
                if (!attack.hasNext())
                    attack = attackers.getTeamPlayers().iterator();
                return attack.next();
            }
        }
    }

}
