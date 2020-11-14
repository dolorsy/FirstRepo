package com.destroyordefend.project.utility;

import com.destroyordefend.project.Unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game {
static Map<Unit,Point> allUnits;
Arena arena;

List<Player> players;
int initPoints =10000 ;
    public void StartAnewGame(){
        arena = new Arena();
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
    protected void AddAnewPlayer(){
        //Todo:  we should Get The Name of Player and the Role (Attacker / Defender) and set In the following constructor
        players.add(new Player());

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
        }

    };

}
