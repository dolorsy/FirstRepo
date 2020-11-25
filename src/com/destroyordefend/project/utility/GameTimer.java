package com.destroyordefend.project.utility;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Unit.Unit;

import static com.destroyordefend.project.Core.Game.game;
import static com.destroyordefend.project.utility.MainMethodAsyncTask.clearQueue;
import static com.destroyordefend.project.utility.MainMethodAsyncTask.invokeMainMethods;
import static com.destroyordefend.project.utility.UpdateMapAsyncTask.invokeUpdatePosition;
import static com.destroyordefend.project.utility.UpdateMapAsyncTask.updatePositionQueue;
import static com.destroyordefend.project.utility.UpdateRangeAsyncTask.invokeUpdateRange;

public class GameTimer extends Thread {
int RoundLength = 30;
int currentSecond = 0;
    public void run(){
        System.out.println("Here");
        for(;currentSecond<=RoundLength;currentSecond++){
            try {


                //Todo: Be Careful About Time Of the Following Three Methods, it should be 0.9 Second For Them all Together
                //Todo:May Be You need Exception Handling
                //Todo: We should invoke All Players UpdateArmy

                for(Unit u: game.getAllUnits()){

                    UpdateMapAsyncTask.addMethod(u::Move);
                    UpdateRangeAsyncTask.addMethod(u::UpdateRange);
                    Runnable method = () -> u.getDamaging().DoDamage();
                    MainMethodAsyncTask.addMethod(method);

                }
                /*
                  The PREVIOUS Code is a big Mistake
                  */
                long current = System.currentTimeMillis();
                System.out.println("1 Time: " + currentSecond  + "  " + String.valueOf((long)current));

                invokeUpdatePosition();
                invokeUpdateRange();
                invokeMainMethods();


                MainMethodAsyncTask.clearQueue();
                UpdateMapAsyncTask.clearQueue();
                UpdateRangeAsyncTask.clearQueue();


                current = System.currentTimeMillis()-current;
                System.out.println("2 Time: " + currentSecond +  "  " + String.valueOf((long)current));
                Thread.sleep(1000 - current);


              //  Game.getGame().UpdateUnits();
            } catch (InterruptedException e) {
                e.printStackTrace();
                //Todo: Need To Implement
            }

        }


    }

    public GameTimer(int roundLength) {
        RoundLength = roundLength;

    }
    public boolean onEnd(){
        return currentSecond == RoundLength;
    }

    public int getCurrentSecond() {
        return currentSecond;
    }
}

