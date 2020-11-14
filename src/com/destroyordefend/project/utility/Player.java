package com.destroyordefend.project.utility;


import com.destroyordefend.project.Unit.Unit;

import java.util.Map;

enum PlayerRole{
    Attacker,Defender
}
public class Player {

    int Points;
    //Is he attacker or Defender
    PlayerRole role;
    String id;
    Map<Unit,Point> army;

    public Player() {
    }

    public Player(int points, PlayerRole role, String id) {
        Points = points;
        this.role = role;
        this.id = id;
    }

    public Map<Unit, Point> getArmy() {
        return army;
    }

    //Todo: Here The Parameter should be a unit With its price to cut the Price From The Points
    void cutPrice(int price) throws NoEnoughPointsException {
        if(Points-price<0) {
            throw new NoEnoughPointsException("No Enough Points to buy; Your Points:" + Points + "; Price: " +  price );
        }
        Points-=price;
    }
    public void CreateArmy(){
        //Todo:Here we will Shopping



    }

    public void BuyAnArmy(Unit unit, int price){
        try{
            cutPrice(price);
            //army.add(Unit,Point);
        }catch (NoEnoughPointsException ex){
            System.err.println(ex);
        }
    }
}

class NoEnoughPointsException extends Exception{
    NoEnoughPointsException(String message){
        super(message);
    }
}
