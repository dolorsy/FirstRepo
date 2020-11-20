package com.destroyordefend.project.Core;

import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.GameTimer;

import java.util.*;


enum States{
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
Arena arena;
 Shop shop = new Shop();
Team Attackers;
Team Defenders;
int initPoints =10000 ;
GameTimer gameTimer;
 Game(){
     //Todo:here The Round Length
     gameTimer = new GameTimer(30);
    if(game == null)
        game = new Game();
}
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

    public void UpdateUnits(){
     //this method to Update AllUnits
     allUnits = new TreeSet<>();
     for(Player player : Attackers.getTeamPlayers()){
         allUnits.addAll(player.getArmy());
     }
        for(Player player : Defenders.getTeamPlayers()){
            allUnits.addAll(player.getArmy());
        }
    }
    public void setGameState(States gameState) {
        GameState = gameState;
    }

    public States getGameState() {
        return GameState;
    }

    public  TreeSet<Unit> getAllUnits() {
        return this.allUnits;
    }

    public  Shop getShop() {
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

    public void UpdateState() {
        boolean stillInGame = false;

        for(Player player : Attackers.getTeamPlayers()){
            if(player.getArmy().size() != 0){
                stillInGame = true;
            }
            if(!stillInGame){
                setGameState(States.DefenderWin);
                return;
            }
        }
       for(Unit unit : allUnits){
           if(unit.getType() == "Base" && unit.getHealth() == 0){
               setGameState(States.AttackerWin);
               return;
           }
       }
        if(gameTimer.onEnd()){
            setGameState(States.DefenderWin);
            return;
        }
    }

}
