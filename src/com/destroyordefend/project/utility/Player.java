package com.destroyordefend.project.utility;


import com.destroyordefend.project.Unit.Unit;

import java.util.ArrayList;
import java.util.List;

enum PlayerRole{
    Attacker,Defender
}
public class Player {

    int Points;
    //Is he attacker or Defender
    PlayerRole role;
    String id;
    List<Unit> army;

    public Player() {
        army = new ArrayList<>();
    }

    public Player(int points, PlayerRole role, String id) {
        Points = points;
        this.role = role;
        this.id = id;
    }

    public List<Unit> getArmy() {
        return army;
    }

    void cutPrice(int price) throws NoEnoughPointsException {
        if(Points-price<0) {
            throw new NoEnoughPointsException("No Enough Points to buy; Your Points:" + Points + "; Price: " +  price );
        }
        Points-=price;
    }
    public void CreateArmy(){
        //Todo:Here we will Shopping
        while (this.Points>0)
            try{
                BuyAnArmy(Game.getShop().sellItem("SS"),Game.shop.getUnitPrice("SS") );
            }catch (PointsCantByuException ex){
                System.err.println(ex);
                break;
            }



    }

    public void BuyAnArmy(Unit unit, int price) throws PointsCantByuException{
        try{
            if(this.Points <Game.getShop().getLowestPrice())
                throw new PointsCantByuException("You Have only " + this.Points + ", this cant buy anything!!");
            cutPrice(price);
            army.add(unit);
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


class PointsCantByuException extends Exception{
    PointsCantByuException(String message){
        super(message);
    }
}
