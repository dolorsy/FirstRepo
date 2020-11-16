package com.destroyordefend.project.utility;

public class GameTimer extends Thread {
int RoundLength = 30;
int currentSecond = 0;
    public void run(){
        for(int i=1;i<=RoundLength;i++){
            try {
                Thread.sleep(1000);
                System.out.println(i);
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

