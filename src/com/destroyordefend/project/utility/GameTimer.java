package com.destroyordefend.project.utility;

import static com.destroyordefend.project.utility.MainMethodAsyncTask.invokeMainMethods;
import static com.destroyordefend.project.utility.UpdateMapAsyncTask.invokeUpdatePosition;
import static com.destroyordefend.project.utility.UpdateRangeAsyncTask.invokeUpdateRange;

public class GameTimer extends Thread {
int RoundLength = 30;
int currentSecond = 0;
    public void run(){
        for(int i=1;i<=RoundLength;i++){
            try {
                //Todo: Be Careful About Time Of the Following Three Methods, it should be 0.9 Second For Them all Together
                //Todo:May Be You need Exception Handling
                invokeUpdatePosition();
                invokeUpdateRange();
                invokeMainMethods();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //Todo: Need To Implement
            }

        }

    }

    public GameTimer(int roundLength) {
        RoundLength = roundLength;
        this.start();

    }

    public int getCurrentSecond() {
        return currentSecond;
    }
}

