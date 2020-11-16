package com.destroyordefend.project.utility;

import java.util.PriorityQueue;
import java.util.Queue;

public class UpdateMapAsyncTask {
    static PriorityQueue<Runnable> updatePositionQueue = new PriorityQueue<>();


    public static void addMethod(Runnable methodCall) {
        updatePositionQueue.add(methodCall);

    }

    /**
     * From any Class We Will Store Methods Like The Following
     * Runnable methodCall = () ->  MyMethodCall;
     * UpdateMapAsyncTask.addMethod(methodCall);
     *
     */

    public static void invokeUpdatePosition()
    {
        for(Runnable updatePosition : updatePositionQueue) {
            updatePosition.run();
        }
    }


}
