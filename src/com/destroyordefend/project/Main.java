package com.destroyordefend.project;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.PointComparator;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.GameTimer;
import com.destroyordefend.project.utility.Log;
import netscape.javascript.JSObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;

public class Main   {

    public static void p(String s){
        System.out.println(s);
    }
    public static void main(String[] args)  {
        GameTimer gameTimer= new GameTimer(10);
        gameTimer.start();
    /*   Game game = Game.getGame();
        game.StartAnewGame();
        Log log = new Log();
        log.m(new Unit(2,2,2,"mm",2,2,2,2));
*/
    }

}
