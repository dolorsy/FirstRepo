package com.destroyordefend.project.utility;


import java.util.Map;

enum PlayerRole{
    Attacker,Defender
}
public class Player {

    int Points;
    //Is he attacker or Defender
    PlayerRole role;

    //Todo: Here Integer (Key) Must Become Unit
    Map<Integer,Point> army;


    public Map<Integer, Point> getArmy() {
        return army;
    }

    //Todo: Here The Parameter should be a unit With its price to cut the Price From The Points
    void cutPrice(int price) throws NoEnoughPointsException {
        if(Points-price<0) {
            throw new NoEnoughPointsException("No Enough Points to buy; Your Points:" + Points + "; Price: " +  price );
        }
        Points-=price;
    }


    public void BuyAnArmy(/*Unit unit,*/int price){
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
