package com.destroyordefend.project.Core;

import com.destroyordefend.project.Main;
import com.destroyordefend.project.Movement.FixedPosition;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Movement.ToTarget;
import com.destroyordefend.project.Tactic.LowestHealthAttack;
import com.destroyordefend.project.Tactic.RandomAttack;
import com.destroyordefend.project.Tactic.Tactic;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.GameTimer;
import com.destroyordefend.project.utility.UpdateMapAsyncTask;
import com.destroyordefend.project.utility.UpdateRangeAsyncTask;

import java.util.*;

import static com.destroyordefend.project.Main.p;


enum States {
    NotRunning,
    Running,
    Paused,
    AttackerWin,
    DefenderWin,
}

public class Game {
    public static Game game;
    static Unit unit;
    TreeSet<Unit> allUnits;
    TreeSet<Terrain> terrains;
    States GameState = States.NotRunning;
    Shop shop = new Shop();
    Team Attackers;
    Team Defenders;
    int initPoints = 10000;
    GameTimer gameTimer;


    public static Game getGame(){
        if (game == null)
            game = new Game();
        return game;
    }

    public void StartAnewGame() {
        //Todo:: terrain need to add terrains
        terrains = new TreeSet<Terrain>(new Terrain.TerrainComparator());
        Attackers = new Team();
        Defenders = new Team();
        gameTimer  = new GameTimer(30);
        allUnits = new TreeSet<Unit>(new PointComparator());
        //Todo:Here We Should get the number of Players
        Attackers.addPlayer(new Player(initPoints, TeamRole.Attacker, "attacker"));
        Defenders.addPlayer(new Player(initPoints, TeamRole.Defender, "defender"));
        unit = new Unit(5,5,2,"TT",5,5,5,50);
        unit.setTreeSetUnit(new TreeSet<>(new PointComparator()));
        p(unit.getTreeSetUnit().toString());
        Attackers.getTeamPlayers().get(0).addArmy(new Unit(unit)
                .AcceptTactic(new LowestHealthAttack()).AcceptMovement(new FixedPosition()));
        Defenders.getTeamPlayers().get(0).addArmy( new Unit(unit).AcceptTactic(new RandomAttack())
                .AcceptMovement(new ToTarget(Attackers.getTeamPlayers().get(0).getArmy().first())));

        UpdateUnits();
        //this.StartShoppingStage();
        this.StartPlacementStage();

        this.StartBattle();
      //  UpdateUnits();
        /**
         * The Following Code We Will Use Later
         *
         * this.CreatePlayersStage();
         * this.StartShoppingStage();
         * **/

    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    private void StartBattle() {
        gameTimer.start();

        p("StartBattel");
        p(String.valueOf(allUnits.size()));
        for (Unit unit : allUnits) {
            unit.print();
            UpdateMapAsyncTask.addMethod(unit::Move);
            UpdateRangeAsyncTask.addMethod(unit::UpdateRange);
            //Todo:Main method add to it Async Task

        }
    }



    public void UpdateUnits() {
        //this method to Update AllUnits
        allUnits = new TreeSet<>(new PointComparator());

        for (Player player : Attackers.getTeamPlayers()) {
            for (Unit unit : player.getArmy()){
                unit.setId(7);
                unit.setHealth(10);
                unit.setRole(player.role.name());
                allUnits.add(unit);
            }
        }
        for (Player player : Defenders.getTeamPlayers()) {
            allUnits.addAll(player.getArmy());
        }
        p(String.valueOf(allUnits.size()));
    }

    public void setGameState(States gameState) {
        GameState = gameState;
    }

    public States getGameState() {
        return GameState;
    }

    public TreeSet<Unit> getAllUnits() {
        return this.allUnits;
    }

    public Shop getShop() {
        return shop;
    }

    protected void AddAnewPlayer() {
        //Todo:  we should Get The Name of Player and the Role (Attacker / Defender) and set In the following constructor

    }

    protected void StartPlacementStage() {
        //Todo: x and y is Temporary Here
        int x = 10, y = 10, r = 5;
        for (Player p : Defenders.getTeamPlayers()) {
            for (Unit u : p.getArmy()) {
                p("PS " + u.getId());
                Movement.SetUnitPlace(new Point(x, y),u);
                x += 100;
                y += 100;
            }
        }
        for (Player p : Attackers.getTeamPlayers()) {
            for (Unit u : p.getArmy()) {
                Movement.SetUnitPlace(new Point(x, y),u);
                x += 10;
                y += 10;
            }
        }

    }

    protected void CreateTeamsStage() {

        /**
         * for the number of players we will call AddPlayer
         * inside the loop the Statement will be
         * AddAnewPlayer();
         */




    }

    protected void StartShoppingStage() {

        for (Player player : Attackers.getTeamPlayers()) {
            player.CreateArmy();
            allUnits.addAll(player.getArmy());
        }
        for (Player player : Defenders.getTeamPlayers()) {
            player.CreateArmy();
            allUnits.addAll(player.getArmy());
        }

    }

    ;

    public void UpdateState() {
        boolean stillInGame = false;

        //Todo: should be a variable in Team count how many unit in all team players
        for (Player player : Attackers.getTeamPlayers()) {
            if (player.getArmy().size() != 0) {
                stillInGame = true;
            }
            if (!stillInGame) {
                setGameState(States.DefenderWin);
                return;
            }
        }
        for (Unit unit : allUnits) {
            if (unit.getType().equals("Base") && unit.getHealth() == 0) {
                setGameState(States.AttackerWin);
                return;
            }
        }
        if (gameTimer.onEnd()) {
            setGameState(States.DefenderWin);
        }
    }

    public void DeleteUnit(Unit unit){
        p("Removed id " + unit.getId() );
        p(unit.getRole());
        if(unit.getRole().equals("Attacker")){
            for(Player player : Attackers.getTeamPlayers()){
                if(player.getId().equals(unit.getPlayerId())){
                    player.getArmy().remove(unit);
                }
            }
        }else{
            for(Player player : Defenders.getTeamPlayers()){
                if(player.getId().equals(unit.getPlayerId())){
                    player.getArmy().remove(unit);
                }
            }
        }
        this.allUnits.remove(unit);

    }


}
