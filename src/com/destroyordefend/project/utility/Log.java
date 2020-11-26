package com.destroyordefend.project.utility;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Unit.Unit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    public Log(){
        File file = new File("logFile.txt");
        createFile(file);
    }
    //Create File
    public static void createFile(File file){
        try {
            File myObj = new File("logFile.txt");
            if (myObj.createNewFile()) {
              //  System.out.println("File created: " + myObj.getName());
            } else {
                //System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //Write File
    public static void writeFile(String text , File file){

        try {
            FileWriter myWriter = new FileWriter(file.getAbsoluteFile(),true);
            myWriter.write(text);
            myWriter.close();
        //   System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void m(Unit unit){
        File file = new File("logFile.txt");
       writeFile("time :"/*Game.getGame().getcurrentSecond()*/ +
                      "Unit_id :" + unit.getId() +
                      " X :" + unit.getPosition().getX() +" "+
                      " Y :" + unit.getPosition().getY() + "\n"  + "---------------" + "\n",file);
    }

    public static void d(Unit unit_One ,Unit unit_Two){
        File file = new File("logFile.txt");
        writeFile("Unit_id :" + unit_One.getId() +
                       " Attack "  +
                       "Unit_id :" + unit_Two.getId() + "\n" + "---------------" + "\n",file);
    }

    public static void h(Unit unit){
        File file = new File("logFile.txt");
        writeFile("time :"/*Game.getGame().getcurrentSecond()*/+
                       "health :" + unit.getHealth() + "\n"  + "---------------" + "\n",file);
           }
    public static void four(){}

    public static void five(){}

    public static void six(){}

    public static void seven(){}

}

