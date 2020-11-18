package com.destroyordefend.project;

import com.destroyordefend.project.utility.GameTimer;
import com.destroyordefend.project.utility.UpdateMapAsyncTask;
import com.destroyordefend.project.utility.UpdateRangeAsyncTask;

import static com.destroyordefend.project.utility.UpdateMapAsyncTask.invokeUpdatePosition;
import static com.destroyordefend.project.utility.UpdateRangeAsyncTask.invokeUpdateRange;


public class Main   {

    public static void main(String[] args)  {
        GameTimer gameTimer= new GameTimer(35);
        for(int i =0;i<10;i++){
            Runnable method = () -> System.out.println(3);
            UpdateMapAsyncTask.addMethod(method);
        }
        for(int i =0;i<10;i++){
            Runnable method = () -> System.out.println(5);
            UpdateRangeAsyncTask.addMethod(method);
        }
        Test t = new Test();
        t.run();


    }

    static class Test extends Thread{



        public void run(){
            invokeUpdatePosition();
            invokeUpdateRange();


        };
    }

}
