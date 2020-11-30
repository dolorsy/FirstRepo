package com.destroyordefend.project.Core;


import com.destroyordefend.project.Unit.Unit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class Shop {
    //Todo: Here static List<>Units
     ArrayList<Unit> ShopUnits;
     HashMap<String,Integer> unitPrices = new HashMap<>();
     int lowestPrice = 0;

     Shop(){

         this.ShopUnits = new ArrayList<>();
         this.InitShop();
     }

private Unit getUnitByType(String type){
    for(Unit unit : this.ShopUnits){
        if(unit.getType().equals(type)){
            return unit;
        }
    }
    return null;
}

public int getUnitPrice(String type){
    return unitPrices.get(type);
}

    public int getLowestPrice() {
        return this.lowestPrice;
    }

     void InitShop(){
        //Todo: Here We Should read from DataBase Or JSON To fill ShopUnits;
        //Todo:Here We should init lowestPrice

         String path = "src\\com\\destroyordefend\\project\\Shop.json";
         Unit.UnitValues  unitValues = new Unit.UnitValues();
         JSONParser jsonParser = new JSONParser();
         try {
             JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(path));
             JSONArray shop = (JSONArray) obj.get("Shop");

             for (Object a : shop) {
                 JSONObject unit1 = (JSONObject) a;
                 String type = (String)  unit1.get("type");
                 unitValues.setType(type);

                 JSONArray sMap = (JSONArray) unit1.get("SortMap");
                 List<String> sn = (List<String>) unit1.get("SortMap");
                 unitValues.setSortMap(sn);

                 String range = (String) unit1.get("range");
                 unitValues.setRange(Integer.parseInt(range));

                 String radius = (String) unit1.get("radius");
                 unitValues.setRadius(Integer.parseInt(radius));

                 String damage = (String) unit1.get("damage");
                 unitValues.setDamage(Integer.parseInt(damage));

                 String shot_speed = (String) unit1.get("shot_speed");
                 unitValues.setShot_speed(Integer.parseInt(shot_speed));

                 String speed = (String) unit1.get("speed");
                 unitValues.setSpeed(Integer.parseInt(speed));

                 String health = (String) unit1.get("health");
                 unitValues.setHealth(Integer.parseInt(health));

                 this.ShopUnits.add(new Unit(unitValues));


             }

         } catch (ParseException | IOException e) {
             e.printStackTrace();
         }
    }

    public Unit sellItem(String type){
        return new Unit(Objects.requireNonNull(getUnitByType(type)));
    }



}
