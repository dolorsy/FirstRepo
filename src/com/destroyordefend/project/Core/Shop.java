package com.destroyordefend.project.Core;


import com.destroyordefend.project.Unit.Unit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;


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

                 JSONArray sMap = (JSONArray) unit1.get("SortMap");
                 List<String> SortMap = (List<String>) unit1.get("SortMap");

                 String price = (String) unit1.get("price");
                 Unit unit = new Unit(
                         type,
                         name ,
                         Integer.parseInt(health) ,
                         Double.parseDouble(armor) ,
                         Integer.parseInt(damage) ,
                         Integer.parseInt(range) ,
                         Double.parseDouble(shot_speed) ,
                         Integer.parseInt(radius) ,
                         Integer.parseInt(speed) ,
                        ((List<String>) SortMap),
                         Integer.parseInt(price)
                 );

                  this.ShopUnits.add(new Unit(unit));
             }

             //Test read Json
             /*
            for (int i = 0 ; i < ShopUnits.size() ; i++){
                System.out.println(
                        ShopUnits.get(i).getType()+"\n"+
                        ShopUnits.get(i).getName()+"\n"+
                        ShopUnits.get(i).getHealth()+"\n"+
                        ShopUnits.get(i).getArmor()+"\n"+
                        ShopUnits.get(i).getDamage()+"\n"+
                        ShopUnits.get(i).getRange()+"\n"+
                        ShopUnits.get(i).getShot_speed()+"\n"+
                        ShopUnits.get(i).getRadius()+"\n"+
                        ShopUnits.get(i).getSpeed()+"\n"+
                        ShopUnits.get(i).getSortMap()+"\n"+
                        ShopUnits.get(i).getPrice()+"\n"+
                        "=================================="

                );
            }
              */

         } catch (ParseException | IOException e) {
             e.printStackTrace();

         }


     }

    public Unit sellItem(String type){
        return new Unit(Objects.requireNonNull(getUnitByType(type)));


    }

}
