package com.destroyordefend.project.Core;

import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Tactic.Tactic;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.GameTimer;
import com.destroyordefend.project.utility.UpdateMapAsyncTask;
import com.destroyordefend.project.utility.UpdateRangeAsyncTask;

import java.util.*;


enum States {
    NotRunning,
    Running,
    Paused,
    AttackerWin,
    DefenderWin,
}

public class Game {
    public static Game game;
    TreeSet<Unit> allUnits;
    States GameState = States.NotRunning;
    Shop shop = new Shop();
    Team Attackers;
    Team Defenders;
    int initPoints = 10000;
    GameTimer gameTimer;

    Game() {
        //Todo:here The Round Length
        gameTimer = new GameTimer(30);
        if (game == null)
            game = new Game();
    }

    public void StartAnewGame() {
        Attackers = new Team();
        Defenders = new Team();
        allUnits = new TreeSet<Unit>(new PointComparator());
        //Todo:Here We Should get the number of Players
        Attackers.addPlayer(new Player(initPoints, TeamRole.Attacker, "attacker"));
        Defenders.addPlayer(new Player(initPoints, TeamRole.Defender, "defender"));
        this.StartShoppingStage();
        this.StartBattle();
        /**
         * The Following Code We Will Use Later
         *
         * this.CreatePlayersStage();
         * this.StartShoppingStage();
         * **/

    }

    private void StartBattle() {
        for (Unit unit : allUnits) {
            UpdateMapAsyncTask.addMethod(unit::Move);
            UpdateRangeAsyncTask.addMethod(unit::UpdateRange);
            //Todo:Main method add to it Async Task

        }
    }



    public void UpdateUnits() {
        //this method to Update AllUnits
        allUnits = new TreeSet<>(new PointComparator());
        for (Player player : Attackers.getTeamPlayers()) {
            allUnits.addAll(player.getArmy());
        }
        for (Player player : Defenders.getTeamPlayers()) {
            allUnits.addAll(player.getArmy());
        }
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
                Movement.SetUnitPlace(new Point(x, y),u);
                x += 10;
                y += 10;
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
         *
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
            return;
        }
    }


}
