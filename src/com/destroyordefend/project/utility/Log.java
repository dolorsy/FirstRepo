package com.destroyordefend.project.utility;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Unit.Unit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.destroyordefend.project.Core.Game.game;

public class Log {
    private static final File logFile = new File("logFile.txt");


    private static void createFile (File file){
        if(!logFile.exists()){
            try {
                boolean newFile = file.createNewFile();

            } catch (IOException e) {
                System.err.println("Cant create a log file!!");
                e.printStackTrace();
            }
        }

    }

    public static void writeFile(String text , File file){
        //todo: we send file as a parameter because maybe we ned to write in a specific file
        System.out.println(text);
        if(!System.getProperty("os.name").equals("Windows 10"))
            return;

        createFile(file);
        try {
            FileWriter myWriter = new FileWriter(file.getAbsoluteFile(),true);
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void move(Unit unit){
        writeFile("time: " + Game.getGame().getGameTimer().getCurrentSecond() +
                "\tUnit_id :" + unit.getId() +
                " Moved to :"+
                "\tX :" + unit.getPosition().getX()+
                ",Y :" + unit.getPosition().getY() + "\n"  + "---------------" + "\n",logFile);
    }

    public static void doDamage(Unit unit_One ,Unit unit_Two){
        writeFile("time: " + Game.getGame().getGameTimer().getCurrentSecond() +
                "\tUnit_id: " + unit_One.getId() +
                "\t Attack "  +
                "\tUnit_id: " + unit_Two.getId() + " By Damage: " + unit_One.getDamaging().getDamage()+ "\n" + "---------------" + "\n",logFile);
    }

    public static void acceptDamage(Unit unit){
        writeFile("time: " + Game.getGame().getGameTimer().getCurrentSecond()+
                "\tUnit id: " + unit.getId() +
                "\tnew health :" + unit.getValues().getHealth() + "\n"  + "---------------" + "\n",logFile);
    }


    public static void onDestroy(Unit unit) {
        writeFile("time: " + Game.getGame().getGameTimer().getCurrentSecond()+
                "\tUnit id: " + unit.getId() +
                "\tDestroyed "+ "\n"  + "---------------" + "\n",logFile);
    }

    public static void GameOver(String gameStateName) {
        writeFile(gameStateName,logFile);
    }
}

