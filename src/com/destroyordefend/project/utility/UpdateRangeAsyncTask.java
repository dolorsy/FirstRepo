package com.destroyordefend.project.utility;

import java.util.PriorityQueue;

public class UpdateRangeAsyncTask {
    static PriorityQueue<Runnable> updateRangeQueue = new PriorityQueue<>();

    public static void addMethod(Runnable methodCall) {
        updateRangeQueue.add(methodCall);
    }

    /**
     * From any Class We Will Store Methods Like The Following
     * Runnable methodCall = () ->  MyMethodCall;
     * UpdateMapAsyncTask.addMethod(methodCall);
     *
     * Example:
     * Runnable method = () -> invokeUpdatePosition();
     *         updatePositionQueue.add(method);
     */

    public static void invokeUpdatePosition()
    {
        for(Runnable updateRange : updateRangeQueue) {
            updateRange.run();
        }
    }

}
