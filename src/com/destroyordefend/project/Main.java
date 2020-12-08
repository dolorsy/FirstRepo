package com.destroyordefend.project;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.utility.ConsoleZoomUI;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void p(String s) {
        System.out.println(s);
    }
    //Todo:: After A WW3 with git
    public static void main(String[] args) throws InterruptedException {
//        GameTimer gameTimer= new GameTimer(10);
//        gameTimer.start();


        try {
            SwingUtilities.invokeAndWait(() -> new ConsoleZoomUI().createComponents());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
       Game game = Game.getGame();
        game.StartAnewGame();
    }
}
