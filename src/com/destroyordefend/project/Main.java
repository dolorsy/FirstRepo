package com.destroyordefend.project;

import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.Game;

import static com.destroyordefend.project.Unit.Unit.*;

public class Main {

    public static void main(String[] args) {
        Game myGame = new Game();
        myGame.StartAnewGame();

        /*
        Unit unit = new Unit();
        UnitValues unitValues = new UnitValues(10,20,10,29);
        System.out.print(
                "Speed :" + unitValues.getSpeed()+
                "\nshot_Spped :" + unitValues.getShot_speed()+
                "\nDamage :" + unitValues.getDamage()+
                "\nHealthe " + unitValues.getHealth()
        );
        */
    }

}
