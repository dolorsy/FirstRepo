package com.destroyordefend.project;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.PointComparator;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.Log;

import java.util.Comparator;
import java.util.TreeSet;


public class Main   {

    public static void p(String s){
        System.out.println(s);
    }
    public static void main(String[] args)  {
        //GameTimer gameTimer= new GameTimer(10);
        Game game = Game.getGame();
        game.StartAnewGame();
        Log log = new Log();
        log.m(new Unit(2,2,2,"mm",2,2,2,2));

    }

}
