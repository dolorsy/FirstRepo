package com.destroyordefend.project.Core;

import com.destroyordefend.project.Unit.Unit;

import java.util.*;


enum State{
    NotRunning,
    Running,
    Paused,
    AttackerWin,
    DefenderWin,
}
public class Game {
static TreeSet<Unit> allUnits;
State GameState = State.NotRunning;
Arena arena;
static Shop shop = new Shop();
Team Attackers;
Team Defenders;
int initPoints =10000 ;
    public void StartAnewGame(){
        Attackers = new Team();
        Defenders =  new Team();
        arena = new Arena();
        allUnits = new TreeSet<Unit>();
        //Todo:Here We Should get the number of Players
        Attackers.addPlayer(new Player(initPoints,TeamRole.Attacker,"attacker"));
        Defenders.addPlayer(new Player(initPoints,TeamRole.Defender,"defender"));
        this.StartShoppingStage();

        /**
         * The Following Code We Will Use Later
         *
         * this.CreatePlayersStage();
         * this.StartShoppingStage();
         * **/




    }

    public void setGameState(State gameState) {
        GameState = gameState;
    }

    public State getGameState() {
        return GameState;
    }

    public static TreeSet<Unit> getAllUnits() {
        return allUnits;
    }

    public static Shop getShop() {
        return shop;
    }

    protected void AddAnewPlayer(){
        //Todo:  we should Get The Name of Player and the Role (Attacker / Defender) and set In the following constructor

    }
    protected  void StartPlacementStage(){
        //Todo: x and y is Temporary Here
        int x=10,y=10,r=5;
        for(Player p : Defenders.getTeamPlayers()){
        for(Unit u : p.getArmy()){
            arena.setUnitPlace(new Point(x,y),(int)u.getRadius()) ;
                    x+=10;y+=10;
        }
        }
        for(Player p : Attackers.getTeamPlayers()){
            for(Unit u : p.getArmy()){
                arena.setUnitPlace(new Point(x,y),(int)u.getRadius()) ;
                x+=10;y+=10;
            }
        }

    }
    protected  void CreateTeamsStage(){

        /**
         * for the number of players we will call AddPlayer
         * inside the loop the Statement will be
         * AddAnewPlayer();
         *
         */


    }
    protected void StartShoppingStage(){

        for(Player player : Attackers.getTeamPlayers()){
            player.CreateArmy();
            allUnits.addAll(player.getArmy());
        }
        for(Player player : Defenders.getTeamPlayers()){
            player.CreateArmy();
            allUnits.addAll(player.getArmy());
        }

    };

}
