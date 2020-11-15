package com.destroyordefend.project.utility;

import com.destroyordefend.project.Unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game {
static ArrayList<Unit> allUnits;
Arena arena;
static Shop shop = new Shop();
List<Player> players;
int initPoints =10000 ;
    public void StartAnewGame(){
        arena = new Arena();
        allUnits = new ArrayList<>();
        //Todo:Here We Should get the number of Players
        players  = new ArrayList<>();

        players.add(new Player(initPoints,PlayerRole.Attacker,"attacker"));
        players.add(new Player(initPoints,PlayerRole.Defender,"defender"));
        this.StartShoppingStage();

        /**
         * The Following Code We Will Use Later
         *
         * this.CreatePlayersStage();
         * this.StartShoppingStage();
         * **/




    }

    public static Shop getShop() {
        return shop;
    }

    protected void AddAnewPlayer(){
        //Todo:  we should Get The Name of Player and the Role (Attacker / Defender) and set In the following constructor
        players.add(new Player());

    }
    protected  void StartPlacementStage(){
        //Todo: x and y is Temporary Here
        int x=10,y=10,r=5;
        for(Player p : players){
        for(Unit u : p.getArmy()){
            arena.setUnitPlace(new Point(x,y),/*u.getRadius*/r) ;
                    x+=10;y+=10;
        }
        }

    }
    protected  void CreatePlayersStage(){

        /**
         * for the number of players we will call AddPlayer
         * inside the loop the Statement will be
         * AddAnewPlayer();
         *
         */


    }
    protected void StartShoppingStage(){
        for(Player player : players){
            player.CreateArmy();
            allUnits.addAll(player.getArmy());
        }

    };

}