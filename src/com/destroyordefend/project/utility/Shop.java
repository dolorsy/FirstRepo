package com.destroyordefend.project.utility;


import com.destroyordefend.project.Unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;


public class Shop {
    //Todo: Here static List<>Units
     ArrayList<Unit> ShopUnits = new ArrayList<>();
     HashMap<String,Integer> unitPrices = new HashMap<>();

private Unit getUnitById(String id){
    for(Unit unit : this.ShopUnits){
        //Todo: getType
        if(/*unit.getType()*/ "SS".equals(id)){
            return unit;

        }
    }
    return null;
}

public int getUnitPrice(String id){
    return unitPrices.get(id);
}
    public void InitShop(){
        //Todo: Here We Should read from DataBase Or JSON To fill ShopUnits;

    }

    public Unit sellItem(String id){
        //TODO: Get The Unit By the ID and Copy it and return
        //Todo: the Code Will be return new Unit(allUnit ShopUnits.getById(id));
        //Todo: the return Statement will be         return new Unit(getUnitById(id));
        return new Unit();
    }



}
