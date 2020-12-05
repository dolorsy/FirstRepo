package com.destroyordefend.project;

import com.destroyordefend.project.Core.Game;

public class Main {

    public static void p(String s) {
        System.out.println(s);
    }

    //Todo:: After A WW3 with git
    public static void main(String[] args) throws InterruptedException {
//        GameTimer gameTimer= new GameTimer(10);
//        gameTimer.start();

       Game game = Game.getGame();
        game.StartAnewGame();


    }
}
