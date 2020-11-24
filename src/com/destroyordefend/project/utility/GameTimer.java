package com.destroyordefend.project.utility;

import com.destroyordefend.project.Core.Game;

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
        for(int i=1;i<=RoundLength;i++){
            try {
                //Todo: Each tikTok we should do that game.allUnits = new List(attackersUnits + DefendersUnit);


                //Todo: Be Careful About Time Of the Following Three Methods, it should be 0.9 Second For Them all Together
                //Todo:May Be You need Exception Handling
                //Todo: We should invoke All Players UpdateArmy
                invokeUpdatePosition();
                invokeUpdateRange();
                invokeMainMethods();
                Thread.sleep(1000);
                MainMethodAsyncTask.clearQueue();
                UpdateMapAsyncTask.clearQueue();
                UpdateRangeAsyncTask.clearQueue();

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

