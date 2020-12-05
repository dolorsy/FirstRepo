package com.destroyordefend.project.Core;

import com.destroyordefend.project.Movement.FixedPatrol;
import com.destroyordefend.project.Movement.FixedPosition;
import com.destroyordefend.project.Tactic.RandomAttack;
import com.destroyordefend.project.Tactic.Tactic;
import com.destroyordefend.project.Unit.Terrain;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.GameTimer;
import com.destroyordefend.project.utility.LiveData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
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

public class Game {
     LiveData<States>  GameState = new LiveData<>(States.NotRunning) ;
    public static Game game;
    private Unit base;
    private TreeSet<Unit> allUnits = new TreeSet<>((v1, v2) -> 1);
    private TreeSet<Terrain> terrains = new TreeSet<>(new PointComparator());
    private Team attackers, defenders;
    int attackerNumber, defenderNumber;
    GameTimer gameTimer;

    private Game() {
        attackers = new Team();
        defenders = new Team();

        base = new Unit(Shop.getInstance().getBaseValues());
        base.setTreeSetUnit(new TreeSet<>(new PointComparator()));
        allUnits.add(base);
    }

    public static Game getGame() {
        if (game == null)
            game = new Game();
        return game;
    }

    public void StartAnewGame() {

        /*
Todo:: terrain need to add terrains
        Terrain t = new Terrain(new Point(30,100),2,"river");
        terrains.add(t );*/
        gameTimer = new GameTimer(10);
        GameState.addObserver(gameTimer);
       //  CreateTeamsStage();

        autoInitGame();
        UpdateUnits();
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
            for (Unit unit : player.getArmy()) {
                unit.setRole(player.getRole());
                allUnits.add(unit);
            }
        }
        for (Player player : defenders.getTeamPlayers()) {
            allUnits.addAll(player.getArmy());
        }
        p(String.valueOf(allUnits.size()));
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
        }*/
        //TOdo :need to check
        Unit left, right = null, cur;
        Iterator<Unit> unitIterator = allUnits.iterator();
        left = null;
        cur = unitIterator.next();

         do{
             if (unitIterator.hasNext())
                 right = unitIterator.next();

            cur.setNeighbourUnit("left", left);
            cur.setNeighbourUnit("right", right);
            left = cur;
            cur = right;

             //  right = unitIterator.next();
        }while (unitIterator.hasNext());
        cur.setNeighbourUnit("left", left);
        cur.setNeighbourUnit("right", null);
    }

    public void setGameState(States gameState) {
        GameState.setData(gameState);
    }
    public void setGameState(String state){
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
        String path = "src\\com\\destroyordefendfirst\\project\\Teams.json";
        TreeSet<Unit> al = new TreeSet<>();
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(path));
            JSONArray jsonArray = (JSONArray) obj.get("Players");
            for (Object jsonObject : jsonArray) {
                JSONObject player = (JSONObject) jsonObject;
                Player p = new Player(Integer.parseInt( player.get("Points").toString())
                        , Player.TeamRole.valueOf((String) player.get("role"))
                        , (String) player.get("id"));
                System.out.println(p.getRole().name());
                JSONArray Army = (JSONArray) player.get("army");
                System.out.println("Army : " + Army.toJSONString());
                for(Object a : Army){
                    JSONObject jsonObject1 = (JSONObject) a;
                    System.out.println((String) jsonObject1.get("name"));
                    Unit unit = new Unit(new Unit.UnitValues (Shop.getInstance()
                            .getUnitByName((String) jsonObject1.get("name"))));

                    unit.setPosition(new Point(Integer.parseInt((String) jsonObject1.get("positionX")),
                            Integer.parseInt((String) jsonObject1.get("positionY"))));
                    unit.AcceptTactic(Tactic.getSuitableTactic((String) jsonObject1.get("tactic")));
                    System.out.println(jsonObject1.toJSONString());
                    System.out.println("test" + jsonObject1.get("movement"));
                   // unit.AcceptMovement(Movement.getSuitableMovment( jsonObject1.get("movement")));
                    unit.setRole(p.getRole());
                    p.addArmy(unit);

                }
                addPlayer(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    }

    public void DeleteUnit(Unit unit) {
        p("Removed id " + unit.getId());
        p(unit.getRole().name());
        (unit.getRole().equals(Player.TeamRole.Attacker) ?
                attackers :
                defenders).removeUnit(unit);

        allUnits.remove(unit);

    }

    private void autoInitGame() {
        Unit defndUnit = new Unit();
        base.setPosition(new Point(30,0));
        Unit.UnitValues valuess = Shop.getInstance().getUnitByName("Prism Tower");
        defndUnit.setRole(Player.TeamRole.Defender);
        defndUnit.setPosition(new Point(25, 35));
        defndUnit.setValues(valuess);
        defndUnit.AcceptMovement(new FixedPosition());
        defndUnit.AcceptTactic(new RandomAttack());
        defndUnit.setRole(Player.TeamRole.Defender);
        Player p = new Player();
        p.setRole(Player.TeamRole.Defender);
        p.addArmy(defndUnit);

        defenders.addPlayer(p);
        Unit attackUnit = new Unit();

        Unit.UnitValues values = Shop.getInstance().getUnitByName("Mirage tank");
        attackUnit.setRole(Player.TeamRole.Attacker);
        attackUnit.setPosition(new Point(20, 20));
        attackUnit.setValues(values);

        attackUnit.AcceptMovement(new  FixedPatrol(80));
        attackUnit.AcceptTactic(new RandomAttack());
        Player attacker = new Player();
        attacker.setRole(Player.TeamRole.Attacker);
        attacker.addArmy(attackUnit);

        attackers.addPlayer(attacker);
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
