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

     public Shop(){

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

     public void InitShop(){

        //Todo: Here We Should read from DataBase Or JSON To fill ShopUnits;
        //Todo:Here We should init lowestPrice

         String path = "src\\com\\destroyordefend\\project\\UnitslnShop.json";

         JSONParser jsonParser = new JSONParser();
         try {
             JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(path));
             JSONArray shop = (JSONArray) obj.get("shop");

             for (Object a : shop) {
                 JSONObject unit1 = (JSONObject) a;
                 String type = (String)  unit1.get("type");

                 String name = (String)  unit1.get("name");

                 String health = (String) unit1.get("health");

                 String armor = (String) unit1.get("armor");

                 String damage = (String) unit1.get("damage");

                 String range = (String) unit1.get("range");

                 String shot_speed = (String) unit1.get("shot_speed");

                 String radius = (String) unit1.get("radius");

                 String speed = (String) unit1.get("speed");

                 List<String> SortMap =new ArrayList<>();

                 JSONArray sMap = (JSONArray) unit1.get("SortMap");
                 System.out.println(sMap);
                 for(Object c : sMap){
                     String s = (String) c.toString();
                     SortMap.add(s);
                 }
                 String price = (String) unit1.get("price");

                 Unit.UnitValues  unitValues = new Unit.UnitValues(
                         type ,
                         name ,
                         Integer.parseInt(health) ,
                         Double.parseDouble(armor) ,
                         Integer.parseInt(damage) ,
                         Integer.parseInt(range) ,
                         Double.parseDouble(shot_speed) ,
                         Integer.parseInt(radius) ,
                         Integer.parseInt(speed) ,
                        ((List<String>) SortMap),
                         Integer.parseInt(price));
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
