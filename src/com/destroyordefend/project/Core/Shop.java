package com.destroyordefend.project.Core;


import com.destroyordefend.project.Unit.Unit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Shop {
    private static Shop ins;
    private Unit.UnitValues baseUnitValues;
    private ArrayList<Unit.UnitValues> shopUnits;
    private int lowestPrice = 1 << 30;

    private Shop() {
        this.shopUnits = new ArrayList<>();
        this.InitShop();
    }

    public static Shop getInstance() {
        if (ins == null) {
            synchronized (Shop.class) {
                if (ins == null)
                    ins = new Shop();
            }
        }
        return ins;
    }

    public ArrayList<Unit.UnitValues> getShopUnits() {
        return shopUnits;
    }

    public Unit.UnitValues getBaseValues() {
        return baseUnitValues;
    }

    public Unit.UnitValues getUnitByName(String name) {
        if(name.equalsIgnoreCase("Main Base"))
            return getBaseValues();
        for (Unit.UnitValues unitValues : this.shopUnits) {
            if (unitValues.is(name)) {
                return unitValues;
            }
        }
        return null;
    }

    public int getLowestPrice() {
        return this.lowestPrice;
    }

    public void InitShop() {

        String path = "src\\com\\destroyordefend\\project\\UnitslnShop.json";
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(path));
            JSONArray shop = (JSONArray) obj.get("shop");

            for (Object a : shop) {
                JSONObject unit1 = (JSONObject) a;
                Unit.UnitValues unitValues = new Unit.UnitValues(unit1);
                if (unitValues.is("main base"))
                    baseUnitValues = unitValues;
                else {
                    this.shopUnits.add(unitValues);
                    lowestPrice = Math.min(lowestPrice, Integer.parseInt((String) unit1.get("price")));
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}