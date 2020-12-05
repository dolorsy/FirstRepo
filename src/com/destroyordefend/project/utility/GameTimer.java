package com.destroyordefend.project.utility;

import com.destroyordefend.project.Unit.Unit;


import java.util.Observable;
import java.util.Observer;
import static com.destroyordefend.project.Core.Game.game;
import static com.destroyordefend.project.utility.MainMethodAsyncTask.doMainThingQueue;
import static com.destroyordefend.project.utility.UpdateMapAsyncTask.updatePositionQueue;
import static com.destroyordefend.project.utility.UpdateRangeAsyncTask.updateRangeQueue;

public class GameTimer extends Thread implements Observer {
int RoundLength ;
int currentSecond = 0;
Thread updatePositionsThread = new Thread();
Thread updateRangeThread = new Thread();
    Thread updateMainThread = new Thread();
//public static ExecutorService executorService = Executors.newFixedThreadPool(5);
    public void run(){
        for(;currentSecond<=RoundLength;currentSecond++){
            try {


                //Todo: Be Careful About Time Of the Following Three Methods, it should be 0.9 Second For Them all Together
                //Todo:May Be You need Exception Handling
                //Todo: We should invoke All Players UpdateArmy

                long current = System.currentTimeMillis();
/*
                executorService.submit(UpdateMapAsyncTask::invokeUpdatePosition);
                executorService.submit(UpdateRangeAsyncTask::invokeUpdateRange);
                executorService.submit(MainMethodAsyncTask::invokeMainMethods);
                executorService.submit(this::reFill);
*/
                if(game.getGameStateName().equals("Running")) {

           /*  updatePositionsThread = new Thread(UpdateMapAsyncTask::invokeUpdatePosition);
             updatePositionsThread.start();*/
             updateRangeThread = new Thread(UpdateRangeAsyncTask::invokeUpdateRange);
             updateRangeThread.start();
             updateMainThread = new Thread(MainMethodAsyncTask::invokeMainMethods);
                    updateMainThread.start();

                    current = System.currentTimeMillis() - current;
                    Thread.sleep(1000 - current);
                    reFill();
                }else if(game.getGameStateName().equals("AttackerWin") || game.getGameStateName().equals("DefenderWin")){
                    Log.GameOver("GameOver, "  + game.getGameStateName());
                    this.interrupt();
                    break;
                }else{
                    currentSecond--;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();

                //Todo: Need To Implement
            }

        }


       // executorService.shutdown();

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

    void reFill(){
        updatePositionQueue.clear();
        updateRangeQueue.clear();
        doMainThingQueue.clear();
        for(Unit unit: game.getAllUnits()){

            if(unit.getSpeed() != 0 )
            UpdateMapAsyncTask.addMethod(unit::Move);
        if(!unit.getName().equals("Main Base"))
            UpdateRangeAsyncTask.addMethod(() -> unit.getTactic().SortMap(unit));

        //Todo: here we can make damaging more real
            if(unit.getDamage() != 0 && unit.getTreeSetUnit().size() !=0 && unit.getDamaging().CanShot())
            MainMethodAsyncTask.addMethod(() ->unit.getDamaging().DoDamage());
        }
    }

     public boolean pause(){
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean resumee(){
        this.notify();
        return false;
    }
    LiveData<String> state;

    @Override
    public void update(Observable o, Object arg) {
        state = (LiveData<String>) o;
    }
}

